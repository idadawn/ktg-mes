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
import com.ktg.mes.wm.domain.WmMaterialrequestNoticeLine;
import com.ktg.mes.wm.service.IWmMaterialrequestNoticeLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 备料通知单明细Controller
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
@RestController
@RequestMapping("/mes/wm/mrnoticeline")
public class WmMaterialrequestNoticeLineController extends BaseController
{
    @Autowired
    private IWmMaterialrequestNoticeLineService wmMaterialrequestNoticeLineService;

    /**
     * 查询备料通知单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        startPage();
        List<WmMaterialrequestNoticeLine> list = wmMaterialrequestNoticeLineService.selectWmMaterialrequestNoticeLineList(wmMaterialrequestNoticeLine);
        return getDataTable(list);
    }

    /**
     * 导出备料通知单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:export')")
    @Log(title = "备料通知单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        List<WmMaterialrequestNoticeLine> list = wmMaterialrequestNoticeLineService.selectWmMaterialrequestNoticeLineList(wmMaterialrequestNoticeLine);
        ExcelUtil<WmMaterialrequestNoticeLine> util = new ExcelUtil<WmMaterialrequestNoticeLine>(WmMaterialrequestNoticeLine.class);
        util.exportExcel(response, list, "备料通知单明细数据");
    }

    /**
     * 获取备料通知单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmMaterialrequestNoticeLineService.selectWmMaterialrequestNoticeLineByLineId(lineId));
    }

    /**
     * 新增备料通知单明细
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:add')")
    @Log(title = "备料通知单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        return toAjax(wmMaterialrequestNoticeLineService.insertWmMaterialrequestNoticeLine(wmMaterialrequestNoticeLine));
    }

    /**
     * 修改备料通知单明细
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:edit')")
    @Log(title = "备料通知单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        return toAjax(wmMaterialrequestNoticeLineService.updateWmMaterialrequestNoticeLine(wmMaterialrequestNoticeLine));
    }

    /**
     * 删除备料通知单明细
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnoticeline:remove')")
    @Log(title = "备料通知单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmMaterialrequestNoticeLineService.deleteWmMaterialrequestNoticeLineByLineIds(lineIds));
    }
}
