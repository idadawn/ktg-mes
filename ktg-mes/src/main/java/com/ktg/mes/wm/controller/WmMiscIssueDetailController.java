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
import com.ktg.mes.wm.domain.WmMiscIssueDetail;
import com.ktg.mes.wm.service.IWmMiscIssueDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 杂项出库单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscissuedetail")
public class WmMiscIssueDetailController extends BaseController
{
    @Autowired
    private IWmMiscIssueDetailService wmMiscIssueDetailService;

    /**
     * 查询杂项出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscIssueDetail wmMiscIssueDetail)
    {
        startPage();
        List<WmMiscIssueDetail> list = wmMiscIssueDetailService.selectWmMiscIssueDetailList(wmMiscIssueDetail);
        return getDataTable(list);
    }

    /**
     * 导出杂项出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:export')")
    @Log(title = "杂项出库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscIssueDetail wmMiscIssueDetail)
    {
        List<WmMiscIssueDetail> list = wmMiscIssueDetailService.selectWmMiscIssueDetailList(wmMiscIssueDetail);
        ExcelUtil<WmMiscIssueDetail> util = new ExcelUtil<WmMiscIssueDetail>(WmMiscIssueDetail.class);
        util.exportExcel(response, list, "杂项出库单明细数据");
    }

    /**
     * 获取杂项出库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmMiscIssueDetailService.selectWmMiscIssueDetailByDetailId(detailId));
    }

    /**
     * 新增杂项出库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:add')")
    @Log(title = "杂项出库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscIssueDetail wmMiscIssueDetail)
    {
        return toAjax(wmMiscIssueDetailService.insertWmMiscIssueDetail(wmMiscIssueDetail));
    }

    /**
     * 修改杂项出库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:edit')")
    @Log(title = "杂项出库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscIssueDetail wmMiscIssueDetail)
    {
        return toAjax(wmMiscIssueDetailService.updateWmMiscIssueDetail(wmMiscIssueDetail));
    }

    /**
     * 删除杂项出库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissuedetail:remove')")
    @Log(title = "杂项出库单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmMiscIssueDetailService.deleteWmMiscIssueDetailByDetailIds(detailIds));
    }
}
