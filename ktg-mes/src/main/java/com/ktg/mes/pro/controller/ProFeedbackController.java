package com.ktg.mes.pro.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollectionUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.md.service.IMdProductBomService;
import com.ktg.mes.md.service.IMdWorkstationService;
import com.ktg.mes.pro.domain.*;
import com.ktg.mes.pro.service.IProRouteProcessService;
import com.ktg.mes.pro.service.IProTaskService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.ItemConsumeTxBean;
import com.ktg.mes.wm.domain.tx.ProductProductTxBean;
import com.ktg.mes.wm.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.pro.service.IProFeedbackService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产报工记录Controller
 * 
 * @author yinjinlu
 * @date 2022-07-10
 */
@RestController
@RequestMapping("/mes/pro/feedback")
public class ProFeedbackController extends BaseController
{
    @Autowired
    private IProFeedbackService proFeedbackService;

    @Autowired
    private IProTaskService proTaskService;

    @Autowired
    private IProRouteProcessService proRouteProcessService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IMdProductBomService mdProductBomService;

    @Autowired
    private IWmItemConsumeService wmItemConsumeService;

    @Autowired
    private IWmItemConsumeLineService wmItemConsumeLineService;

    @Autowired
    private IWmItemConsumeDetailService wmItemConsumeDetailService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    @Autowired
    private IWmProductProduceService wmProductProduceService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询生产报工记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProFeedback proFeedback)
    {
        startPage();
        List<ProFeedback> list = proFeedbackService.selectProFeedbackList(proFeedback);
        return getDataTable(list);
    }

    /**
     * 导出生产报工记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:export')")
    @Log(title = "生产报工记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProFeedback proFeedback)
    {
        List<ProFeedback> list = proFeedbackService.selectProFeedbackList(proFeedback);
        ExcelUtil<ProFeedback> util = new ExcelUtil<ProFeedback>(ProFeedback.class);
        util.exportExcel(response, list, "生产报工记录数据");
    }

    /**
     * 获取生产报工记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proFeedbackService.selectProFeedbackByRecordId(recordId));
    }

    /**
     * 新增生产报工记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:add')")
    @Log(title = "生产报工记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProFeedback proFeedback)
    {
        MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(proFeedback.getWorkstationId());
        if(StringUtils.isNotNull(workstation)){
            proFeedback.setProcessId(workstation.getProcessId());
            proFeedback.setProcessCode(workstation.getProcessCode());
            proFeedback.setProcessName(workstation.getProcessName());
        }else {
            return AjaxResult.error("当前生产任务对应的工作站不存在！");
        }
        //检查对应的工艺路线和工序配置
        if(StringUtils.isNotNull(proFeedback.getRouteId())&& StringUtils.isNotNull(proFeedback.getProcessId())){
            ProRouteProcess param = new ProRouteProcess();
            param.setRouteId(proFeedback.getRouteId());
            param.setProcessId(proFeedback.getProcessId());
            List<ProRouteProcess> processes = proRouteProcessService.selectProRouteProcessList(param);
            if(CollectionUtil.isEmpty(processes)){
                return AjaxResult.error("未找到生产任务对应的工艺工序配置！");
            }
        }else {
            return AjaxResult.error("当前生产任务对应的工艺工序配置无效！");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(proFeedback.getWorkorderId());

        ProTask task = proTaskService.selectProTaskByTaskId(proFeedback.getTaskId());

        if(UserConstants.ORDER_STATUS_FINISHED.equals(task.getStatus()) || UserConstants.ORDER_STATUS_FINISHED.equals(workorder.getStatus())){
            return AjaxResult.error("当前生产任务的状态为已完成，不能继续报工！");
        }

        //检查数量
        if(UserConstants.YES.equals(proFeedback.getIsCheck())){
            if(!StringUtils.isNotNull(proFeedback.getQuantityFeedback()) || proFeedback.getQuantityFeedback().compareTo(BigDecimal.ZERO) <= 0 ){
                return AjaxResult.error("报工数量必须大于0!");
            }
        }else {
            if(!StringUtils.isNotNull(proFeedback.getQuantityQualified()) || !StringUtils.isNotNull(proFeedback.getQuantityUnquanlified()) || proFeedback.getQuantityQualified().add(proFeedback.getQuantityUnquanlified()).compareTo(BigDecimal.ZERO) <= 0){
                return AjaxResult.error("请输入合格品和不良品数量！");
            }
        }
        String feedbackCode = autoCodeUtil.genSerialCode(UserConstants.FEEDBACK_CODE,"");
        proFeedback.setFeedbackCode(feedbackCode);
        proFeedback.setCreateBy(getUsername());
        return toAjax(proFeedbackService.insertProFeedback(proFeedback));
    }

    /**
     * 修改生产报工记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:edit')")
    @Log(title = "生产报工记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProFeedback proFeedback)
    {
        MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(proFeedback.getWorkstationId());
        if(StringUtils.isNotNull(workstation)){
            proFeedback.setProcessId(workstation.getProcessId());
            proFeedback.setProcessCode(workstation.getProcessCode());
            proFeedback.setProcessName(workstation.getProcessName());
        }else {
            return AjaxResult.error("当前生产任务对应的工作站不存在！");
        }

        //检查对应的工艺路线和工序配置
        if(StringUtils.isNotNull(proFeedback.getRouteId())&& StringUtils.isNotNull(proFeedback.getProcessId())){
            ProRouteProcess param = new ProRouteProcess();
            param.setRouteId(proFeedback.getRouteId());
            param.setProcessId(proFeedback.getProcessId());
            List<ProRouteProcess> processes = proRouteProcessService.selectProRouteProcessList(param);
            if(CollectionUtil.isEmpty(processes)){
                return AjaxResult.error("未找到生产任务对应的工艺工序配置！");
            }
        }else {
            return AjaxResult.error("当前生产任务对应的工艺工序配置无效！");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(proFeedback.getWorkorderId());

        ProTask task = proTaskService.selectProTaskByTaskId(proFeedback.getTaskId());

        if(UserConstants.ORDER_STATUS_FINISHED.equals(task.getStatus()) || UserConstants.ORDER_STATUS_FINISHED.equals(workorder.getStatus())){
            return AjaxResult.error("当前生产任务的状态为已完成，不能继续报工！");
        }

        //检查数量
        if(UserConstants.YES.equals(proFeedback.getIsCheck())){
            if(!StringUtils.isNotNull(proFeedback.getQuantityFeedback()) || proFeedback.getQuantityFeedback().compareTo(BigDecimal.ZERO) <= 0 ){
                return AjaxResult.error("报工数量必须大于0!");
            }
        }else {
            if(!StringUtils.isNotNull(proFeedback.getQuantityQualified()) || !StringUtils.isNotNull(proFeedback.getQuantityUnquanlified()) || proFeedback.getQuantityQualified().add(proFeedback.getQuantityUnquanlified()).compareTo(BigDecimal.ZERO) <= 0){
                return AjaxResult.error("请输入合格品和不良品数量！");
            }
        }

        return toAjax(proFeedbackService.updateProFeedback(proFeedback));
    }

    /**
     * 删除生产报工记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:remove')")
    @Log(title = "生产报工记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proFeedbackService.deleteProFeedbackByRecordIds(recordIds));
    }

    /**
     * 执行报工
     * 1.生成物资消耗单
     * 2.产品产出单处理(是否关键工序判断)
     *   2.1生成产品产出单头，
     *   2.2生成产品产出单的行
     *      如果不需要检验，生成产出单行，根据合格品和不合格品数量设置行的质量状态为OK/NG
     *      如果需要检验，生成产出单行，设置行的质量状态为待检验，设置单据状态为待检验(等待质量检验完成，更新单据状态和行上的质量状态)
     * 3.执行物料消耗和产品产出动作
     *   3.1更新单据状态为已完成
     *   3.2更新生产任务的生产数量和状态
     *   3.3更新生产工单的生产数量和状态
     * @param recordId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:feedback:edit')")
    @Log(title = "生产报工执行", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{recordId}")
    public AjaxResult execute(@PathVariable("recordId") Long recordId){

        if(!StringUtils.isNotNull(recordId)){
            return AjaxResult.error("请先保存单据");
        }

        ProFeedback feedback= proFeedbackService.selectProFeedbackByRecordId(recordId);
        if(feedback.getQuantityFeedback().compareTo(BigDecimal.ZERO) !=1){
            return AjaxResult.error("报工数量必须大于0");
        }

        ProTask task = proTaskService.selectProTaskByTaskId(feedback.getTaskId());

        //判断当前生产任务的状态，如果已经完成则不能再报工
        if(UserConstants.FEEDBACK_STATUS_FINISHED.equals(task.getStatus())){
            return AjaxResult.error("当前生产工单的状态为已完成，不能继续报工，请刷新生产任务列表！");
        }

        //仍旧有待检数量时不能执行
        if(StringUtils.isNotNull(feedback.getQuantityUncheck()) && feedback.getQuantityUncheck().compareTo(BigDecimal.ZERO) >0){
            return  AjaxResult.error("当前报工单未完成检验（待检数量大于0），无法执行报工！");
        }

        //根据当前工序的物料BOM配置，进行物料消耗
        //先生成消耗单
        WmItemConsume itemConsume = wmItemConsumeService.generateItemConsume(feedback);
        if(StringUtils.isNotNull(itemConsume)){
            //再执行库存消耗动作
            executeItemConsume(itemConsume);
        }

        //如果是关键工序，判断当前物资是否需要进行检验。如果是，则生成产品产出单头和行（质量状态为待检测），更新报工单状态为待检测；如果不需要检验，则生成产品产出单头和行（质量状态为OK/NG），执行产品入库动作，更新报工单状态为已完成。
        if(proRouteProcessService.checkKeyProcess(feedback)){

            //生成产品产出记录单
            WmProductProduce productRecord = wmProductProduceService.generateProductProduce(feedback);

            if(UserConstants.YES.equals(feedback.getIsCheck())){
                feedback.setStatus(UserConstants.FEEDBACK_STATUS_UNCHECK); //待检测状态
                proFeedbackService.updateProFeedback(feedback);

                return AjaxResult.success("报工成功，请等待质量检验完成！");
            }else{
                //执行产品产出入线边库
                wmProductProduceService.executeProductProduce(productRecord);
                //更新生产任务和生产工单的进度
                proFeedbackService.updateProTaskAndWorkorderByFeedback(feedback);
            }
        }

        //更新报工单的状态
        feedback.setStatus(UserConstants.FEEDBACK_STATUS_FINISHED);
        proFeedbackService.updateProFeedback(feedback);

        return AjaxResult.success();
    }


    /**
     * 执行物料消耗库存动作
     * @param record
     */
    private void executeItemConsume(WmItemConsume record){
        //需要在此处进行分批次领料的线边库扣减
        List<ItemConsumeTxBean> beans = wmItemConsumeService.getTxBeans(record.getRecordId());
        storageCoreService.processItemConsume(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmItemConsumeService.updateWmItemConsume(record);
    }




}
