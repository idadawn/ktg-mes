package com.ktg.mes.qc.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.bean.BeanUtils;
import com.ktg.mes.pro.domain.ProFeedback;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProFeedbackService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.qc.domain.*;
import com.ktg.mes.qc.service.*;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.mapper.WmStorageAreaMapper;
import com.ktg.mes.wm.mapper.WmStorageLocationMapper;
import com.ktg.mes.wm.mapper.WmWarehouseMapper;
import com.ktg.mes.wm.service.IWmProductProduceDetailService;
import com.ktg.mes.wm.service.IWmProductProduceLineService;
import com.ktg.mes.wm.service.IWmProductProduceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 过程检验单Controller
 * 
 * @author yinjinlu
 * @date 2022-08-29
 */
@RestController
@RequestMapping("/mes/qc/ipqc")
public class QcIpqcController extends BaseController
{
    @Autowired
    private IQcIpqcService qcIpqcService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IQcTemplateIndexService qcTemplateIndexService;

    @Autowired
    private IQcTemplateService qcTemplateService;

    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    @Autowired
    private IQcDefectRecordService qcDefectRecordService;

    @Autowired
    private IProFeedbackService proFeedbackService;

    @Autowired
    private IWmProductProduceService productProduceService;

    @Autowired
    private IWmProductProduceLineService productProduceLineService;

    @Autowired
    private IWmProductProduceDetailService productProduceDetailService;

    @Autowired
    private WmWarehouseMapper wmWarehouseMapper;

    @Autowired
    private WmStorageLocationMapper wmStorageLocationMapper;

    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;


