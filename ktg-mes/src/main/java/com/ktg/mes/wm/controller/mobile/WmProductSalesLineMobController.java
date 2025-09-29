package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmMaterialStock;
import com.ktg.mes.wm.domain.WmProductSalesLine;
import com.ktg.mes.wm.service.IWmMaterialStockService;
import com.ktg.mes.wm.service.IWmProductSalesLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("产品销售出库明细")
@RestController
@RequestMapping("/mobile/wm/productsalesline")
public class WmProductSalesLineMobController extends BaseController {
    @Autowired
    private IWmProductSalesLineService wmProductSalesLineService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    /**
     *
     * 查询产品销售出库行列表
     */
    @ApiOperation("查询销售出库行列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSalesLine wmProductSalesLine)
    {
        startPage();
        List<WmProductSalesLine> list = wmProductSalesLineService.selectWmProductSalesLineWithQuantityList(wmProductSalesLine);
        return getDataTable(list);
    }


    /**
     * 获取产品销售出库行详细信息
     */
    @ApiOperation("获取销售出库行信息明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductSalesLineService.selectWmProductSalesLineWithQuantityByLineId(lineId));
    }

    /**
     * 新增产品销售出库行
     */
    @ApiOperation("新增销售出库信息明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:add')")
    @Log(title = "产品销售出库行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSalesLine wmProductSalesLine)
    {
        if(StringUtils.isNotNull(wmProductSalesLine.getMaterialStockId())){
            WmMaterialStock stock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmProductSalesLine.getMaterialStockId());
            wmProductSalesLine.setItemId(stock.getItemId());
            wmProductSalesLine.setItemCode(stock.getItemCode());
            wmProductSalesLine.setItemName(stock.getItemName());
            wmProductSalesLine.setSpecification(stock.getSpecification());
            wmProductSalesLine.setUnitOfMeasure(stock.getUnitOfMeasure());
            wmProductSalesLine.setUnitName(stock.getUnitName());
            wmProductSalesLine.setBatchId(stock.getBatchId());
            wmProductSalesLine.setBatchCode(stock.getBatchCode());
        }

        wmProductSalesLine.setCreateBy(getUsername());
        wmProductSalesLineService.insertWmProductSalesLine(wmProductSalesLine);
        return AjaxResult.success(wmProductSalesLine);
    }

    /**
     * 修改产品销售出库行
     */
    @ApiOperation("编辑销售出库明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:edit')")
    @Log(title = "产品销售出库行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSalesLine wmProductSalesLine)
    {
        if(StringUtils.isNotNull(wmProductSalesLine.getMaterialStockId())){
            WmMaterialStock stock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmProductSalesLine.getMaterialStockId());
            wmProductSalesLine.setItemId(stock.getItemId());
            wmProductSalesLine.setItemCode(stock.getItemCode());
            wmProductSalesLine.setItemName(stock.getItemName());
            wmProductSalesLine.setSpecification(stock.getSpecification());
            wmProductSalesLine.setUnitOfMeasure(stock.getUnitOfMeasure());
            wmProductSalesLine.setUnitName(stock.getUnitName());
            wmProductSalesLine.setBatchId(stock.getBatchId());
            wmProductSalesLine.setBatchCode(stock.getBatchCode());
        }

        return toAjax(wmProductSalesLineService.updateWmProductSalesLine(wmProductSalesLine));
    }

    /**
     * 删除产品销售出库行
     */
    @ApiOperation("删除销售出库明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:remove')")
    @Log(title = "产品销售出库行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductSalesLineService.deleteWmProductSalesLineByLineIds(lineIds));
    }
}
