package com.ktg.mes.qc.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.bean.BeanUtils;
import com.ktg.mes.qc.domain.*;
import com.ktg.mes.qc.service.IQcRqcLineService;
import com.ktg.mes.qc.service.IQcTemplateIndexService;
import com.ktg.mes.qc.service.IQcTemplateProductService;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
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
import com.ktg.mes.qc.service.IQcRqcService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 退料检验单Controller
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/mes/qc/rqc")
public class QcRqcController extends BaseController
{
    @Autowired
    private IQcRqcService qcRqcService;

    @Autowired
    private IQcRqcLineService qcRqcLineService;

    @Autowired
    private IQcTemplateProductService qcTemplateProductService;

    @Autowired
    private IWmRtIssueLineService wmRtIssueLineService;

    @Autowired
    private IWmRtIssueService wmRtIssueService;

    @Autowired
    private IWmRtSalesLineService wmRtSalesLineService;

    @Autowired
    private IWmRtSalesService wmRtSalesService;

    @Autowired
    private IWmBatchService wmBatchService;

    @Autowired
    private IQcTemplateIndexService qcTemplateIndexService;

    /**
     * 查询退料检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcRqc qcRqc)
    {
        startPage();
        List<QcRqc> list = qcRqcService.selectQcRqcList(qcRqc);
        return getDataTable(list);
    }

    /**
     * 导出退料检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:export')")
    @Log(title = "退料检验单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcRqc qcRqc)
    {
        List<QcRqc> list = qcRqcService.selectQcRqcList(qcRqc);
        ExcelUtil<QcRqc> util = new ExcelUtil<QcRqc>(QcRqc.class);
        util.exportExcel(response, list, "退料检验单数据");
    }

    /**
     * 获取退料检验单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:query')")
    @GetMapping(value = "/{rqcId}")
    public AjaxResult getInfo(@PathVariable("rqcId") Long rqcId)
    {
        return AjaxResult.success(qcRqcService.selectQcRqcByRqcId(rqcId));
    }

    /**
     * 新增退料检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:add')")
    @Log(title = "退料检验单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody QcRqc qcRqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcRqcService.checkCodeUnique(qcRqc))){
            return AjaxResult.error("单据编号已存在！");
        }

        QcTemplateProduct param = new QcTemplateProduct();
        param.setItemId(qcRqc.getItemId());
        List<QcTemplateProduct> templates = qcTemplateProductService.selectQcTemplateProductList(param);
        if(CollUtil.isNotEmpty(templates)){
            qcRqc.setTemplateId(templates.get(0).getTemplateId());
        }else{
            return AjaxResult.error("当前物资未配置检测模板！");
        }

        //检测数量效验
        if((qcRqc.getQuantityUnqualified() == null || qcRqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) <= 0)&&
                (qcRqc.getQuantityQualified() == null || qcRqc.getQuantityQualified().compareTo(BigDecimal.ZERO) <= 0)){
            return AjaxResult.error("请填写合格品/不合格品数量！");
        }

        if(qcRqc.getQuantityCheck().compareTo(qcRqc.getQuantityQualified().add(qcRqc.getQuantityUnqualified()))!=0){
            return AjaxResult.error("检测数量与合格品/不合格品总和数量不匹配！");
        }

        qcRqcService.insertQcRqc(qcRqc);

        generateLine(qcRqc);

        return AjaxResult.success(qcRqc.getRqcId());
    }

    /**
     * 修改退料检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:edit')")
    @Log(title = "退料检验单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody QcRqc qcRqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcRqcService.checkCodeUnique(qcRqc))){
            return AjaxResult.error("单据编号已存在！");
        }

        QcTemplateProduct param = new QcTemplateProduct();
        param.setItemId(qcRqc.getItemId());
        List<QcTemplateProduct> templates = qcTemplateProductService.selectQcTemplateProductList(param);
        if(CollUtil.isNotEmpty(templates)){
            qcRqc.setTemplateId(templates.get(0).getTemplateId());
        }else{
            return AjaxResult.error("当前物资未配置检测模板！");
        }

        //检测数量效验
        if((qcRqc.getQuantityUnqualified() == null || qcRqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) <= 0)&&
                (qcRqc.getQuantityQualified() == null || qcRqc.getQuantityQualified().compareTo(BigDecimal.ZERO) <= 0)){
            return AjaxResult.error("请填写合格品/不合格品数量！");
        }

        if(qcRqc.getQuantityCheck().compareTo(qcRqc.getQuantityQualified().add(qcRqc.getQuantityUnqualified()))!=0){
            return AjaxResult.error("检测数量与合格品/不合格品总和数量不匹配！");
        }

        //完成状态时,处理来源单据的状态、数量、质量状态等数据
        if(UserConstants.ORDER_STATUS_FINISHED.equals(qcRqc.getStatus())){
            if(qcRqc.getSourceDocId() != null){
                //如果是生产退料单，则根据合格品数量、不合格品数量对来源单据行进行拆分
                if(UserConstants.SOURCE_DOC_TYPE_RTISSUE.equals(qcRqc.getSourceDocType())){
                    //1.获取生产退料单行
                    WmRtIssueLine sourceLine =  wmRtIssueLineService.selectWmRtIssueLineByLineId(qcRqc.getSourceLineId());

                    //2.质量状态不同时，判断行是否需要生产一个新的批次;方法是调用批次管理的checkBatchCodeByBatchAndProperties方法。
                    /**
                     * 为了系统逻辑简单起见（做减法），默认质量状态不参与批次规则；如此一来合格品和不良品的批次还是沿用原有批次，在库存层面不进行区分。实际操作时由相关人员在线下进行标记和区分（例如打标记，将物资存放在独立的库区等）
                    if(qcRqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) > 0){
                        WmBatch batch = new WmBatch();
                        batch.setBatchId(qcRqc.getBatchId());
                        batch.setBatchCode(qcRqc.getBatchCode());
                        batch.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                        batch = wmBatchService.checkBatchCodeByBatchAndProperties(batch);
                        WmRtIssueLine line = new WmRtIssueLine();
                        try {
                            BeanUtils.copyProperties(sourceLine, line);
                        } catch (Exception e) {
                            return AjaxResult.error("拆分行数据异常！");
                        }
                        line.setBatchId(batch.getBatchId());
                        line.setBatchCode(batch.getBatchCode());
                        line.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                        line.setQuantityRt(qcRqc.getQuantityUnqualified());
                        wmRtIssueLineService.insertWmRtIssueLine(line);
                    }
                     **/
                    if(qcRqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) == 0){
                        //直接更新生产退料单行的质量状态为合格
                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                    }else if(qcRqc.getQuantityQualified().compareTo(BigDecimal.ZERO) == 0){
                        //直接更新生产退料单行的质量状态为不合格
                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                    } else{
                        //将生产退料单的待检测行拆分成两行，一个不合格品，一个合格品，批次一致
                        WmRtIssueLine line = new WmRtIssueLine();
                        try {
                            BeanUtils.copyBeanProp(line,sourceLine);
                        } catch (Exception e) {
                            return AjaxResult.error("拆分行数据异常！");
                        }
                        line.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                        line.setQuantityRt(qcRqc.getQuantityUnqualified());
                        wmRtIssueLineService.insertWmRtIssueLine(line);

                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                        sourceLine.setQuantityRt(qcRqc.getQuantityQualified());
                        wmRtIssueLineService.updateWmRtIssueLine(sourceLine);
                    }

                    //3.判断生产退料单行上是否还有待检验的物资，如果没有，则将生产退料单的状态设置为待上架
                    WmRtIssueLine paramLine = new WmRtIssueLine();
                    paramLine.setRtId(qcRqc.getSourceDocId());
                    paramLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
                    List<WmRtIssueLine> lines = wmRtIssueLineService.selectWmRtIssueLineList(paramLine);
                    if(CollUtil.isEmpty(lines)){
                        WmRtIssue issue = wmRtIssueService.selectWmRtIssueByRtId(qcRqc.getSourceDocId());
                        issue.setStatus(UserConstants.RTISSUE_STATUS_UNSTOCK);
                        wmRtIssueService.updateWmRtIssue(issue);
                    }
                } else if(UserConstants.SOURCE_DOC_TYPE_RTSALSE.equals(qcRqc.getSourceDocType())){
                    WmRtSalesLine sourceLine =  wmRtSalesLineService.selectWmRtSalesLineByLineId(qcRqc.getSourceLineId());
                    if(qcRqc.getQuantityUnqualified().compareTo(BigDecimal.ZERO) == 0){
                        //直接更新销售退料单行的质量状态为合格
                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                    }else if(qcRqc.getQuantityQualified().compareTo(BigDecimal.ZERO) == 0) {
                        //直接更新销售退料单行的质量状态为不合格
                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                    }else{
                        WmRtSalesLine line = new WmRtSalesLine();
                        try {
                            BeanUtils.copyBeanProp(line,sourceLine);
                        } catch (Exception e) {
                            return AjaxResult.error("拆分行数据异常！");
                        }
                        line.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                        line.setQuantityRted(qcRqc.getQuantityUnqualified());
                        wmRtSalesLineService.insertWmRtSalesLine(line);

                        sourceLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                        sourceLine.setQuantityRted(qcRqc.getQuantityQualified());
                        wmRtSalesLineService.updateWmRtSalesLine(sourceLine);
                    }

                    WmRtSalesLine paramLine = new WmRtSalesLine();
                    paramLine.setRtId(qcRqc.getSourceDocId());
                    paramLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
                    List<WmRtSalesLine> lines = wmRtSalesLineService.selectWmRtSalesLineList(paramLine);
                    if(CollUtil.isEmpty(lines)){
                        WmRtSales sales = wmRtSalesService.selectWmRtSalesByRtId(qcRqc.getSourceDocId());
                        sales.setStatus(UserConstants.RT_SALES_STATUS_UNSTOCK);
                        wmRtSalesService.updateWmRtSales(sales);
                    }
                } else{
                    return AjaxResult.error("来源单据类型不正确！");
                }
            }
        }
        return toAjax(qcRqcService.updateQcRqc(qcRqc));
    }

    /**
     * 删除退料检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:remove')")
    @Log(title = "退料检验单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{rqcIds}")
    public AjaxResult remove(@PathVariable Long[] rqcIds)
    {
        for (Long rqcId:rqcIds){
                QcRqc qcRqc = qcRqcService.selectQcRqcByRqcId(rqcId);
                if(!UserConstants.ORDER_STATUS_PREPARE.equals(qcRqc.getStatus())){
                    return AjaxResult.error("只能删除草稿状态的单据！");
                }
                qcRqcLineService.deleteQcRqcLineByRqcId(rqcId);
        }

        return toAjax(qcRqcService.deleteQcRqcByRqcIds(rqcIds));
    }

    /**
     * 根据头信息生成行信息
     * @param rqc
     */
    private void generateLine(QcRqc rqc){
        QcTemplateIndex param = new QcTemplateIndex();
        param.setTemplateId(rqc.getTemplateId());
        List<QcTemplateIndex > indexs = qcTemplateIndexService.selectQcTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (QcTemplateIndex index:indexs
            ) {
                QcRqcLine line = new QcRqcLine();
                line.setRqcId(rqc.getRqcId());
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
                line.setCrQuantity(BigDecimal.ZERO);
                line.setMajQuantity(BigDecimal.ZERO);
                line.setMajQuantity(BigDecimal.ZERO);
                qcRqcLineService.insertQcRqcLine(line);
            }
        }
    }
}
