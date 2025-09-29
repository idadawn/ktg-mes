package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.wm.domain.WmRtSalesLine;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmRtSalesLineService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("销售退货明细")
@RestController
@RequestMapping("/mobile/wm/rtsalesline")
public class WmRtSalesLineMobController extends BaseController {

    @Autowired
    private IWmRtSalesLineService wmRtSalesLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IMdItemService mdItemService;

    /**
     * 查询产品销售退货行列表
     */
    @ApiOperation("查询销售退货单明细列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtSalesLine wmRtSalesLine)
    {
        startPage();
        List<WmRtSalesLine> list = wmRtSalesLineService.selectWmRtSalesLineWithQuantityList(wmRtSalesLine);
        return getDataTable(list);
    }


    /**
     * 获取产品销售退货行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmRtSalesLineService.selectWmRtSalesLineWithQuantityByLineId(lineId));
    }

    /**
     * 新增产品销售退货行
     */
    @ApiOperation("新增销售退货单明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:add')")
    @Log(title = "产品销售退货行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtSalesLine wmRtSalesLine)
    {
        if(StringUtils.isNotNull(wmRtSalesLine.getItemId())){
            MdItem item = mdItemService.selectMdItemById(wmRtSalesLine.getItemId());
            wmRtSalesLine.setItemCode(item.getItemCode());
            wmRtSalesLine.setItemName(item.getItemName());
            wmRtSalesLine.setSpecification(item.getSpecification());
            wmRtSalesLine.setUnitOfMeasure(item.getUnitOfMeasure());
        }

        wmRtSalesLine.setCreateBy(getUsername());
        return toAjax(wmRtSalesLineService.insertWmRtSalesLine(wmRtSalesLine));
    }

    /**
     * 修改产品销售退货行
     */
    @ApiOperation("编辑销售退货单明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:edit')")
    @Log(title = "产品销售退货行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtSalesLine wmRtSalesLine)
    {
        if(StringUtils.isNotNull(wmRtSalesLine.getItemId())){
            MdItem item = mdItemService.selectMdItemById(wmRtSalesLine.getItemId());
            wmRtSalesLine.setItemCode(item.getItemCode());
            wmRtSalesLine.setItemName(item.getItemName());
            wmRtSalesLine.setSpecification(item.getSpecification());
            wmRtSalesLine.setUnitOfMeasure(item.getUnitOfMeasure());
        }

        return toAjax(wmRtSalesLineService.updateWmRtSalesLine(wmRtSalesLine));
    }

    /**
     * 删除产品销售退货行
     */
    @ApiOperation("删除销售退货单明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:remove')")
    @Log(title = "产品销售退货行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmRtSalesLineService.deleteWmRtSalesLineByLineIds(lineIds));
    }

}
