package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
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
import com.ktg.mes.wm.domain.WmIssueDetail;
import com.ktg.mes.wm.service.IWmIssueDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产领料单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-04
 */
@RestController
@RequestMapping("/mes/wm/issuedetail")
public class WmIssueDetailController extends BaseController
{
    @Autowired
    private IWmIssueDetailService wmIssueDetailService;

    /**
     * 查询生产领料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueDetail wmIssueDetail)
    {
        startPage();
        List<WmIssueDetail> list = wmIssueDetailService.selectWmIssueDetailList(wmIssueDetail);
        return getDataTable(list);
    }

    /**
     * 导出生产领料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:export')")
    @Log(title = "生产领料单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmIssueDetail wmIssueDetail)
    {
        List<WmIssueDetail> list = wmIssueDetailService.selectWmIssueDetailList(wmIssueDetail);
        ExcelUtil<WmIssueDetail> util = new ExcelUtil<WmIssueDetail>(WmIssueDetail.class);
        util.exportExcel(response, list, "生产领料单明细数据");
    }

    /**
     * 获取生产领料单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmIssueDetailService.selectWmIssueDetailByDetailId(detailId));
    }

    /**
     * 新增生产领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueDetail wmIssueDetail)
    {
        if(BigDecimal.ZERO.compareTo(wmIssueDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        return toAjax(wmIssueDetailService.insertWmIssueDetail(wmIssueDetail));
    }

    /**
     * 修改生产领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueDetail wmIssueDetail)
    {
        if(BigDecimal.ZERO.compareTo(wmIssueDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        return toAjax(wmIssueDetailService.updateWmIssueDetail(wmIssueDetail));
    }

    /**
     * 删除生产领料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmIssueDetailService.deleteWmIssueDetailByDetailIds(detailIds));
    }
}
