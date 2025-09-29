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
import com.ktg.mes.wm.domain.WmProductSalesLine;
import com.ktg.mes.wm.service.IWmProductSalesLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 产品销售出库行Controller
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
@RestController
@RequestMapping("/mes/wm/productsalesline")
public class WmProductSalesLineController extends BaseController
{
    @Autowired
    private IWmProductSalesLineService wmProductSalesLineService;

    /**
     * 查询产品销售出库行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSalesLine wmProductSalesLine)
    {
        startPage();
        List<WmProductSalesLine> list = wmProductSalesLineService.selectWmProductSalesLineList(wmProductSalesLine);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/listWithQuantity")
    public TableDataInfo listWithQuantity(WmProductSalesLine wmProductSalesLine)
    {
        startPage();
        List<WmProductSalesLine> list = wmProductSalesLineService.selectWmProductSalesLineWithQuantityList(wmProductSalesLine);
        return getDataTable(list);
    }

    /**
     * 查询产品销售出库行列表（含明细）
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmProductSalesLine wmProductSalesLine)
    {
        startPage();
        List<WmProductSalesLine> list = wmProductSalesLineService.selectWmProductSalesLineWithDetailList(wmProductSalesLine);
        return getDataTable(list);
    }



    /**
     * 导出产品销售出库行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:export')")
    @Log(title = "产品销售出库行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductSalesLine wmProductSalesLine)
    {
        List<WmProductSalesLine> list = wmProductSalesLineService.selectWmProductSalesLineList(wmProductSalesLine);
        ExcelUtil<WmProductSalesLine> util = new ExcelUtil<WmProductSalesLine>(WmProductSalesLine.class);
        util.exportExcel(response, list, "产品销售出库行数据");
    }

    /**
     * 获取产品销售出库行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductSalesLineService.selectWmProductSalesLineByLineId(lineId));
    }

    /**
     * 新增产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:add')")
    @Log(title = "产品销售出库行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSalesLine wmProductSalesLine)
    {
        wmProductSalesLine.setCreateBy(getUsername());
        return toAjax(wmProductSalesLineService.insertWmProductSalesLine(wmProductSalesLine));
    }

    /**
     * 修改产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:edit')")
    @Log(title = "产品销售出库行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSalesLine wmProductSalesLine)
    {
        return toAjax(wmProductSalesLineService.updateWmProductSalesLine(wmProductSalesLine));
    }

    /**
     * 删除产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:remove')")
    @Log(title = "产品销售出库行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductSalesLineService.deleteWmProductSalesLineByLineIds(lineIds));
    }
}
