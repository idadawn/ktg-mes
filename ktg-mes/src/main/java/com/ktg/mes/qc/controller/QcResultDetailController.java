package com.ktg.mes.qc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ktg.mes.qc.domain.QcResultDetail;
import com.ktg.mes.qc.service.IQcResultDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 检测结果明细记录Controller
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
@RestController
@RequestMapping("/mes/qc/qcresultdetail")
public class QcResultDetailController extends BaseController
{
    @Autowired
    private IQcResultDetailService qcResultDetailService;

    /**
     * 查询检测结果明细记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcResultDetail qcResultDetail)
    {
        startPage();
        List<QcResultDetail> list = qcResultDetailService.selectQcResultDetailList(qcResultDetail);
        return getDataTable(list);
    }

    /**
     *
     * @param qcResultDetail
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:list')")
    @GetMapping("/listDetails")
    public AjaxResult listDetails(QcResultDetail qcResultDetail){
        List<QcResultDetail> details = null;
        if(StringUtils.isNotNull(qcResultDetail.getQcType())){
            switch (qcResultDetail.getQcType()){
                case UserConstants.QC_TYPE_IQC:
                    details = qcResultDetailService.selectQcResultDetailByResultIdAndIQCId(qcResultDetail);
                    break;
                case UserConstants.QC_TYPE_PQC:
                    details = qcResultDetailService.selectQcResultDetailByResultIdAndIPQCId(qcResultDetail);
                    break;
                case UserConstants.QC_TYPE_OQC:
                    details = qcResultDetailService.selectQcResultDetailByResultIdAndOQCId(qcResultDetail);
                    break;
                case UserConstants.QC_TYPE_RQC:
                    details = qcResultDetailService.selectQcResultDetailByResultIdAndRQCId(qcResultDetail);
                    break;
                default:
                    return AjaxResult.error("来源单据类型错误!");
            }
        }
        return AjaxResult.success(details);
    }


    /**
     * 导出检测结果明细记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:export')")
    @Log(title = "检测结果明细记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcResultDetail qcResultDetail)
    {
        List<QcResultDetail> list = qcResultDetailService.selectQcResultDetailList(qcResultDetail);
        ExcelUtil<QcResultDetail> util = new ExcelUtil<QcResultDetail>(QcResultDetail.class);
        util.exportExcel(response, list, "检测结果明细记录数据");
    }

    /**
     * 获取检测结果明细记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(qcResultDetailService.selectQcResultDetailByDetailId(detailId));
    }

    /**
     * 新增检测结果明细记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:add')")
    @Log(title = "检测结果明细记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcResultDetail qcResultDetail)
    {
        return toAjax(qcResultDetailService.insertQcResultDetail(qcResultDetail));
    }

    /**
     * 修改检测结果明细记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:edit')")
    @Log(title = "检测结果明细记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcResultDetail qcResultDetail)
    {
        return toAjax(qcResultDetailService.updateQcResultDetail(qcResultDetail));
    }

    /**
     * 删除检测结果明细记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcresult:remove')")
    @Log(title = "检测结果明细记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(qcResultDetailService.deleteQcResultDetailByDetailIds(detailIds));
    }
}
