package com.ktg.mes.pro.controller;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.md.domain.MdItemBatchConfig;
import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.service.IMdItemBatchConfigService;
import com.ktg.mes.md.service.IMdProductBomService;
import com.ktg.mes.pro.domain.ProTask;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.domain.ProWorkorderBom;
import com.ktg.mes.pro.service.IProTaskService;
import com.ktg.mes.pro.service.IProWorkorderBomService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 生产工单Controller
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
@RestController
@RequestMapping("/mes/pro/workorder")
public class ProWorkorderController extends BaseController
{
    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IProWorkorderBomService proWorkorderBomService;

    @Autowired
    private IMdProductBomService mdProductBomService;

    @Autowired
    private IMdItemBatchConfigService mdItemBatchConfigService;

    @Autowired
    private IProTaskService proTaskService;

    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询生产工单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProWorkorder proWorkorder)
    {
        startPage();
        List<ProWorkorder> list = proWorkorderService.selectProWorkorderList(proWorkorder);
        return getDataTable(list);
    }

    @GetMapping("/listWithTaskJson")
    public TableDataInfo listWithTaskJson(ProWorkorder proWorkorder){
        startPage();
        List<ProWorkorder> list = proWorkorderService.selectProWorkorderList(proWorkorder);
        Iterator<ProWorkorder> iterator = list.iterator();
        while (iterator.hasNext()){
            ProWorkorder workorder = iterator.next();
            List<ProTask> tasks = proTaskService.selectProTaskProcessViewByWorkorder(workorder.getWorkorderId());
            workorder.setTasks(tasks);
        }
        return getDataTable(list);
    }

    /**
     * 导出生产工单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:export')")
    @Log(title = "生产工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProWorkorder proWorkorder)
    {
        List<ProWorkorder> list = proWorkorderService.selectProWorkorderList(proWorkorder);
        ExcelUtil<ProWorkorder> util = new ExcelUtil<ProWorkorder>(ProWorkorder.class);
        util.exportExcel(response, list, "生产工单数据");
    }

    /**
     * 获取生产工单详细信息
     */
    @GetMapping(value = "/{workorderId}")
    public AjaxResult getInfo(@PathVariable("workorderId") Long workorderId)
    {
        return AjaxResult.success(proWorkorderService.selectProWorkorderByWorkorderId(workorderId));
    }

    /**
     * 新增生产工单
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:add')")
    @Log(title = "生产工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProWorkorder proWorkorder)
    {
        if(UserConstants.NOT_UNIQUE.equals(proWorkorderService.checkWorkorderCodeUnique(proWorkorder))){
            return AjaxResult.error("生产工单编号已存在！");
        }

        //效验产品的批次属性是否有客户，有则客户必选
        MdItemBatchConfig batchConfig = mdItemBatchConfigService.getMdItemBatchConfigByItemId(proWorkorder.getProductId());
        if(StringUtils.isNotNull(batchConfig) && UserConstants.YES.equals(batchConfig.getClientFlag()) && StringUtils.isNull(proWorkorder.getClientId())){
            return AjaxResult.error("当前生产工单的产品批次属性包含客户信息，客户必选！");
        }

        if(proWorkorder.getParentId()==null || proWorkorder.getParentId()==0){
            proWorkorder.setAncestors("0");
        }
        proWorkorderService.insertProWorkorder(proWorkorder);

        Long workorderId = proWorkorder.getWorkorderId();
        generateBomLine(workorderId);
        proWorkorder.setCreateBy(getUsername());
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_WORKORDER,proWorkorder.getWorkorderId(),proWorkorder.getWorkorderCode(),proWorkorder.getWorkorderName());
        return AjaxResult.success(workorderId);
    }

    /**
     * 修改生产工单
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:edit')")
    @Log(title = "生产工单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody ProWorkorder proWorkorder)
    {
        if(UserConstants.NOT_UNIQUE.equals(proWorkorderService.checkWorkorderCodeUnique(proWorkorder))){
            return AjaxResult.error("生产工单编号已存在！");
        }

        //效验产品的批次属性是否有客户，有则客户必选
        MdItemBatchConfig batchConfig = mdItemBatchConfigService.getMdItemBatchConfigByItemId(proWorkorder.getProductId());
        if(StringUtils.isNotNull(batchConfig) && UserConstants.YES.equals(batchConfig.getClientFlag()) && StringUtils.isNull(proWorkorder.getClientId())){
            return AjaxResult.error("当前生产工单的产品批次属性包含客户信息，客户必选！");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(proWorkorder.getWorkorderId());

        int ret =proWorkorderService.updateProWorkorder(proWorkorder);
        //如果是产品和数量发生变化则需要重新生成BOM组成
        if(ret >0){
            if(workorder.getProductId().longValue() != proWorkorder.getProductId().longValue() ||
                    workorder.getQuantity().compareTo(proWorkorder.getQuantity())!=0){
                removeBomLine(proWorkorder.getWorkorderId());
                generateBomLine(proWorkorder.getWorkorderId());
            }
        }

        return toAjax(ret);
    }

    /**
     * 删除生产工单
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:remove')")
    @Log(title = "生产工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workorderIds}")
    public AjaxResult remove(@PathVariable Long[] workorderIds)
    {
        for (Long id:workorderIds
             ) {
            ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(id);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(workorder.getStatus())){
                return AjaxResult.error("只能删除草稿状态单据！");
            }
            removeBomLine(id);
        }
        return toAjax(proWorkorderService.deleteProWorkorderByWorkorderIds(workorderIds));
    }

    /**
     * 根据生产工单中的产品生成BOM物料行
     * @param workorderId
     */
    private void generateBomLine(Long workorderId){
        //先根据ID找到对应的产品
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(workorderId);

        //根据产品找到BOM组成
        MdProductBom param = new MdProductBom();
        param.setItemId(workorder.getProductId());
        List<MdProductBom> boms = mdProductBomService.selectMdProductBomList(param);

        //生成BOM数据
        BigDecimal orderQuantitiy = workorder.getQuantity();
        if(CollUtil.isNotEmpty(boms)){
            for (MdProductBom bom:boms
                 ) {
                ProWorkorderBom workorderBom = new ProWorkorderBom();
                workorderBom.setWorkorderId(workorderId);
                workorderBom.setItemId(bom.getBomItemId());
                workorderBom.setItemCode(bom.getBomItemCode());
                workorderBom.setItemName(bom.getBomItemName());
                workorderBom.setItemSpc(bom.getBomItemSpec());
                workorderBom.setItemOrProduct(bom.getItemOrProduct());
                workorderBom.setUnitOfMeasure(bom.getUnitOfMeasure());
                workorderBom.setUnitName(bom.getUnitName());
                workorderBom.setQuantity(orderQuantitiy.multiply(bom.getQuantity()));
                proWorkorderBomService.insertProWorkorderBom(workorderBom);
            }
        }
    }

    /**
     * 删除当前工单下所有BOM组成
     * @param workorderId
     */
    private void removeBomLine(Long workorderId){
        ProWorkorderBom param = new ProWorkorderBom();
        param.setWorkorderId(workorderId);
        proWorkorderBomService.deleteProWorkorderBomByWorkorderId(workorderId);
    }

    /**
     * 获取当前工单的物料需求清单
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:list')")
    @GetMapping("/listItems")
    public TableDataInfo listItemss(ProWorkorder proWorkorder)
    {
        List<MdProductBom> result = new ArrayList<MdProductBom>();
        ProWorkorderBom param = new ProWorkorderBom();
        param.setWorkorderId(proWorkorder.getWorkorderId());
        List<ProWorkorderBom> boms = proWorkorderBomService.selectProWorkorderBomList(param);
        if(!CollectionUtils.isEmpty(boms)){
            for ( ProWorkorderBom bom: boms
                 ) {
                MdProductBom theBom = new MdProductBom();
                theBom.setBomItemId(bom.getItemId());
                theBom.setBomItemName(bom.getItemName());
                theBom.setBomItemCode(bom.getItemCode());
                theBom.setItemOrProduct(bom.getItemOrProduct());
                theBom.setUnitOfMeasure(bom.getUnitOfMeasure());
                theBom.setUnitName(bom.getUnitName());
                theBom.setBomItemSpec(bom.getItemSpc());
                theBom.setQuantity(bom.getQuantity());
                result.addAll(getBoms(theBom,bom.getQuantity(),0));
            }
        }
        TableDataInfo dataTable = getDataTable(result);
        List<MdProductBom> rows = (List<MdProductBom>) dataTable.getRows();
        // 使用Map来合并对象
        Map<String, MdProductBom> map = new HashMap<>();
        for (MdProductBom bom : rows) {
            String bomItemCode = bom.getBomItemCode();
            if (map.containsKey(bomItemCode)) {
                MdProductBom existingObj = map.get(bomItemCode);
                existingObj.setQuantity(existingObj.getQuantity().add(bom.getQuantity()) );
            } else {
                map.put(bomItemCode, bom);
            }
        }
        // 从Map中获取去重且合并后的对象集合
        Collection<MdProductBom> mergedCollection = map.values();
        // （可选）将结果转换回List
        List<MdProductBom> mergedList = mergedCollection.stream().collect(Collectors.toList());
        dataTable.setRows(mergedList);
        return dataTable;
    }

    private List<MdProductBom> getBoms(MdProductBom item,BigDecimal quantity,int count){
        MdProductBom param = new MdProductBom();
        List<MdProductBom> results = new ArrayList<MdProductBom>();
        if(count >20){
            return results;
        }
        param.setItemId(item.getBomItemId());
        List<MdProductBom> boms = mdProductBomService.selectMdProductBomList(param);
        if(CollUtil.isNotEmpty(boms)){
            //最多20层依赖
            count ++;
            for (MdProductBom bomItem: boms
                 ) {
                bomItem.setQuantity(quantity.multiply(bomItem.getQuantity()));
                results.addAll(getBoms(bomItem,bomItem.getQuantity(),count));
            }
        }else{
            results.add(item);
        }
        return results;
    }


    /**
     * 完成工单
     * @param workorderId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:edit')")
    @Log(title = "生产工单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/finish/{workorderId}")
    public AjaxResult dofinish(@PathVariable Long workorderId){
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(workorderId);

        //将此工单下所有的生产任务状态设置为已完成
        ProTask param = new ProTask();
        param.setWorkorderId(workorderId);
        List<ProTask> tasks = proTaskService.selectProTaskList(param);
        if(!CollectionUtils.isEmpty(tasks)){
            for (ProTask task:tasks
                 ) {
                task.setStatus(UserConstants.ORDER_STATUS_FINISHED);
                task.setFinishDate(new Date());
                proTaskService.updateProTask(task);
            }
        }

        workorder.setStatus(UserConstants.ORDER_STATUS_FINISHED); //更新工单的状态
        workorder.setFinishDate(new Date());
        proWorkorderService.updateProWorkorder(workorder);
        return AjaxResult.success();
    }

    /**
     * 取消工单
     * @param workorderId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:workorder:edit')")
    @Log(title = "生产工单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/cancel/{workorderId}")
    public AjaxResult doCancel(@PathVariable Long workorderId){
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(workorderId);

        //将此工单下所有的生产任务状态设置为取消
        ProTask param = new ProTask();
        param.setWorkorderId(workorderId);
        List<ProTask> tasks = proTaskService.selectProTaskList(param);
        if(!CollectionUtils.isEmpty(tasks)){
            for (ProTask task:tasks
            ) {
                task.setStatus(UserConstants.ORDER_STATUS_CANCELED);
                task.setCancelDate(new Date());
                proTaskService.updateProTask(task);
            }
        }

        workorder.setStatus(UserConstants.ORDER_STATUS_CANCELED); //更新工单的状态
        workorder.setCancelDate(new Date());
        proWorkorderService.updateProWorkorder(workorder);
        return AjaxResult.success();
    }


    /**
     * 首页生产工单查询
     * @param proWorkorder
     * @return
     */
    @GetMapping("/getHomeList")
    public AjaxResult getHomeList(ProWorkorder proWorkorder) {
        return proWorkorderService.getHomeList(proWorkorder);
    }

}