    /**
     * 查询过程检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIpqc qcIpqc)
    {
        startPage();
        List<QcIpqc> list = qcIpqcService.selectQcIpqcList(qcIpqc);
        return getDataTable(list);
    }

    /**
     * 导出过程检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:export')")
    @Log(title = "过程检验单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcIpqc qcIpqc)
    {
        List<QcIpqc> list = qcIpqcService.selectQcIpqcList(qcIpqc);
        ExcelUtil<QcIpqc> util = new ExcelUtil<QcIpqc>(QcIpqc.class);
        util.exportExcel(response, list, "过程检验单数据");
    }

    /**
     * 获取过程检验单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:query')")
    @GetMapping(value = "/{ipqcId}")
    public AjaxResult getInfo(@PathVariable("ipqcId") Long ipqcId)
    {
        return AjaxResult.success(qcIpqcService.selectQcIpqcByIpqcId(ipqcId));
    }

    /**
     * 新增过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:add')")
    @Log(title = "过程检验单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody QcIpqc qcIpqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIpqcService.checkIpqcCodeUnique(qcIpqc))){
            return AjaxResult.error("检测单编码已存在！");
        }

        //根据工单获取产品信息
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(qcIpqc.getWorkorderId());
        qcIpqc.setWorkorderId(workorder.getWorkorderId());
        qcIpqc.setWorkorderCode(workorder.getWorkorderCode());
        qcIpqc.setWorkorderName(workorder.getWorkorderName());
        qcIpqc.setItemId(workorder.getProductId());
        qcIpqc.setItemCode(workorder.getProductCode());
        qcIpqc.setItemName(workorder.getProductName());
        qcIpqc.setSpecification(workorder.getProductSpc());
        qcIpqc.setUnitOfMeasure(workorder.getUnitOfMeasure());

        //查询工序相关信息
        List<QcIpqc> infos = qcIpqcService.getProcessInfo(qcIpqc);
        if(!CollectionUtils.isEmpty(infos)&&infos.size() ==1){
            qcIpqc.setProcessId(infos.get(0).getProcessId());
            qcIpqc.setProcessCode(infos.get(0).getProcessCode());
            qcIpqc.setProcessName(infos.get(0).getProcessName());
        }

        //根据产品和检测类型获取检测模板
        QcTemplate param = new QcTemplate();
        param.setQcTypes(qcIpqc.getIpqcType());
        param.setItemId(workorder.getProductId());
        QcTemplate template = qcTemplateService.selectQcTemplateByProductAndQcType(param);
        if(StringUtils.isNotNull(template)){
            qcIpqc.setTemplateId(template.getTemplateId());
        }else{
            return AjaxResult.error("当前工单生产的产品未配置此类型的检验模板！");
        }

        //检测数量效验
        if((qcIpqc.getQuantityUnqualified() == null || qcIpqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) <= 0)&&
                (qcIpqc.getQuantityQualified() == null || qcIpqc.getQuantityQualified().compareTo(BigDecimal.ZERO) <= 0)){
            return AjaxResult.error("请填写合格品/不合格品数量！");
        }

        if(qcIpqc.getQuantityCheck().compareTo(qcIpqc.getQuantityQualified().add(qcIpqc.getQuantityUnqualified()))!=0){
            return AjaxResult.error("检测数量与合格品/不合格品总和数量不匹配！");
        }

        //先保存
        qcIpqcService.insertQcIpqc(qcIpqc);

        //生成行信息
        generateLine(qcIpqc);

        //将ID返回
        Long ipqcId = qcIpqc.getIpqcId();
        return AjaxResult.success(ipqcId);
    }

    /**
     * 修改过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:edit')")
    @Log(title = "过程检验单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody QcIpqc qcIpqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIpqcService.checkIpqcCodeUnique(qcIpqc))){
            return AjaxResult.error("检测单编码已存在！");
        }

        //对合格品和不合格品数量进行检查


        //根据工单获取产品信息
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(qcIpqc.getWorkorderId());
        qcIpqc.setWorkorderId(workorder.getWorkorderId());
        qcIpqc.setWorkorderCode(workorder.getWorkorderCode());
        qcIpqc.setWorkorderName(workorder.getWorkorderName());
        qcIpqc.setItemId(workorder.getProductId());
        qcIpqc.setItemCode(workorder.getProductCode());
        qcIpqc.setItemName(workorder.getProductName());
        qcIpqc.setSpecification(workorder.getProductSpc());
        qcIpqc.setUnitOfMeasure(workorder.getUnitOfMeasure());

        //查询工序相关信息
        List<QcIpqc> infos = qcIpqcService.getProcessInfo(qcIpqc);
        if(!CollectionUtils.isEmpty(infos)&&infos.size() ==1){
            qcIpqc.setProcessId(infos.get(0).getProcessId());
            qcIpqc.setProcessCode(infos.get(0).getProcessCode());
            qcIpqc.setProcessName(infos.get(0).getProcessName());
        }

        //根据产品和检测类型获取检测模板
        QcTemplate param = new QcTemplate();
        param.setQcTypes(qcIpqc.getIpqcType());
        param.setItemId(workorder.getProductId());
        QcTemplate template = qcTemplateService.selectQcTemplateByProductAndQcType(param);
        if(StringUtils.isNotNull(template)){
            qcIpqc.setTemplateId(template.getTemplateId());
        }else{
            return AjaxResult.error("当前工单生产的产品未配置此类型的检验模板！");
        }

        //检测数量效验
        if((qcIpqc.getQuantityUnqualified() == null || qcIpqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) <= 0)&&
                (qcIpqc.getQuantityQualified() == null || qcIpqc.getQuantityQualified().compareTo(BigDecimal.ZERO) <= 0)){
            return AjaxResult.error("请填写合格品/不合格品数量！");
        }

        if(qcIpqc.getQuantityCheck().compareTo(qcIpqc.getQuantityQualified().add(qcIpqc.getQuantityUnqualified()))!=0){
            return AjaxResult.error("检测数量与合格品/不合格品总和数量不匹配！");
        }

        //如果是完成单据则根据单据上的来源单据，更新对应的关联检验单信息
        if(UserConstants.ORDER_STATUS_FINISHED.equals(qcIpqc.getStatus())){

            if(UserConstants.SOURCE_DOC_TYPE_FEEDBACK.equals(qcIpqc.getSourceDocType())){
                //1.这里的sourceDocCode是报工单的编码；sourceDocId是产品产出单的ID；sourceDocLineID是产品产出单的行ID
                //2.根据合格品，不合格品数量对产品产出单行进行拆分；同时生成相应的明细行记录
                //3.执行产品产出，更新产品产出单状态为FINISHED
                //4.更新报工单的状态为FINISHED

                WmProductProduce produce = productProduceService.selectWmProductProduceByRecordId(qcIpqc.getSourceDocId());
                ProFeedback feedback =  proFeedbackService.selectProFeedbackByRecordId(produce.getFeedbackId());
                WmProductProduceLine line = productProduceLineService.selectWmProductProduceLineByLineId(qcIpqc.getSourceLineId());

                WmWarehouse warehouse = wmWarehouseMapper.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
                WmStorageLocation location = wmStorageLocationMapper.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
                WmStorageArea area = wmStorageAreaMapper.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);

                WmProductProduceDetail detail = new WmProductProduceDetail();
                detail.setRecordId(produce.getRecordId());
                detail.setItemId(line.getItemId());
                detail.setItemCode(line.getItemCode());
                detail.setItemName(line.getItemName());
                detail.setSpecification(line.getSpecification());
                detail.setUnitOfMeasure(line.getUnitOfMeasure());
                detail.setUnitName(line.getUnitName());
                detail.setQuantity(line.getQuantityProduce());
                detail.setBatchId(line.getBatchId());
                detail.setBatchCode(line.getBatchCode());
                detail.setWarehouseId(warehouse.getWarehouseId());
                detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
                detail.setWarehouseName(warehouse.getWarehouseName());
                detail.setLocationId(location.getLocationId());
                detail.setLocationCode(UserConstants.VIRTUAL_WS);
                detail.setLocationName(location.getLocationName());
                detail.setAreaId(area.getAreaId());
                detail.setAreaCode(UserConstants.VIRTUAL_WA);
                detail.setAreaName(area.getAreaName());


                if(BigDecimal.ZERO.compareTo(qcIpqc.getQuantityUnqualified())<0){
                    //不合格品数量大于0的情况下，生成一个新的产品产出单行，将质量状态设置为不合格
                    WmProductProduceLine unqualifiedLine = new WmProductProduceLine();
                    BeanUtils.copyBeanProp(unqualifiedLine,line);
                    unqualifiedLine.setQuantityProduce(qcIpqc.getQuantityUnqualified());
                    unqualifiedLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                    productProduceLineService.insertWmProductProduceLine(unqualifiedLine);
                    WmProductProduceDetail unqualifiedDetail = new WmProductProduceDetail();
                    BeanUtils.copyBeanProp(unqualifiedDetail,detail);
                    unqualifiedDetail.setLineId(unqualifiedLine.getLineId());
                    unqualifiedDetail.setQuantity(qcIpqc.getQuantityUnqualified());
                    productProduceDetailService.insertWmProductProduceDetail(unqualifiedDetail);

                    line.setQuantityProduce(qcIpqc.getQuantityQualified());
                    line.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                    productProduceLineService.updateWmProductProduceLine(line);
                    detail.setQuantity(qcIpqc.getQuantityQualified());
                    detail.setLineId(line.getLineId());
                    productProduceDetailService.insertWmProductProduceDetail(detail);
                }else{
                    line.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                    productProduceLineService.updateWmProductProduceLine(line);
                    detail.setLineId(line.getLineId());
                    productProduceDetailService.insertWmProductProduceDetail(detail);
                }

                //执行产品产出，跟新产品产出单状态为FINISHED
                productProduceService.executeProductProduce(produce);

                //更新生产任务和工单的进度
                proFeedbackService.updateProTaskAndWorkorderByFeedback(feedback);

                //更新报工单状态为FINISHED，根据工废和料废数量更新报工单上的工废和料废数量
                if(StringUtils.isNotNull(feedback)){
                    if(qcIpqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO)>0){
                        feedback.setQuantityLaborScrap(qcIpqc.getQuantityLaborScrap());
                        feedback.setQuantityMaterialScrap(qcIpqc.getQuantityMaterialScrap());
                        feedback.setQuantityOtherScrap(qcIpqc.getQuantityOtherScrap());
                    }
                    feedback.setStatus(UserConstants.FEEDBACK_STATUS_FINISHED);
                    proFeedbackService.updateProFeedback(feedback);
                }
            }
        }
        return toAjax(qcIpqcService.updateQcIpqc(qcIpqc));
    }

    /**
     * 删除过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:remove')")
    @Log(title = "过程检验单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{ipqcIds}")
    public AjaxResult remove(@PathVariable Long[] ipqcIds)
    {
        for (Long ipqcId: ipqcIds
             ) {
            QcIpqc ipqc = qcIpqcService.selectQcIpqcByIpqcId(ipqcId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(ipqc.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            qcIpqcLineService.deleteByIpqcId(ipqcId); //删除对应的行信息
            QcDefectRecord p2 = new QcDefectRecord();
            p2.setQcId(ipqcId);
            p2.setQcType(UserConstants.QC_TYPE_PQC);
            qcDefectRecordService.deleteByQcIdAndType(p2);//删除对应的缺陷记录
        }

        return toAjax(qcIpqcService.deleteQcIpqcByIpqcIds(ipqcIds));
    }


    /**
     * 根据头信息生成行信息
     * @param iqc
     */
    private void generateLine(QcIpqc iqc){
        QcTemplateIndex param = new QcTemplateIndex();
        param.setTemplateId(iqc.getTemplateId());
        List<QcTemplateIndex > indexs = qcTemplateIndexService.selectQcTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (QcTemplateIndex index:indexs
            ) {
                QcIpqcLine line = new QcIpqcLine();
                line.setIpqcId(iqc.getIpqcId());
                line.setIndexId(index.getIndexId());
                line.setIndexCode(index.getIndexCode());
                line.setIndexName(index.getIndexName());
                line.setIndexType(index.getIndexType());
                line.setQcTool(index.getQcTool());
                line.setCheckMethod(index.getCheckMethod());
                line.setStanderVal(index.getStanderVal());
                line.setUnitOfMeasure(index.getUnitOfMeasure());
                line.setThresholdMax(index.getThresholdMax());
                line.setThresholdMin(index.getThresholdMin());
                line.setCrQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                qcIpqcLineService.insertQcIpqcLine(line);
            }
        }
    }
}
