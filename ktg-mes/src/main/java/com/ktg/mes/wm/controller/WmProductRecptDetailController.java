package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
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
import com.ktg.mes.wm.domain.WmProductRecptDetail;
import com.ktg.mes.wm.service.IWmProductRecptDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 产品入库记录明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/mes/wm/productrecptdetail")
public class WmProductRecptDetailController extends BaseController
{
    @Autowired
    private IWmProductRecptDetailService wmProductRecptDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询产品入库记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductRecptDetail wmProductRecptDetail)
    {
        startPage();
        List<WmProductRecptDetail> list = wmProductRecptDetailService.selectWmProductRecptDetailList(wmProductRecptDetail);
        return getDataTable(list);
    }

    /**
     * 导出产品入库记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:export')")
    @Log(title = "产品入库记录明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductRecptDetail wmProductRecptDetail)
    {
        List<WmProductRecptDetail> list = wmProductRecptDetailService.selectWmProductRecptDetailList(wmProductRecptDetail);
        ExcelUtil<WmProductRecptDetail> util = new ExcelUtil<WmProductRecptDetail>(WmProductRecptDetail.class);
        util.exportExcel(response, list, "产品入库记录明细数据");
    }

    /**
     * 获取产品入库记录明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmProductRecptDetailService.selectWmProductRecptDetailByDetailId(detailId));
    }

    /**
     * 新增产品入库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:add')")
    @Log(title = "产品入库记录明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductRecptDetail wmProductRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmProductRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductRecptDetail.getWarehouseId());
            wmProductRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库!");
        }

        if(StringUtils.isNotNull(wmProductRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductRecptDetail.getLocationId());
            wmProductRecptDetail.setLocationCode(location.getLocationCode());
            wmProductRecptDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区!");
        }

        if(StringUtils.isNotNull(wmProductRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductRecptDetail.getAreaId());
            wmProductRecptDetail.setAreaCode(area.getAreaCode());
            wmProductRecptDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位!");
        }

        if(BigDecimal.ZERO.compareTo(wmProductRecptDetail.getQuantity()) >= 0){
            return AjaxResult.error("入库数量必须大于0!");
        }


        return toAjax(wmProductRecptDetailService.insertWmProductRecptDetail(wmProductRecptDetail));
    }

    /**
     * 修改产品入库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:edit')")
    @Log(title = "产品入库记录明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductRecptDetail wmProductRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmProductRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductRecptDetail.getWarehouseId());
            wmProductRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }

        if(StringUtils.isNotNull(wmProductRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductRecptDetail.getLocationId());
            wmProductRecptDetail.setLocationCode(location.getLocationCode());
            wmProductRecptDetail.setLocationName(location.getLocationName());
        }

        if(StringUtils.isNotNull(wmProductRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductRecptDetail.getAreaId());
            wmProductRecptDetail.setAreaCode(area.getAreaCode());
            wmProductRecptDetail.setAreaName(area.getAreaName());
        }
        return toAjax(wmProductRecptDetailService.updateWmProductRecptDetail(wmProductRecptDetail));
    }

    /**
     * 删除产品入库记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:remove')")
    @Log(title = "产品入库记录明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmProductRecptDetailService.deleteWmProductRecptDetailByDetailIds(detailIds));
    }
}
