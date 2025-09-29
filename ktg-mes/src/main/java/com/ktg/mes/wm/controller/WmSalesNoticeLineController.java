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
import com.ktg.mes.wm.domain.WmSalesNoticeLine;
import com.ktg.mes.wm.service.IWmSalesNoticeLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 发货通知单行Controller
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
@RestController
@RequestMapping("/mes/wm/salesnoticeline")
public class WmSalesNoticeLineController extends BaseController
{
    @Autowired
    private IWmSalesNoticeLineService wmSalesNoticeLineService;

    /**
     * 查询发货通知单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmSalesNoticeLine wmSalesNoticeLine)
    {
        startPage();
        List<WmSalesNoticeLine> list = wmSalesNoticeLineService.selectWmSalesNoticeLineList(wmSalesNoticeLine);
        return getDataTable(list);
    }

    /**
     * 导出发货通知单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:export')")
    @Log(title = "发货通知单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmSalesNoticeLine wmSalesNoticeLine)
    {
        List<WmSalesNoticeLine> list = wmSalesNoticeLineService.selectWmSalesNoticeLineList(wmSalesNoticeLine);
        ExcelUtil<WmSalesNoticeLine> util = new ExcelUtil<WmSalesNoticeLine>(WmSalesNoticeLine.class);
        util.exportExcel(response, list, "发货通知单行数据");
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
