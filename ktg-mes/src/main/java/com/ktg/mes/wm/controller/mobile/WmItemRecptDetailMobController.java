package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmItemRecptDetail;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmItemRecptDetailService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/3/24
 */
@Api("采购入库单明细")
@RestController
@RequestMapping("/mobile/wm/itemreceptdetail")
public class WmItemRecptDetailMobController extends BaseController {

    @Autowired
    private IWmItemRecptDetailService wmItemRecptDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;



    /**
     * 查询物料入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecept:list')")
    @GetMapping("/list")
    public AjaxResult list(WmItemRecptDetail wmItemRecptDetail)
    {
        List<WmItemRecptDetail> list = wmItemRecptDetailService.selectWmItemRecptDetailList(wmItemRecptDetail);
        return AjaxResult.success(list);
    }

    /**
     * 获取物料入库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecept:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmItemRecptDetailService.selectWmItemRecptDetailByDetailId(detailId));
    }

    /**
     * 新增物料入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecept:add')")
    @Log(title = "物料入库单明细", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmItemRecptDetail wmItemRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmItemRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecptDetail.getWarehouseId());
            wmItemRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmItemRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmItemRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecptDetail.getLocationId());
            wmItemRecptDetail.setLocationCode(location.getLocationCode());
            wmItemRecptDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmItemRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecptDetail.getAreaId());
            wmItemRecptDetail.setAreaCode(area.getAreaCode());
            wmItemRecptDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(BigDecimal.ZERO.compareTo(wmItemRecptDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //库位的批次混合、产品混合校验
        //TODO：这里只与库存记录进行对比校验。对于在途物资，需要等执行入库时在库存核心中进行校验时才会报错，此时只能取消单据，重新制作入库单。后期需要优化，增加与在途单据的对比。
        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmItemRecptDetail.getAreaId(),wmItemRecptDetail.getItemId(),wmItemRecptDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmItemRecptDetailService.insertWmItemRecptDetail(wmItemRecptDetail);
        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmItemRecptDetailService.checkQuantity(wmItemRecptDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出入库单行数量，请重新调整！");
        };



        return AjaxResult.success(wmItemRecptDetail);
    }

    /**
     * 修改物料入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecept:edit')")
    @Log(title = "物料入库单明细", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemRecptDetail wmItemRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmItemRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecptDetail.getWarehouseId());
            wmItemRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmItemRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }

        if(StringUtils.isNotNull(wmItemRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecptDetail.getLocationId());
            wmItemRecptDetail.setLocationCode(location.getLocationCode());
            wmItemRecptDetail.setLocationName(location.getLocationName());
        }

        if(StringUtils.isNotNull(wmItemRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecptDetail.getAreaId());
            wmItemRecptDetail.setAreaCode(area.getAreaCode());
            wmItemRecptDetail.setAreaName(area.getAreaName());
        }

        if(BigDecimal.ZERO.compareTo(wmItemRecptDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //库位的批次混合、产品混合校验
        //TODO：这里只与库存记录进行对比校验。对于在途物资，需要等执行入库时在库存核心中进行校验时才会报错，此时只能取消单据，重新制作入库单。后期需要优化，增加与在途单据的对比。
        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmItemRecptDetail.getAreaId(),wmItemRecptDetail.getItemId(),wmItemRecptDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmItemRecptDetailService.updateWmItemRecptDetail(wmItemRecptDetail);
        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmItemRecptDetailService.checkQuantity(wmItemRecptDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出入库单行数量，请重新调整！");
        };
        return AjaxResult.success(wmItemRecptDetail);
    }

    /**
     * 删除物料入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecept:remove')")
    @Log(title = "物料入库单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmItemRecptDetailService.deleteWmItemRecptDetailByDetailIds(detailIds));
    }




}
