package com.ktg.mes.wm.controller.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
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
import com.ktg.common.core.page.TableDataInfo;

/**
 * 库存盘点记录Controller
 *
 * @author yinjinlu
 * @date 2023-08-17
 */
@Api("物资盘点")
@RestController
@RequestMapping("/mobile/wm/stocktaking")
public class WmStockTakingMobController extends BaseController
{
    @Autowired
    private IWmStockTakingService wmStockTakingService;

    @Autowired
    private IWmStockTakingLineService wmStockTakingLineService;

    @Autowired
    private IWmStockTakingPlanService wmStockTakingPlanService;

    @Autowired
    private IWmStockTakingResultService wmStockTakingResultService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询库存盘点记录列表
     */
    @ApiOperation("查询库存盘点单列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTaking wmStockTaking)
    {
        startPage();
        List<WmStockTaking> list = wmStockTakingService.selectWmStockTakingList(wmStockTaking);
        return getDataTable(list);
    }

    /**
     * 获取库存盘点记录详细信息
     */
    @ApiOperation("获取库存盘点单详情接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:query')")
    @GetMapping(value = "/{takingId}")
    public AjaxResult getInfo(@PathVariable("takingId") Long takingId)
    {
        return AjaxResult.success(wmStockTakingService.selectWmStockTakingByTakingId(takingId));
    }

    /**
     * 新增库存盘点记录
     */
    @ApiOperation("新增库存盘点单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:add')")
    @Log(title = "库存盘点记录", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTaking wmStockTaking)
    {
        if(StringUtils.isNotNull(wmStockTaking.getTakingCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmStockTakingService.checkUnique(wmStockTaking))){
                return AjaxResult.error("单据编号已存在!");
            }
        }else {
            wmStockTaking.setTakingCode(autoCodeUtil.genSerialCode(UserConstants.STOCKTAKING_CODE,""));
        }
        wmStockTaking.setCreateBy(getUsername());
        wmStockTakingService.insertWmStockTaking(wmStockTaking);
        //根据头上选择的盘点方案，生成对应的盘点行
        if(StringUtils.isNotNull(wmStockTaking.getPlanId())){
            WmStockTakingPlan plan =  wmStockTakingPlanService.selectWmStockTakingPlanByPlanId(wmStockTaking.getPlanId());
            if(plan!= null && UserConstants.YES.equals(plan.getEnableFlag())){
                try{
                    WmMaterialStock param = JSON.parseObject(plan.getDataSql(), WmMaterialStock.class);

                    //如果是动态盘点，根据头上选择的时间重新赋值时间参数
                    if(UserConstants.WM_STOCK_TAKING_TYPE_DYNAMIC.equals(wmStockTaking.getTakingType())){
                        if(StringUtils.isNotNull(wmStockTaking.getStartTime()) && StringUtils.isNotNull(wmStockTaking.getEndTime())){
                            Map<String, Object> params = new HashMap<>();
                            params.put("startTime", wmStockTaking.getStartTime());
                            params.put("endTime", wmStockTaking.getEndTime());
                            param.setParams(params);
                        }else{
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return AjaxResult.error("动态盘点必须选择开始时间和结束时间！");
                        }
                    }else{
                        Map<String, Object> params = new HashMap<>();
                        param.setParams(params);
                    }

                    List<WmMaterialStock> stocks = wmMaterialStockService.selectWmMaterialStockList(param);

                    if(!CollectionUtils.isEmpty(stocks)){
                        for (WmMaterialStock stock : stocks){
                            WmStockTakingLine line = new WmStockTakingLine();
                            line.setTakingId(wmStockTaking.getTakingId());
                            line.setMaterialStockId(stock.getMaterialStockId());
                            line.setItemId(stock.getItemId());
                            line.setItemCode(stock.getItemCode());
                            line.setItemName(stock.getItemName());
                            line.setSpecification(stock.getSpecification());
                            line.setUnitOfMeasure(stock.getUnitOfMeasure());
                            line.setUnitName(stock.getUnitName());
                            line.setBatchId(stock.getBatchId());
                            line.setBatchCode(stock.getBatchCode());
                            line.setQuantity(stock.getQuantityOnhand());
                            line.setTakingQuantity(BigDecimal.ZERO);
                            line.setWarehouseId(stock.getWarehouseId());
                            line.setWarehouseCode(stock.getWarehouseCode());
                            line.setWarehouseName(stock.getWarehouseName());
                            line.setLocationId(stock.getLocationId());
                            line.setLocationCode(stock.getLocationCode());
                            line.setLocationName(stock.getLocationName());
                            line.setAreaId(stock.getAreaId());
                            line.setAreaCode(stock.getAreaCode());
                            line.setAreaName(stock.getAreaName());
                            line.setTakingStatus(UserConstants.WM_STOCK_TAKING_STATUS_LOSS);
                            wmStockTakingLineService.insertWmStockTakingLine(line);
                        }
                    }
                }catch (Exception e){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AjaxResult.error("盘点方案参数解析失败，请检查方案参数配置！");
                }
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AjaxResult.error("盘点方案无效或者未启用！");
            }
        }
        return AjaxResult.success(wmStockTaking);
    }

