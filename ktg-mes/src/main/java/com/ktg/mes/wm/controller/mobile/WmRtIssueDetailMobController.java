package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.wm.domain.WmRtIssueDetail;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmRtIssueDetailService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Api("生产退料明细")
@RestController
@RequestMapping("/mobile/wm/rtissuedetail")
public class WmRtIssueDetailMobController extends BaseController {

    @Autowired
    private IWmRtIssueDetailService wmRtIssueDetailService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询生产退料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/list")
    public AjaxResult list(WmRtIssueDetail wmRtIssueDetail)
    {
        List<WmRtIssueDetail> list = wmRtIssueDetailService.selectWmRtIssueDetailList(wmRtIssueDetail);
        return AjaxResult.success(list);
    }

    /**
     * 导出生产退料单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:export')")
    @Log(title = "生产退料单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtIssueDetail wmRtIssueDetail)
    {
        List<WmRtIssueDetail> list = wmRtIssueDetailService.selectWmRtIssueDetailList(wmRtIssueDetail);
        ExcelUtil<WmRtIssueDetail> util = new ExcelUtil<WmRtIssueDetail>(WmRtIssueDetail.class);
        util.exportExcel(response, list, "生产退料单明细数据");
    }

    /**
     * 获取生产退料单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmRtIssueDetailService.selectWmRtIssueDetailByDetailId(detailId));
    }

    /**
     * 新增生产退料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:add')")
    @Log(title = "生产退料单明细", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmRtIssueDetail wmRtIssueDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmRtIssueDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmRtIssueDetail.getWarehouseId());
            wmRtIssueDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmRtIssueDetail.setWarehouseName(warehouse.getWarehouseName());
        }

        if(StringUtils.isNotNull(wmRtIssueDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmRtIssueDetail.getLocationId());
            wmRtIssueDetail.setLocationCode(location.getLocationCode());
            wmRtIssueDetail.setLocationName(location.getLocationName());
        }

        if(StringUtils.isNotNull(wmRtIssueDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmRtIssueDetail.getAreaId());
            wmRtIssueDetail.setAreaCode(area.getAreaCode());
            wmRtIssueDetail.setAreaName(area.getAreaName());
        }

        if(BigDecimal.ZERO.compareTo(wmRtIssueDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmRtIssueDetail.getAreaId(), wmRtIssueDetail.getItemId(), wmRtIssueDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmRtIssueDetailService.insertWmRtIssueDetail(wmRtIssueDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmRtIssueDetailService.checkQuantity(wmRtIssueDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出退料单行数量，请重新调整！");
        };


        return AjaxResult.success(wmRtIssueDetail);
    }

    /**
     * 修改生产退料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单明细", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtIssueDetail wmRtIssueDetail)
    {
        //根据明细行上的仓库、库区、库位ID设置对应的编号和名称
        if(StringUtils.isNotNull(wmRtIssueDetail.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmRtIssueDetail.getWarehouseId());
            wmRtIssueDetail.setWarehouseCode(warehouse.getWarehouseCode());
            wmRtIssueDetail.setWarehouseName(warehouse.getWarehouseName());
        }else{
            return AjaxResult.error("请选择仓库！");
        }

        if(StringUtils.isNotNull(wmRtIssueDetail.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmRtIssueDetail.getLocationId());
            wmRtIssueDetail.setLocationCode(location.getLocationCode());
            wmRtIssueDetail.setLocationName(location.getLocationName());
        }else{
            return AjaxResult.error("请选择库区！");
        }

        if(StringUtils.isNotNull(wmRtIssueDetail.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmRtIssueDetail.getAreaId());
            wmRtIssueDetail.setAreaCode(area.getAreaCode());
            wmRtIssueDetail.setAreaName(area.getAreaName());
        }else{
            return AjaxResult.error("请选择库位！");
        }

        if(BigDecimal.ZERO.compareTo(wmRtIssueDetail.getQuantity()) >= 0){
            return AjaxResult.error("物料明细行数量必须大于0，请重新调整！");
        }

        String checkResult = wmStorageAreaService.checkMatrialAndBatchMixing(wmRtIssueDetail.getAreaId(), wmRtIssueDetail.getItemId(), wmRtIssueDetail.getBatchId());
        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL.equals(checkResult)){
            return AjaxResult.error("库位不允许物资混放，请选择其他库位！");
        }

        if(UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH.equals(checkResult)){
            return AjaxResult.error("库位不允许批次混放，请选择其他库位！");
        }

        wmRtIssueDetailService.updateWmRtIssueDetail(wmRtIssueDetail);

        //是否超出行数量验证
        if(UserConstants.GREATER_THAN.equals(wmRtIssueDetailService.checkQuantity(wmRtIssueDetail.getLineId()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("物料明细行总数量超出退料单行数量，请重新调整！");
        };

        return AjaxResult.success(wmRtIssueDetail);
    }

    /**
     * 删除生产退料单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:remove')")
    @Log(title = "生产退料单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmRtIssueDetailService.deleteWmRtIssueDetailByDetailIds(detailIds));
    }

}
