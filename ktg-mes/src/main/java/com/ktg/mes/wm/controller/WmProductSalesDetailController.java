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
import com.ktg.mes.wm.domain.WmProductSalesDetail;
import com.ktg.mes.wm.service.IWmProductSalesDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 产品销售出库记录明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
@RestController
@RequestMapping("/mes/wm/productsalesdetail")
public class WmProductSalesDetailController extends BaseController
{
    @Autowired
    private IWmProductSalesDetailService wmProductSalesDetailService;

    /**
     * 查询产品销售出库记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSalesDetail wmProductSalesDetail)
    {
        startPage();
        List<WmProductSalesDetail> list = wmProductSalesDetailService.selectWmProductSalesDetailList(wmProductSalesDetail);
        return getDataTable(list);
    }

    /**
     * 导出产品销售出库记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:export')")
    @Log(title = "产品销售出库记录明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductSalesDetail wmProductSalesDetail)
    {
        List<WmProductSalesDetail> list = wmProductSalesDetailService.selectWmProductSalesDetailList(wmProductSalesDetail);
        ExcelUtil<WmProductSalesDetail> util = new ExcelUtil<WmProductSalesDetail>(WmProductSalesDetail.class);
        util.exportExcel(response, list, "产品销售出库记录明细数据");
    }

    /**
     * 获取产品销售出库记录明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmProductSalesDetailService.selectWmProductSalesDetailByDetailId(detailId));
    }

    /**
     * 新增产品销售出库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:add')")
    @Log(title = "产品销售出库记录明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSalesDetail wmProductSalesDetail)
    {
        return toAjax(wmProductSalesDetailService.insertWmProductSalesDetail(wmProductSalesDetail));
    }

    /**
     * 修改产品销售出库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:edit')")
    @Log(title = "产品销售出库记录明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSalesDetail wmProductSalesDetail)
    {
        return toAjax(wmProductSalesDetailService.updateWmProductSalesDetail(wmProductSalesDetail));
    }

    /**
     * 删除产品销售出库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:remove')")
    @Log(title = "产品销售出库记录明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmProductSalesDetailService.deleteWmProductSalesDetailByDetailIds(detailIds));
    }
}