    /**
     * 修改库存盘点记录
     */
    @ApiOperation("编辑库存盘点单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:edit')")
    @Log(title = "库存盘点记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTaking wmStockTaking)
    {
        //判断头上的计划是否发生变化，如果发生变化，则删除原有的盘点行，重新生成新的盘点行
        if(StringUtils.isNotNull(wmStockTaking.getPlanId())){
            WmStockTaking oldTaking = wmStockTakingService.selectWmStockTakingByTakingId(wmStockTaking.getTakingId());
            if(!oldTaking.getPlanId().equals(wmStockTaking.getPlanId())){
                wmStockTakingLineService.deleteByTakingId(wmStockTaking.getTakingId());
                WmStockTakingPlan plan =  wmStockTakingPlanService.selectWmStockTakingPlanByPlanId(wmStockTaking.getPlanId());
                if(plan!= null && UserConstants.YES.equals(plan.getEnableFlag())){
                    try{
                        WmMaterialStock param = JSON.parseObject(plan.getDataSql(), WmMaterialStock.class);

                        //如果是动态盘点，根据头上选择的时间重新赋值时间参数
                        if(UserConstants.WM_STOCK_TAKING_TYPE_DYNAMIC.equals(wmStockTaking.getTakingType())){
                            if(StringUtils.isNotNull(wmStockTaking.getStartTime()) && StringUtils.isNotNull(wmStockTaking.getEndTime())){
                                Map<String, Object> params = new HashMap<>();
                                params.put("startTime", wmStockTaking.getStartTime());
                                params.put("endTime", wmStockTaking.getEndTime());
                                param.setParams(params);
                            }else{
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return AjaxResult.error("动态盘点必须选择开始时间和结束时间！");
                            }
                        }

                        List<WmMaterialStock> stocks = wmMaterialStockService.selectWmMaterialStockList(param);
                        if(!CollectionUtils.isEmpty(stocks)){
                            for (WmMaterialStock stock : stocks){
                                WmStockTakingLine line = new WmStockTakingLine();
                                line.setTakingId(wmStockTaking.getTakingId());
                                line.setMaterialStockId(stock.getMaterialStockId());
                                line.setItemId(stock.getItemId());
                                line.setItemCode(stock.getItemCode());
                                line.setItemName(stock.getItemName());
                                line.setSpecification(stock.getSpecification());
                                line.setUnitOfMeasure(stock.getUnitOfMeasure());
                                line.setUnitName(stock.getUnitName());
                                line.setBatchId(stock.getBatchId());
                                line.setBatchCode(stock.getBatchCode());
                                line.setQuantity(stock.getQuantityOnhand());
                                line.setTakingQuantity(BigDecimal.ZERO);
                                line.setWarehouseId(stock.getWarehouseId());
                                line.setWarehouseCode(stock.getWarehouseCode());
                                line.setWarehouseName(stock.getWarehouseName());
                                line.setLocationId(stock.getLocationId());
                                line.setLocationCode(stock.getLocationCode());
                                line.setLocationName(stock.getLocationName());
                                line.setAreaId(stock.getAreaId());
                                line.setAreaCode(stock.getAreaCode());
                                line.setAreaName(stock.getAreaName());
                                line.setTakingStatus(UserConstants.WM_STOCK_TAKING_STATUS_LOSS);
                                wmStockTakingLineService.insertWmStockTakingLine(line);
                            }
                        }
                    }catch (Exception e){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return AjaxResult.error("盘点方案参数解析失败，请检查方案参数配置！");
                    }
                }else{
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AjaxResult.error("盘点方案无效或者未启用！");
                }
            }
        }

        if(UserConstants.ORDER_STATUS_APPROVING.equals(wmStockTaking.getStatus())){
            //检查要盘点的内容
            WmStockTakingLine param = new WmStockTakingLine();
            param.setTakingId(wmStockTaking.getTakingId());
            List<WmStockTakingLine> lines = wmStockTakingLineService.selectWmStockTakingLineList(param);
            if(CollectionUtils.isEmpty(lines)){
                return AjaxResult.error("请添加需要盘点的物资！");
            }
            //根据冻结标识，对物资进行冻结
            if(UserConstants.YES.equals(wmStockTaking.getFrozenFlag())){
                wmStockTakingLineService.updateFrozenStatus(wmStockTaking.getTakingId(), UserConstants.YES);
            }
        }

        if(UserConstants.ORDER_STATUS_FINISHED.equals(wmStockTaking.getStatus())){
            //根据冻结标识，对物资进行解冻
            if(UserConstants.YES.equals(wmStockTaking.getFrozenFlag())){
                wmStockTakingLineService.updateFrozenStatus(wmStockTaking.getTakingId(), UserConstants.NO);
            }
        }

        return toAjax(wmStockTakingService.updateWmStockTaking(wmStockTaking));
    }

    /**
     * 删除库存盘点记录
     */
    @ApiOperation("删除库存盘点单接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:remove')")
    @Log(title = "库存盘点记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{takingIds}")
    public AjaxResult remove(@PathVariable Long[] takingIds)
    {
        for(Long takingId:takingIds){
            WmStockTaking taking = wmStockTakingService.selectWmStockTakingByTakingId(takingId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(taking.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据！");
            }
            wmStockTakingLineService.deleteByTakingId(takingId);
            wmStockTakingResultService.deleteWmStockTakingResultByTakingId(takingId);
        }
        return toAjax(wmStockTakingService.deleteWmStockTakingByTakingIds(takingIds));
    }
}
