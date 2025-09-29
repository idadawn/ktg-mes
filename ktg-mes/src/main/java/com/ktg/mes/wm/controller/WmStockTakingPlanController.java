package com.ktg.mes.wm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmMaterialStock;
import com.ktg.mes.wm.domain.WmStockTakingParam;
import com.ktg.mes.wm.service.IWmStockTakingParamService;
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
import com.ktg.mes.wm.domain.WmStockTakingPlan;
import com.ktg.mes.wm.service.IWmStockTakingPlanService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 库存盘点方案Controller
 * 
 * @author yinjinlu
 * @date 2025-03-21
 */
@RestController
@RequestMapping("/mes/wm/stocktakingplan")
public class WmStockTakingPlanController extends BaseController
{
    @Autowired
    private IWmStockTakingPlanService wmStockTakingPlanService;

    @Autowired
    private IWmStockTakingParamService wmStockTakingParamService;

    /**
     * 查询库存盘点方案列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTakingPlan wmStockTakingPlan)
    {
        startPage();
        List<WmStockTakingPlan> list = wmStockTakingPlanService.selectWmStockTakingPlanList(wmStockTakingPlan);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点方案列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:export')")
    @Log(title = "库存盘点方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmStockTakingPlan wmStockTakingPlan)
    {
        List<WmStockTakingPlan> list = wmStockTakingPlanService.selectWmStockTakingPlanList(wmStockTakingPlan);
        ExcelUtil<WmStockTakingPlan> util = new ExcelUtil<WmStockTakingPlan>(WmStockTakingPlan.class);
        util.exportExcel(response, list, "库存盘点方案数据");
    }

    /**
     * 获取库存盘点方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(wmStockTakingPlanService.selectWmStockTakingPlanByPlanId(planId));
    }

    /**
     * 新增库存盘点方案
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:add')")
    @Log(title = "库存盘点方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTakingPlan wmStockTakingPlan)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmStockTakingPlanService.checkUnique(wmStockTakingPlan))){
            return AjaxResult.error("方案编号已存在！");
        }

        if(UserConstants.WM_STOCK_TAKING_TYPE_DYNAMIC.equals(wmStockTakingPlan.getTakingType())){
            if(wmStockTakingPlan.getStartTime() == null || wmStockTakingPlan.getEndTime() == null){
                return AjaxResult.error("动态盘点方案必须设置开始时间和结束时间！");
            }
            if(wmStockTakingPlan.getEndTime().before(wmStockTakingPlan.getStartTime())){
                return AjaxResult.error("结束时间必须大于开始时间！");
            }
        }else{
            wmStockTakingPlan.setStartTime(null);
            wmStockTakingPlan.setEndTime(null);
        }

        return toAjax(wmStockTakingPlanService.insertWmStockTakingPlan(wmStockTakingPlan));
    }

    /**
     * 修改库存盘点方案
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:edit')")
    @Log(title = "库存盘点方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTakingPlan wmStockTakingPlan)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmStockTakingPlanService.checkUnique(wmStockTakingPlan))){
            return AjaxResult.error("方案编号已存在！");
        }

        if(UserConstants.WM_STOCK_TAKING_TYPE_DYNAMIC.equals(wmStockTakingPlan.getTakingType())){
            if(wmStockTakingPlan.getStartTime() == null || wmStockTakingPlan.getEndTime() == null){
                return AjaxResult.error("动态盘点方案必须设置开始时间和结束时间！");
            }
            if(wmStockTakingPlan.getEndTime().before(wmStockTakingPlan.getStartTime())){
                return AjaxResult.error("结束时间必须大于开始时间！");
            }
        }else{
            wmStockTakingPlan.setStartTime(null);
            wmStockTakingPlan.setEndTime(null);
        }

        if(UserConstants.YES.equals(wmStockTakingPlan.getEnableFlag())){
            //启用的时候检查参数项；
            WmStockTakingParam wmStockTakingParam = new WmStockTakingParam();
            wmStockTakingParam.setPlanId(wmStockTakingPlan.getPlanId());
            List<WmStockTakingParam> paramList = wmStockTakingParamService.selectWmStockTakingParamList(wmStockTakingParam);
            if(StringUtils.isEmpty(paramList)){
                return AjaxResult.error("启用方案时必须设置盘点参数！");
            }
            //生成序列化的WmMaterialStock对象，并存储在数据库中；等查询的时候直接从数据库中获取即可；
            WmMaterialStock stock = new WmMaterialStock();
            for(WmStockTakingParam param : paramList){
                if(UserConstants.WM_STOCK_TAKING_PARAM_TYPE_ITEM.equals(param.getParamType())){
                    stock.setItemId(param.getParamValueId());
                    stock.setItemCode(param.getParamValueCode());
                }
                if(UserConstants.WM_STOCK_TAKING_PARAM_TYPE_BATCH.equals(param.getParamType())){
                    stock.setBatchId(param.getParamValueId());
                    stock.setBatchCode(param.getParamValueCode());
                }

                if(UserConstants.WM_STOCK_TAKING_PARAM_TYPE_WAREHOUSE.equals(param.getParamType())){
                    stock.setWarehouseId(param.getParamValueId());
                    stock.setWarehouseCode(param.getParamValueCode());
                }

                if(UserConstants.WM_STOCK_TAKING_PARAM_TYPE_LOCATION.equals(param.getParamType())){
                    stock.setLocationId(param.getParamValueId());
                    stock.setLocationCode(param.getParamValueCode());
                }
            }

            if(wmStockTakingPlan.getStartTime() != null && wmStockTakingPlan.getEndTime() != null){
                Map<String, Object> params = new HashMap<>();
                params.put("startTime", wmStockTakingPlan.getStartTime());
                params.put("endTime", wmStockTakingPlan.getEndTime());
                stock.setParams(params);
            }

            String dataSql = JSON.toJSONString(stock);
            wmStockTakingPlan.setDataSql(dataSql);
        }
        return toAjax(wmStockTakingPlanService.updateWmStockTakingPlan(wmStockTakingPlan));
    }


    /**
     * 删除库存盘点方案
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:remove')")
    @Log(title = "库存盘点方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(wmStockTakingPlanService.deleteWmStockTakingPlanByPlanIds(planIds));
    }
}
