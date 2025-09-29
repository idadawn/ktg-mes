package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * 杂项入库单行Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscrecptline")
public class WmMiscRecptLineController extends BaseController
{
    @Autowired
    private IWmMiscRecptLineService wmMiscRecptLineService;

    @Autowired
    private IWmMiscRecptDetailService wmMiscRecptDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询杂项入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscRecptLine wmMiscRecptLine)
    {
        startPage();
        List<WmMiscRecptLine> list = wmMiscRecptLineService.selectWmMiscRecptLineList(wmMiscRecptLine);
        return getDataTable(list);
    }

    /**
     * 导出杂项入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:export')")
    @Log(title = "杂项入库单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscRecptLine wmMiscRecptLine)
    {
        List<WmMiscRecptLine> list = wmMiscRecptLineService.selectWmMiscRecptLineList(wmMiscRecptLine);
        ExcelUtil<WmMiscRecptLine> util = new ExcelUtil<WmMiscRecptLine>(WmMiscRecptLine.class);
        util.exportExcel(response, list, "杂项入库单行数据");
    }

    /**
     * 获取杂项入库单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmMiscRecptLineService.selectWmMiscRecptLineByLineId(lineId));
    }

    /**
     * 新增杂项入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:add')")
    @Log(title = "杂项入库单行", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscRecptLine wmMiscRecptLine)
    {
        if(StringUtils.isNotNull(wmMiscRecptLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmMiscRecptLine.getWarehouseId());
            wmMiscRecptLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmMiscRecptLine.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmMiscRecptLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmMiscRecptLine.getLocationId());
            wmMiscRecptLine.setLocationCode(location.getLocationCode());
            wmMiscRecptLine.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmMiscRecptLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmMiscRecptLine.getAreaId());
            wmMiscRecptLine.setAreaCode(area.getAreaCode());
            wmMiscRecptLine.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(StringUtils.isNull(wmMiscRecptLine.getQuantityRecived()) || wmMiscRecptLine.getQuantityRecived().compareTo(BigDecimal.ZERO) <= 0){
            return AjaxResult.error("入库数量必须大于0！");
        }
        int ret = wmMiscRecptLineService.insertWmMiscRecptLine(wmMiscRecptLine);

        //为了兼容其他需要扫码上架的情况，此处自动生成一个detail记录
        generateDetailRecord(wmMiscRecptLine);

        return toAjax(ret);
    }

    /**
     * 修改杂项入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:edit')")
    @Log(title = "杂项入库单行", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscRecptLine wmMiscRecptLine)
    {
        if(StringUtils.isNotNull(wmMiscRecptLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmMiscRecptLine.getWarehouseId());
            wmMiscRecptLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmMiscRecptLine.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmMiscRecptLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmMiscRecptLine.getLocationId());
            wmMiscRecptLine.setLocationCode(location.getLocationCode());
            wmMiscRecptLine.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmMiscRecptLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmMiscRecptLine.getAreaId());
            wmMiscRecptLine.setAreaCode(area.getAreaCode());
            wmMiscRecptLine.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(StringUtils.isNull(wmMiscRecptLine.getQuantityRecived()) || wmMiscRecptLine.getQuantityRecived().compareTo(BigDecimal.ZERO) <= 0){
            return AjaxResult.error("入库数量必须大于0！");
        }
        //删除对应的detail记录，再重新生成
        generateDetailRecord(wmMiscRecptLine);

        return toAjax(wmMiscRecptLineService.updateWmMiscRecptLine(wmMiscRecptLine));
    }

    private void generateDetailRecord(WmMiscRecptLine wmMiscRecptLine){
        wmMiscRecptDetailService.deleteWmMiscRecptDetailByLineId(wmMiscRecptLine.getLineId());
        WmMiscRecptDetail detail = new WmMiscRecptDetail();
        detail.setRecptId(wmMiscRecptLine.getRecptId());
        detail.setLineId(wmMiscRecptLine.getLineId());
        detail.setItemId(wmMiscRecptLine.getItemId());
        detail.setItemCode(wmMiscRecptLine.getItemCode());
        detail.setItemName(wmMiscRecptLine.getItemName());
        detail.setSpecification(wmMiscRecptLine.getSpecification());
        detail.setUnitOfMeasure(wmMiscRecptLine.getUnitOfMeasure());
        detail.setUnitName(wmMiscRecptLine.getUnitName());
        detail.setQuantity(wmMiscRecptLine.getQuantityRecived());
        detail.setBatchId(wmMiscRecptLine.getBatchId());
        detail.setBatchCode(wmMiscRecptLine.getBatchCode());
        detail.setWarehouseId(wmMiscRecptLine.getWarehouseId());
        detail.setWarehouseCode(wmMiscRecptLine.getWarehouseCode());
        detail.setWarehouseName(wmMiscRecptLine.getWarehouseName());
        detail.setLocationId(wmMiscRecptLine.getLocationId());
        detail.setLocationCode(wmMiscRecptLine.getLocationCode());
        detail.setLocationName(wmMiscRecptLine.getLocationName());
        detail.setAreaId(wmMiscRecptLine.getAreaId());
        detail.setAreaCode(wmMiscRecptLine.getAreaCode());
        detail.setAreaName(wmMiscRecptLine.getAreaName());
        wmMiscRecptDetailService.insertWmMiscRecptDetail(detail);
    }

    /**
     * 删除杂项入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:remove')")
    @Log(title = "杂项入库单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmMiscRecptLineService.deleteWmMiscRecptLineByLineIds(lineIds));
    }
}
