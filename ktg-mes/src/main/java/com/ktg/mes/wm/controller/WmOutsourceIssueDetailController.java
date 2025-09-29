package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ktg.mes.wm.domain.WmOutsourceIssueDetail;
import com.ktg.mes.wm.service.IWmOutsourceIssueDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 外协领料单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-04-12
 */
@RestController
@RequestMapping("/mes/wm/outsourceissuedetail")
public class WmOutsourceIssueDetailController extends BaseController
{
    @Autowired
    private IWmOutsourceIssueDetailService wmOutsourceIssueDetailService;

    /**
     * 查询外协领料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        startPage();
        List<WmOutsourceIssueDetail> list = wmOutsourceIssueDetailService.selectWmOutsourceIssueDetailList(wmOutsourceIssueDetail);
        return getDataTable(list);
    }

    /**
     * 导出外协领料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:export')")
    @Log(title = "外协领料单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        List<WmOutsourceIssueDetail> list = wmOutsourceIssueDetailService.selectWmOutsourceIssueDetailList(wmOutsourceIssueDetail);
        ExcelUtil<WmOutsourceIssueDetail> util = new ExcelUtil<WmOutsourceIssueDetail>(WmOutsourceIssueDetail.class);
        util.exportExcel(response, list, "外协领料单明细数据");
    }

    /**
     * 获取外协领料单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmOutsourceIssueDetailService.selectWmOutsourceIssueDetailByDetailId(detailId));
    }

    /**
     * 新增外协领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:add')")
    @Log(title = "外协领料单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        return toAjax(wmOutsourceIssueDetailService.insertWmOutsourceIssueDetail(wmOutsourceIssueDetail));
    }

    /**
     * 修改外协领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:edit')")
    @Log(title = "外协领料单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        return toAjax(wmOutsourceIssueDetailService.updateWmOutsourceIssueDetail(wmOutsourceIssueDetail));
    }

    /**
     * 删除外协领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissuedetail:remove')")
    @Log(title = "外协领料单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmOutsourceIssueDetailService.deleteWmOutsourceIssueDetailByDetailIds(detailIds));
    }
}
