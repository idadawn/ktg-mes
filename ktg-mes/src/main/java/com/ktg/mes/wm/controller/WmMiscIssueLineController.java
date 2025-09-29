package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
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
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 杂项出库单行Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscissueline")
public class WmMiscIssueLineController extends BaseController
{
    @Autowired
    private IWmMiscIssueLineService wmMiscIssueLineService;

    @Autowired
    private IWmMiscIssueDetailService wmMiscIssueDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询杂项出库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscIssueLine wmMiscIssueLine)
    {
        startPage();
        List<WmMiscIssueLine> list = wmMiscIssueLineService.selectWmMiscIssueLineList(wmMiscIssueLine);
        return getDataTable(list);
    }

    /**
     * 导出杂项出库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:export')")
    @Log(title = "杂项出库单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscIssueLine wmMiscIssueLine)
    {
        List<WmMiscIssueLine> list = wmMiscIssueLineService.selectWmMiscIssueLineList(wmMiscIssueLine);
        ExcelUtil<WmMiscIssueLine> util = new ExcelUtil<WmMiscIssueLine>(WmMiscIssueLine.class);
        util.exportExcel(response, list, "杂项出库单行数据");
    }

    /**
     * 获取杂项出库单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmMiscIssueLineService.selectWmMiscIssueLineByLineId(lineId));
    }

    /**
     * 新增杂项出库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:add')")
    @Log(title = "杂项出库单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscIssueLine wmMiscIssueLine)
    {
        if(StringUtils.isNotNull(wmMiscIssueLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmMiscIssueLine.getWarehouseId());
            wmMiscIssueLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmMiscIssueLine.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmMiscIssueLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmMiscIssueLine.getLocationId());
            wmMiscIssueLine.setLocationCode(location.getLocationCode());
            wmMiscIssueLine.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmMiscIssueLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmMiscIssueLine.getAreaId());
            wmMiscIssueLine.setAreaCode(area.getAreaCode());
            wmMiscIssueLine.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(StringUtils.isNull(wmMiscIssueLine.getQuantityIssued()) || wmMiscIssueLine.getQuantityIssued().compareTo(BigDecimal.ZERO) <= 0){
            return AjaxResult.error("出库数量必须大于0！");
        }
        int ret =wmMiscIssueLineService.insertWmMiscIssueLine(wmMiscIssueLine);
        generateDetail(wmMiscIssueLine);
        
        return toAjax(ret);
    }

    /**
     * 修改杂项出库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:edit')")
    @Log(title = "杂项出库单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscIssueLine wmMiscIssueLine)
    {
        if(StringUtils.isNotNull(wmMiscIssueLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmMiscIssueLine.getWarehouseId());
            wmMiscIssueLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmMiscIssueLine.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmMiscIssueLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmMiscIssueLine.getLocationId());
            wmMiscIssueLine.setLocationCode(location.getLocationCode());
            wmMiscIssueLine.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmMiscIssueLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmMiscIssueLine.getAreaId());
            wmMiscIssueLine.setAreaCode(area.getAreaCode());
            wmMiscIssueLine.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(StringUtils.isNull(wmMiscIssueLine.getQuantityIssued()) || wmMiscIssueLine.getQuantityIssued().compareTo(BigDecimal.ZERO) <= 0){
            return AjaxResult.error("出库数量必须大于0！");
        }

        generateDetail(wmMiscIssueLine);

        return toAjax(wmMiscIssueLineService.updateWmMiscIssueLine(wmMiscIssueLine));
    }

    private void generateDetail(WmMiscIssueLine wmMiscIssueLine){
        wmMiscIssueDetailService.deleteWmMiscIssueDetailByLineId(wmMiscIssueLine.getLineId());
        WmMiscIssueDetail detail = new WmMiscIssueDetail();
        detail.setLineId(wmMiscIssueLine.getLineId());
        detail.setIssueId(wmMiscIssueLine.getIssueId());
        detail.setMaterialStockId(wmMiscIssueLine.getMaterialStockId());
        detail.setItemId(wmMiscIssueLine.getItemId());
        detail.setItemCode(wmMiscIssueLine.getItemCode());
        detail.setItemName(wmMiscIssueLine.getItemName());
        detail.setSpecification(wmMiscIssueLine.getSpecification());
        detail.setUnitOfMeasure(wmMiscIssueLine.getUnitOfMeasure());
        detail.setUnitName(wmMiscIssueLine.getUnitName());
        detail.setQuantity(wmMiscIssueLine.getQuantityIssued());
        detail.setBatchId(wmMiscIssueLine.getBatchId());
        detail.setBatchCode(wmMiscIssueLine.getBatchCode());
        detail.setWarehouseId(wmMiscIssueLine.getWarehouseId());
        detail.setWarehouseCode(wmMiscIssueLine.getWarehouseCode());
        detail.setWarehouseName(wmMiscIssueLine.getWarehouseName());
        detail.setLocationId(wmMiscIssueLine.getLocationId());
        detail.setLocationCode(wmMiscIssueLine.getLocationCode());
        detail.setLocationName(wmMiscIssueLine.getLocationName());
        detail.setAreaId(wmMiscIssueLine.getAreaId());
        detail.setAreaCode(wmMiscIssueLine.getAreaCode());
        detail.setAreaName(wmMiscIssueLine.getAreaName());
        wmMiscIssueDetailService.insertWmMiscIssueDetail(detail);
    }

    /**
     * 删除杂项出库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissueline:remove')")
    @Log(title = "杂项出库单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmMiscIssueLineService.deleteWmMiscIssueLineByLineIds(lineIds));
    }
}
