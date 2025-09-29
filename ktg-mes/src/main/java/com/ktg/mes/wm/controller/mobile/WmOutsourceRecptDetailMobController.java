package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.wm.domain.WmOutsourceRecptDetail;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmOutsourceRecptDetailService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/mobile/wm/outsourcerecptdetail")
public class WmOutsourceRecptDetailMobController extends BaseController {
    @Autowired
    private IWmOutsourceRecptDetailService wmOutsourceRecptDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询外协入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        startPage();
        List<WmOutsourceRecptDetail> list = wmOutsourceRecptDetailService.selectWmOutsourceRecptDetailList(wmOutsourceRecptDetail);
        return getDataTable(list);
    }

    /**
     * 获取外协入库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmOutsourceRecptDetailService.selectWmOutsourceRecptDetailByDetailId(detailId));
    }

    /**
     * 新增外协入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:add')")
    @Log(title = "外协入库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmOutsourceRecptDetail.getWarehouseId());
            wmOutsourceRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmOutsourceRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmOutsourceRecptDetail.getLocationId());
            wmOutsourceRecptDetail.setLocationCode(location.getLocationCode());
            wmOutsourceRecptDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmOutsourceRecptDetail.getAreaId());
            wmOutsourceRecptDetail.setAreaCode(area.getAreaCode());
            wmOutsourceRecptDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(BigDecimal.ZERO.compareTo(wmOutsourceRecptDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //库位的批次混合、产品混合校验
        //TODO：这里只与库存记录进行对比校验。对于在途物资，需要等执行入库时在库存核心中进行校验时才会报错，此时只能取消单据，重新制作入库单。后期需要优化，增加与在途单据的对比。
        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmOutsourceRecptDetail.getAreaId(),wmOutsourceRecptDetail.getItemId(),wmOutsourceRecptDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmOutsourceRecptDetailService.insertWmOutsourceRecptDetail(wmOutsourceRecptDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmOutsourceRecptDetailService.checkQuantity(wmOutsourceRecptDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出入库单行数量，请重新调整！");
        };

        return AjaxResult.success(wmOutsourceRecptDetail);
    }

    /**
     * 修改外协入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:edit')")
    @Log(title = "外协入库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmOutsourceRecptDetail.getWarehouseId());
            wmOutsourceRecptDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmOutsourceRecptDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmOutsourceRecptDetail.getLocationId());
            wmOutsourceRecptDetail.setLocationCode(location.getLocationCode());
            wmOutsourceRecptDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmOutsourceRecptDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmOutsourceRecptDetail.getAreaId());
            wmOutsourceRecptDetail.setAreaCode(area.getAreaCode());
            wmOutsourceRecptDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(BigDecimal.ZERO.compareTo(wmOutsourceRecptDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //库位的批次混合、产品混合校验
        //TODO：这里只与库存记录进行对比校验。对于在途物资，需要等执行入库时在库存核心中进行校验时才会报错，此时只能取消单据，重新制作入库单。后期需要优化，增加与在途单据的对比。
        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmOutsourceRecptDetail.getAreaId(),wmOutsourceRecptDetail.getItemId(),wmOutsourceRecptDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmOutsourceRecptDetailService.updateWmOutsourceRecptDetail(wmOutsourceRecptDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmOutsourceRecptDetailService.checkQuantity(wmOutsourceRecptDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出入库单行数量，请重新调整！");
        };

        return AjaxResult.success(wmOutsourceRecptDetail);
    }

    /**
     * 删除外协入库单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:remove')")
    @Log(title = "外协入库单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmOutsourceRecptDetailService.deleteWmOutsourceRecptDetailByDetailIds(detailIds));
    }
}
