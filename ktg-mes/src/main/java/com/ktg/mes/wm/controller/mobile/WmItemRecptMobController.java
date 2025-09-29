package com.ktg.mes.wm.controller.mobile;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.ItemRecptTxBean;
import com.ktg.mes.wm.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("采购入库")
@RestController
@RequestMapping("/mobile/wm/itemrecpt")
public class WmItemRecptMobController extends BaseController {

    @Autowired
    private IWmItemRecptService wmItemRecptService;

    @Autowired
    private IWmItemRecptLineService wmItemRecptLineService;

    @Autowired
    private IWmItemRecptDetailService wmItemRecptDetailService;

    @Autowired
    private IWmArrivalNoticeService wmArrivalNoticeService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询物料入库单列表
     */
    @ApiOperation("查询物料入库单列表")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmItemRecpt wmItemRecpt)
    {
        startPage();
        List<WmItemRecpt> list = wmItemRecptService.selectWmItemRecptList(wmItemRecpt);
        return getDataTable(list);
    }



    /**
     * 新增物料入库单
     */
    @ApiOperation("新增采购入库单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:add')")
    @Log(title = "物料入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmItemRecpt wmItemRecpt)
    {
        if(StringUtils.isNotNull(wmItemRecpt.getRecptCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmItemRecptService.checkRecptCodeUnique(wmItemRecpt))){
                return  AjaxResult.error("单据编号已存在！");
            }
        }else {
            wmItemRecpt.setRecptCode(autoCodeUtil.genSerialCode(UserConstants.ITEMRECPT_CODE,""));
        }

        if(StringUtils.isNull(wmItemRecpt.getVendorId())){
            return AjaxResult.error("请选择供应商！");
        }

        if(StringUtils.isNull(wmItemRecpt.getRecptName())){
            wmItemRecpt.setRecptName("供应商"+wmItemRecpt.getVendorName()+"物资入库");
        }



        wmItemRecpt.setCreateBy(getUsername());
        wmItemRecptService.insertWmItemRecpt(wmItemRecpt);
        return AjaxResult.success(wmItemRecpt);
    }

    /**
     * 修改物料入库单
     */
    @ApiOperation("修改采购入库单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemRecpt wmItemRecpt)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmItemRecptService.checkRecptCodeUnique(wmItemRecpt))){
            return  AjaxResult.error("单据编号已存在！");
        }

        if(StringUtils.isNull(wmItemRecpt.getVendorId())){
            return AjaxResult.error("请选择供应商！");
        }

        //如果是提交到待上架状态，则检查是否有对应的入库单行
        if(UserConstants.ORDER_STATUS_APPROVING.equals(wmItemRecpt.getStatus())){
            //检查有没有入库单行
            WmItemRecptLine param = new WmItemRecptLine();
            param.setRecptId(wmItemRecpt.getRecptId());
            List<WmItemRecptLine> lines = wmItemRecptLineService.selectWmItemRecptLineList(param);
            if(CollUtil.isEmpty(lines)){
                return AjaxResult.error("请添加入库单行");
            }
        }

        //如果是提交到待执行入库状态，需要检查各个行是否都已分配了对应的明细行，数量必须一致。
        if(UserConstants.ORDER_STATUS_APPROVED.equals(wmItemRecpt.getStatus())){

            WmItemRecptLine param = new WmItemRecptLine();
            param.setRecptId(wmItemRecpt.getRecptId());
            List<WmItemRecptLine> lines = wmItemRecptLineService.selectWmItemRecptLineList(param);
            boolean flag = true;
            StringBuilder sb = new StringBuilder();
            if(CollUtil.isNotEmpty(lines)){
                for(WmItemRecptLine line : lines){
                    if(!UserConstants.EQUAL_TO.equals(wmItemRecptDetailService.checkQuantity(line.getLineId()))){
                        flag = false;
                        sb.append(line.getItemName()).append(line.getBatchCode()).append("未完成上架！");
                    }
                }
            }else{
                flag = false;
                sb.append("请为对入库单行进行上架操作!");
            }

            if(!flag){
                return AjaxResult.error(sb.toString());
            }
        }

        return toAjax(wmItemRecptService.updateWmItemRecpt(wmItemRecpt));
    }

    /**
     * 获取物料入库单详细信息
     */
    @ApiOperation("获取物料入库单详细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:query')")
    @GetMapping(value = "/{recptId}")
    public AjaxResult getInfo(@PathVariable("recptId") Long recptId)
    {
        return AjaxResult.success(wmItemRecptService.selectWmItemRecptByRecptId(recptId));
    }


    /**
     * 删除物料入库单
     */
    @ApiOperation("删除采购入库单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:remove')")
    @Log(title = "物料入库单", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{recptIds}")
    public AjaxResult remove(@PathVariable Long[] recptIds)
    {
        for (Long id:
                recptIds
        ) {
            WmItemRecpt itemRecpt = wmItemRecptService.selectWmItemRecptByRecptId(id);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(itemRecpt.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            wmItemRecptLineService.deleteByRecptId(id);
        }

        return toAjax(wmItemRecptService.deleteWmItemRecptByRecptIds(recptIds));
    }

    /**
     * 执行入库
     * @return
     */
    @ApiOperation("执行入库接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{recptId}")
    public AjaxResult execute(@PathVariable Long recptId){

        WmItemRecpt recpt = wmItemRecptService.selectWmItemRecptByRecptId(recptId);

        //构造Transaction事务，并执行库存更新逻辑
        List<ItemRecptTxBean> beans = wmItemRecptService.getTxBeans(recptId);

        //调用库存核心
        storageCoreService.processItemRecpt(beans);

        //更新单据状态
        recpt.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmItemRecptService.updateWmItemRecpt(recpt);

        //更新关联的到货通知单的状态（这里不进行数量的验证，只要关联就人为对应的单据已完成）
        WmArrivalNotice notice = wmArrivalNoticeService.selectWmArrivalNoticeByNoticeId(recpt.getNoticeId());
        if(StringUtils.isNotNull(notice)){
            notice.setStatus(UserConstants.ORDER_STATUS_FINISHED);
            wmArrivalNoticeService.updateWmArrivalNotice(notice);
        }

        return AjaxResult.success();
    }


}
