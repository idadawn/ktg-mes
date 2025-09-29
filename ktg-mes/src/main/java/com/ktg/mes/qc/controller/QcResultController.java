package com.ktg.mes.qc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.qc.domain.*;
import com.ktg.mes.qc.service.*;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
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
 * 检测结果记录Controller
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
@RestController
@RequestMapping("/mes/qc/qcresult")
public class QcResultController extends BaseController
{
    @Autowired
    private IQcResultService qcResultService;

    @Autowired
    private IQcResultDetailService qcResultDetailService;

    @Autowired
    private IQcIndexService qcIndexService;

    @Autowired
    private IQcIqcService iqcService;

    @Autowired
    private IQcIpqcService pqcService;

    @Autowired
    private IQcOqcService oqcService;

    @Autowired
    private IQcRqcService rqcService;


    /**
     * 查询检测结果记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcResult qcResult)
    {
        startPage();
        List<QcResult> list = qcResultService.selectQcResultList(qcResult);
        return getDataTable(list);
    }

    /**
     * 导出检测结果记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:export')")
    @Log(title = "检测结果记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcResult qcResult)
    {
        List<QcResult> list = qcResultService.selectQcResultList(qcResult);
        ExcelUtil<QcResult> util = new ExcelUtil<QcResult>(QcResult.class);
        util.exportExcel(response, list, "检测结果记录数据");
    }

    /**
     * 获取检测结果记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:query')")
    @GetMapping(value = "/{resultId}")
    public AjaxResult getInfo(@PathVariable("resultId") Long resultId)
    {
        return AjaxResult.success(qcResultService.selectQcResultByResultId(resultId));
    }

    /**
     * 新增检测结果记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:add')")
    @Log(title = "检测结果记录", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody QcResult qcResult)
    {
        List<QcResultDetail> items = qcResult.getItems();
        if(CollectionUtils.isEmpty(items)){
            return AjaxResult.error("检测项以及对应的结果值不能为空!");
        }

        switch (qcResult.getSourceDocType()){
            case UserConstants.QC_TYPE_IQC:
                QcIqc iqc = iqcService.selectQcIqcByIqcId(qcResult.getSourceDocId());
                if(!StringUtils.isNotNull(iqc)){
                    return AjaxResult.error("检测结果对应的来料检验单不存在!");
                }

                qcResult.setSourceDocCode(iqc.getIqcCode());
                qcResult.setSourceDocName(iqc.getIqcName());
                qcResult.setItemId(iqc.getItemId());
                qcResult.setItemCode(iqc.getItemCode());
                qcResult.setItemName(iqc.getItemName());
                qcResult.setSpecification(iqc.getSpecification());
                qcResult.setUnitOfMeasure(iqc.getUnitOfMeasure());
                break;
            case UserConstants.QC_TYPE_PQC:
                QcIpqc pqc = pqcService.selectQcIpqcByIpqcId(qcResult.getSourceDocId());
                if(!StringUtils.isNotNull(pqc)){
                    return AjaxResult.error("检测结果对应的过程检验单不存在!");
                }
                qcResult.setSourceDocCode(pqc.getIpqcCode());
                qcResult.setSourceDocName(pqc.getIpqcName());
                qcResult.setItemId(pqc.getItemId());
                qcResult.setItemCode(pqc.getItemCode());
                qcResult.setItemName(pqc.getItemName());
                qcResult.setSpecification(pqc.getSpecification());
                qcResult.setUnitOfMeasure(pqc.getUnitOfMeasure());
                break;
            case UserConstants.QC_TYPE_OQC:
                QcOqc oqc = oqcService.selectQcOqcByOqcId(qcResult.getSourceDocId());
                if(!StringUtils.isNotNull(oqc)){
                    return AjaxResult.error("检测结果对应的出货检验单不存在!");
                }
                qcResult.setSourceDocCode(oqc.getOqcCode());
                qcResult.setSourceDocName(oqc.getOqcName());
                qcResult.setItemId(oqc.getItemId());
                qcResult.setItemCode(oqc.getItemCode());
                qcResult.setItemName(oqc.getItemName());
                qcResult.setSpecification(oqc.getSpecification());
                qcResult.setUnitOfMeasure(oqc.getUnitOfMeasure());
                break;
            case UserConstants.QC_TYPE_RQC:
                 QcRqc rqc = rqcService.selectQcRqcByRqcId(qcResult.getSourceDocId());
                if(!StringUtils.isNotNull(rqc)){
                    return AjaxResult.error("检测结果对应的退料单不存在!");
                }
                qcResult.setSourceDocCode(rqc.getRqcCode());
                qcResult.setSourceDocName(rqc.getRqcName());
                qcResult.setItemId(rqc.getItemId());
                qcResult.setItemCode(rqc.getItemCode());
                qcResult.setItemName(rqc.getItemName());
                qcResult.setSpecification(rqc.getSpecification());
                qcResult.setUnitOfMeasure(rqc.getUnitOfMeasure());
                break;
            default:
                return AjaxResult.error("不支持的来源单据类型!请检查sourceDocType参数!");
        }

        qcResultService.insertQcResult(qcResult);
        for(QcResultDetail detail : items){
            detail.setResultId(qcResult.getResultId());
            if(StringUtils.isNotNull(detail.getIndexId())){
                QcIndex index = qcIndexService.selectQcIndexByIndexId(detail.getIndexId());
                if(!StringUtils.isNotNull(index)){
                    return AjaxResult.error("检测项：【"+detail.getIndexName()+"】在系统中不存在，请检查检测项设置；重新编辑检测模板，再重新起草此检测单！");
                }
            }
            qcResultDetailService.insertQcResultDetail(detail);
        }
        return AjaxResult.success(qcResult);
    }

    /**
     * 修改检测结果记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:edit')")
    @Log(title = "检测结果记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody QcResult qcResult)
    {
        List<QcResultDetail> items = qcResult.getItems();
        if(CollectionUtils.isEmpty(items)){
            return AjaxResult.error("检测项以及对应的结果值不能为空!");
        }

        for(QcResultDetail detail : items){
            qcResultDetailService.updateQcResultDetail(detail);
        }

        return toAjax(qcResultService.updateQcResult(qcResult));
    }

    /**
     * 删除检测结果记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:remove')")
    @Log(title = "检测结果记录", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{resultIds}")
    public AjaxResult remove(@PathVariable Long[] resultIds)
    {
        //先删除检测结果明细
        for(Long resultId :resultIds){
            qcResultDetailService.deleteQcResultDetailByResultId(resultId);
        }
        return toAjax(qcResultService.deleteQcResultByResultIds(resultIds));
    }
}
