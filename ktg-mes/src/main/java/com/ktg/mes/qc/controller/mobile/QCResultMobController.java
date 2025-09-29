package com.ktg.mes.qc.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.bean.BeanUtils;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.qc.domain.*;
import com.ktg.mes.qc.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 质检记录Controller
 *
 * @author yinjinlu
 * @date 2023-11-19
 */
@RestController
@RequestMapping("/mobile/qc/qcresult")
public class QCResultMobController extends BaseController
{
    @Autowired
    private IQcResultService qcResultService;

    @Autowired
    private IQcResultDetailService qcResultDetailService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    @Autowired
    private IQcIqcService qcIqcService;

    @Autowired
    private IQcIqcLineService qcIqcLineService;

    @Autowired
    private IQcIpqcService qcIpqcService;

    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    @Autowired
    private IQcOqcService qcOqcService;

    @Autowired
    private IQcOqcLineService qcOqcLineService;


    /**
     * 查询质检记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcResult qcResult)
    {
        startPage();
        List<QcResult> list = qcResultService.selectQcResultList(qcResult);
        return getDataTable(list);
    }

    /**
     * 导出质检记录列表
     */
    @Log(title = "质检记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcResult qcResult)
    {
        List<QcResult> list = qcResultService.selectQcResultList(qcResult);
        ExcelUtil<QcResult> util = new ExcelUtil<QcResult>(QcResult.class);
        util.exportExcel(response, list, "质检记录数据");
    }

    /**
     * 获取质检记录详细信息
     */
    @GetMapping(value = "/{resultId}")
    public AjaxResult getInfo(@PathVariable("resultId") Long resultId)
    {
        return AjaxResult.success(qcResultService.selectQcResultByResultId(resultId));
    }

    /**
     * 新增质检记录
     */
    @Log(title = "质检记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcResult qcResult)
    {
        //质量的质检记录编码自动生成
        String resultCode = autoCodeUtil.genSerialCode(UserConstants.QC_RESULT_CODE,null);
        qcResult.setResultCode(resultCode);

        //根据传入的sourceDocId和sourceDocType初始化其他信息
        if(StringUtils.isNull(qcResult.getSourceDocId()) || StringUtils.isNull(qcResult.getSourceDocType())){
            return AjaxResult.error("来源单据类型和ID不能为空！");
        }

        String sourceDocType = qcResult.getSourceDocType();
        Long sourceDocId = qcResult.getSourceDocId();
        Long resultId = -1L;
        switch (sourceDocType){
            case UserConstants.QC_TYPE_IQC:
                //根据iqcId查询对应的单据编号和名称
                QcIqc iqc = qcIqcService.selectQcIqcByIqcId(sourceDocId);
                if(StringUtils.isNotNull(iqc)){
                    qcResult.setSourceDocCode(iqc.getIqcCode());
                    qcResult.setSourceDocName(iqc.getIqcName());
                    qcResult.setItemId(iqc.getItemId());
                    qcResult.setItemName(iqc.getItemName());
                    qcResult.setItemCode(iqc.getItemCode());
                    qcResult.setSpecification(iqc.getSpecification());
                    qcResult.setUnitOfMeasure(iqc.getUnitOfMeasure());
                }

                qcResultService.insertQcResult(qcResult);
                resultId = qcResult.getResultId();
                generateIQCRecordIndex(resultId,sourceDocId);
                break;
            case UserConstants.QC_TYPE_PQC:
                QcIpqc ipqc = qcIpqcService.selectQcIpqcByIpqcId(sourceDocId);
                if(StringUtils.isNotNull(ipqc)){
                    qcResult.setSourceDocCode(ipqc.getIpqcCode());
                    qcResult.setSourceDocName(ipqc.getIpqcName());
                    qcResult.setItemId(ipqc.getItemId());
                    qcResult.setItemName(ipqc.getItemName());
                    qcResult.setItemCode(ipqc.getItemCode());
                    qcResult.setSpecification(ipqc.getSpecification());
                    qcResult.setUnitOfMeasure(ipqc.getUnitOfMeasure());
                }

                qcResultService.insertQcResult(qcResult);
                resultId = qcResult.getResultId();
                generateIPQCRecordIndex(resultId,sourceDocId);
                break;
            case UserConstants.QC_TYPE_OQC:
                QcOqc oqc = qcOqcService.selectQcOqcByOqcId(sourceDocId);
                if(StringUtils.isNotNull(oqc)){
                    qcResult.setSourceDocCode(oqc.getOqcCode());
                    qcResult.setSourceDocName(oqc.getOqcName());
                    qcResult.setItemId(oqc.getItemId());
                    qcResult.setItemName(oqc.getItemName());
                    qcResult.setItemCode(oqc.getItemCode());
                    qcResult.setSpecification(oqc.getSpecification());
                    qcResult.setUnitOfMeasure(oqc.getUnitOfMeasure());
                }

                qcResultService.insertQcResult(qcResult);
                resultId = qcResult.getResultId();
                generateOQCRecordIndex(resultId,sourceDocId);
                break;
            default:
                return AjaxResult.error("错误的检测单类型!");
        }
        return AjaxResult.success(qcResult);
    }

    /**
     * 修改质检记录
     */
    @Log(title = "质检记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcResult qcResult)
    {
        return toAjax(qcResultService.updateQcResult(qcResult));
    }

    /**
     * 删除质检记录
     */
    @Log(title = "质检记录", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{resultIds}")
    public AjaxResult remove(@PathVariable Long[] resultIds)
    {
        return toAjax(qcResultService.deleteQcResultByResultIds(resultIds));
    }

    /**
     * 根据来料检验单ID，自动生成对应的检测记录-检测项
     * @param resultId
     */
    private void generateIQCRecordIndex(Long resultId,Long iqcId){
        QcResultDetail qcResultDetail = new QcResultDetail();
        qcResultDetail.setResultId(resultId);
        qcResultDetail.setQcId(iqcId);
        List<QcResultDetail> details = qcResultDetailService.selectQcResultDetailByResultIdAndIQCId(qcResultDetail);

        if(!CollectionUtils.isEmpty(details)){
            for (QcResultDetail line: details
                 ) {
                line.setResultId(resultId);
                qcResultDetailService.insertQcResultDetail(line);
            }
        }
    }

    /**
     * 根据过程检验单ID自动生成对应的检测记录-检测项
     * @param resultId
     */
    private void generateIPQCRecordIndex(Long resultId,Long ipqcId){
        QcResultDetail qcResultDetail = new QcResultDetail();
        qcResultDetail.setResultId(resultId);
        qcResultDetail.setQcId(ipqcId);
        List<QcResultDetail> details = qcResultDetailService.selectQcResultDetailByResultIdAndIPQCId(qcResultDetail);

        if(!CollectionUtils.isEmpty(details)){
            for (QcResultDetail line: details
                 ) {
                line.setResultId(resultId);
                qcResultDetailService.insertQcResultDetail(line);
            }
        }
    }

    /**
     * 根据出厂检验单ID，自动生成对应的检测记录-检测项
     * @param resultId
     */
    private void generateOQCRecordIndex(Long resultId,Long oqcId){
        QcResultDetail qcResultDetail = new QcResultDetail();
        qcResultDetail.setResultId(resultId);
        qcResultDetail.setQcId(oqcId);
        List<QcResultDetail> details = qcResultDetailService.selectQcResultDetailByResultIdAndOQCId(qcResultDetail);

        if(!CollectionUtils.isEmpty(details)){
            for (QcResultDetail line: details
                 ) {
                line.setResultId(resultId);
                qcResultDetailService.insertQcResultDetail(line);
            }
        }
    }
}
