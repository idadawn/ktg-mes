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
import com.ktg.mes.wm.domain.WmMiscRecptDetail;
import com.ktg.mes.wm.service.IWmMiscRecptDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 杂项入库单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscrecptdetail")
public class WmMiscRecptDetailController extends BaseController
{
    @Autowired
    private IWmMiscRecptDetailService wmMiscRecptDetailService;

    /**
     * 查询杂项入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscRecptDetail wmMiscRecptDetail)
    {
        startPage();
        List<WmMiscRecptDetail> list = wmMiscRecptDetailService.selectWmMiscRecptDetailList(wmMiscRecptDetail);
        return getDataTable(list);
    }

    /**
     * 导出杂项入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:export')")
    @Log(title = "杂项入库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscRecptDetail wmMiscRecptDetail)
    {
        List<WmMiscRecptDetail> list = wmMiscRecptDetailService.selectWmMiscRecptDetailList(wmMiscRecptDetail);
        ExcelUtil<WmMiscRecptDetail> util = new ExcelUtil<WmMiscRecptDetail>(WmMiscRecptDetail.class);
        util.exportExcel(response, list, "杂项入库单明细数据");
    }

    /**
     * 获取杂项入库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmMiscRecptDetailService.selectWmMiscRecptDetailByDetailId(detailId));
    }

    /**
     * 新增杂项入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:add')")
    @Log(title = "杂项入库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscRecptDetail wmMiscRecptDetail)
    {
        return toAjax(wmMiscRecptDetailService.insertWmMiscRecptDetail(wmMiscRecptDetail));
    }

    /**
     * 修改杂项入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:edit')")
    @Log(title = "杂项入库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscRecptDetail wmMiscRecptDetail)
    {
        return toAjax(wmMiscRecptDetailService.updateWmMiscRecptDetail(wmMiscRecptDetail));
    }

    /**
     * 删除杂项入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:remove')")
    @Log(title = "杂项入库单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmMiscRecptDetailService.deleteWmMiscRecptDetailByDetailIds(detailIds));
    }
}
