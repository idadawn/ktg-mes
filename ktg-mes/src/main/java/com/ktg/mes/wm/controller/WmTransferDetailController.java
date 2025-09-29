package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import com.ktg.mes.wm.domain.WmTransferDetail;
import com.ktg.mes.wm.service.IWmTransferDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 转移调拨单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-18
 */
@RestController
@RequestMapping("/mes/wm/transferdetail")
public class WmTransferDetailController extends BaseController
{
    @Autowired
    private IWmTransferDetailService wmTransferDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询转移调拨单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmTransferDetail wmTransferDetail)
    {
        startPage();
        List<WmTransferDetail> list = wmTransferDetailService.selectWmTransferDetailList(wmTransferDetail);
        return getDataTable(list);
    }

    /**
     * 导出转移调拨单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:export')")
    @Log(title = "转移调拨单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmTransferDetail wmTransferDetail)
    {
        List<WmTransferDetail> list = wmTransferDetailService.selectWmTransferDetailList(wmTransferDetail);
        ExcelUtil<WmTransferDetail> util = new ExcelUtil<WmTransferDetail>(WmTransferDetail.class);
        util.exportExcel(response, list, "转移调拨单明细数据");
    }

    /**
     * 获取转移调拨单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmTransferDetailService.selectWmTransferDetailByDetailId(detailId));
    }

    /**
     * 新增转移调拨单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:add')")
    @Log(title = "转移调拨单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmTransferDetail wmTransferDetail)
    {
        if(StringUtils.isNotNull(wmTransferDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransferDetail.getWarehouseId());
            wmTransferDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmTransferDetail.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmTransferDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmTransferDetail.getLocationId());
            wmTransferDetail.setLocationCode(location.getLocationCode());
            wmTransferDetail.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmTransferDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmTransferDetail.getAreaId());
            wmTransferDetail.setAreaCode(area.getAreaCode());
            wmTransferDetail.setAreaName(area.getAreaName());
        }

        if(BigDecimal.ZERO.compareTo(wmTransferDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmTransferDetail.getAreaId(), wmTransferDetail.getItemId(), wmTransferDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmTransferDetailService.insertWmTransferDetail(wmTransferDetail);


        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmTransferDetailService.checkQuantity(wmTransferDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出转移单行数量，请重新调整！");
        };

        return AjaxResult.success(wmTransferDetail);
    }

    /**
     * 修改转移调拨单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:edit')")
    @Log(title = "转移调拨单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmTransferDetail wmTransferDetail)
    {
        if(StringUtils.isNotNull(wmTransferDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransferDetail.getWarehouseId());
            wmTransferDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmTransferDetail.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmTransferDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmTransferDetail.getLocationId());
            wmTransferDetail.setLocationCode(location.getLocationCode());
            wmTransferDetail.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmTransferDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmTransferDetail.getAreaId());
            wmTransferDetail.setAreaCode(area.getAreaCode());
            wmTransferDetail.setAreaName(area.getAreaName());
        }

        if(BigDecimal.ZERO.compareTo(wmTransferDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmTransferDetail.getAreaId(), wmTransferDetail.getItemId(), wmTransferDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmTransferDetailService.updateWmTransferDetail(wmTransferDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmTransferDetailService.checkQuantity(wmTransferDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出转移单行数量，请重新调整！");
        };

        return AjaxResult.success(wmTransferDetail);
    }

    /**
     * 删除转移调拨单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:remove')")
    @Log(title = "转移调拨单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmTransferDetailService.deleteWmTransferDetailByDetailIds(detailIds));
    }
}
