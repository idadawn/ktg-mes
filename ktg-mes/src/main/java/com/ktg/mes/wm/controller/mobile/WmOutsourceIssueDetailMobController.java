package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.wm.domain.WmOutsourceIssueDetail;
import com.ktg.mes.wm.service.IWmOutsourceIssueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/mobile/wm/outsourceissuedetail")
public class WmOutsourceIssueDetailMobController extends BaseController {

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
