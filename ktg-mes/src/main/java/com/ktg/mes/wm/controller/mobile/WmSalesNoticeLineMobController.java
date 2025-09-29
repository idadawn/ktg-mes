package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.wm.domain.WmSalesNoticeLine;
import com.ktg.mes.wm.service.IWmSalesNoticeLineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("发货通知单")
@RestController
@RequestMapping("/mobile/wm/salesnoticeline")
public class WmSalesNoticeLineMobController extends BaseController {

    @Autowired
    private IWmSalesNoticeLineService wmSalesNoticeLineService;

    /**
     * 查询发货通知单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:list')")
    @GetMapping("/list")
    public AjaxResult list(WmSalesNoticeLine wmSalesNoticeLine)
    {
        List<WmSalesNoticeLine> list = wmSalesNoticeLineService.selectWmSalesNoticeLineList(wmSalesNoticeLine);
        return AjaxResult.success(list);
    }

    /**
     * 获取发货通知单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmSalesNoticeLineService.selectWmSalesNoticeLineByLineId(lineId));
    }

    /**
     * 新增发货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:add')")
    @Log(title = "发货通知单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmSalesNoticeLine wmSalesNoticeLine)
    {
        return toAjax(wmSalesNoticeLineService.insertWmSalesNoticeLine(wmSalesNoticeLine));
    }

    /**
     * 修改发货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:edit')")
    @Log(title = "发货通知单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmSalesNoticeLine wmSalesNoticeLine)
    {
        return toAjax(wmSalesNoticeLineService.updateWmSalesNoticeLine(wmSalesNoticeLine));
    }

    /**
     * 删除发货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:remove')")
    @Log(title = "发货通知单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmSalesNoticeLineService.deleteWmSalesNoticeLineByLineIds(lineIds));
    }

}
