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
import org.springframework.transaction.annotation.Transactional;
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
import com.ktg.mes.wm.domain.WmRtSalesDetail;
import com.ktg.mes.wm.service.IWmRtSalesDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 销售退货记录明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-16
 */
@RestController
@RequestMapping("/mes/wm/rtsalesdetail")
public class WmRtSalesDetailController extends BaseController
{
    @Autowired
    private IWmRtSalesDetailService wmRtSalesDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询销售退货记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtSalesDetail wmRtSalesDetail)
    {
        startPage();
        List<WmRtSalesDetail> list = wmRtSalesDetailService.selectWmRtSalesDetailList(wmRtSalesDetail);
        return getDataTable(list);
    }

    /**
     * 导出销售退货记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:export')")
    @Log(title = "销售退货记录明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtSalesDetail wmRtSalesDetail)
    {
        List<WmRtSalesDetail> list = wmRtSalesDetailService.selectWmRtSalesDetailList(wmRtSalesDetail);
        ExcelUtil<WmRtSalesDetail> util = new ExcelUtil<WmRtSalesDetail>(WmRtSalesDetail.class);
        util.exportExcel(response, list, "销售退货记录明细数据");
    }

    /**
     * 获取销售退货记录明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmRtSalesDetailService.selectWmRtSalesDetailByDetailId(detailId));
    }

    /**
     * 新增销售退货记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:add')")
    @Log(title = "销售退货记录明细", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmRtSalesDetail wmRtSalesDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmRtSalesDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmRtSalesDetail.getWarehouseId());
            wmRtSalesDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmRtSalesDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmRtSalesDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmRtSalesDetail.getLocationId());
            wmRtSalesDetail.setLocationCode(location.getLocationCode());
            wmRtSalesDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmRtSalesDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmRtSalesDetail.getAreaId());
            wmRtSalesDetail.setAreaCode(area.getAreaCode());
            wmRtSalesDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(BigDecimal.ZERO.compareTo(wmRtSalesDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmRtSalesDetail.getAreaId(), wmRtSalesDetail.getItemId(), wmRtSalesDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmRtSalesDetailService.insertWmRtSalesDetail(wmRtSalesDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmRtSalesDetailService.checkQuantity(wmRtSalesDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出退料单行数量，请重新调整！");
        };

        return AjaxResult.success(wmRtSalesDetail);
    }

    /**
     * 修改销售退货记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:edit')")
    @Log(title = "销售退货记录明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtSalesDetail wmRtSalesDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmRtSalesDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmRtSalesDetail.getWarehouseId());
            wmRtSalesDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmRtSalesDetail.setWarehouseName(warehouse.getWarehouseName());
        }

        if(StringUtils.isNotNull(wmRtSalesDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmRtSalesDetail.getLocationId());
            wmRtSalesDetail.setLocationCode(location.getLocationCode());
            wmRtSalesDetail.setLocationName(location.getLocationName());
        }

        if(StringUtils.isNotNull(wmRtSalesDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmRtSalesDetail.getAreaId());
            wmRtSalesDetail.setAreaCode(area.getAreaCode());
            wmRtSalesDetail.setAreaName(area.getAreaName());
        }

        if(BigDecimal.ZERO.compareTo(wmRtSalesDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmRtSalesDetail.getAreaId(), wmRtSalesDetail.getItemId(), wmRtSalesDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmRtSalesDetailService.updateWmRtSalesDetail(wmRtSalesDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmRtSalesDetailService.checkQuantity(wmRtSalesDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出退料单行数量，请重新调整！");
        };

        return AjaxResult.success(wmRtSalesDetail);
    }

    /**
     * 删除销售退货记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:remove')")
    @Log(title = "销售退货记录明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmRtSalesDetailService.deleteWmRtSalesDetailByDetailIds(detailIds));
    }
}
