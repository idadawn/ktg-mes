package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.wm.domain.WmIssueDetail;
import com.ktg.mes.wm.service.IWmIssueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/3/26
 */
@RestController
@RequestMapping("/mobile/wm/issuedetail")
public class WmIssueDetailMobController extends BaseController {

    @Autowired
    private IWmIssueDetailService wmIssueDetailService;

    /**
     * 查询生产领料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public AjaxResult list(WmIssueDetail wmIssueDetail)
    {
        List<WmIssueDetail> list = wmIssueDetailService.selectWmIssueDetailList(wmIssueDetail);
        return AjaxResult.success(list);
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
