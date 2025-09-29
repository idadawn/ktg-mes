package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmStockTakingPlan;

/**
 * 库存盘点方案Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-03-21
 */
public interface WmStockTakingPlanMapper 
{
    /**
     * 查询库存盘点方案
     * 
     * @param planId 库存盘点方案主键
     * @return 库存盘点方案
     */
    public WmStockTakingPlan selectWmStockTakingPlanByPlanId(Long planId);

    /**
     * 查询库存盘点方案列表
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 库存盘点方案集合
     */
    public List<WmStockTakingPlan> selectWmStockTakingPlanList(WmStockTakingPlan wmStockTakingPlan);

    /**
     * 检查唯一性
     * @param wmStockTakingPlan
     * @return
     */
    public WmStockTakingPlan checkUnique(WmStockTakingPlan wmStockTakingPlan);

    /**
     * 新增库存盘点方案
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 结果
     */
    public int insertWmStockTakingPlan(WmStockTakingPlan wmStockTakingPlan);

    /**
     * 修改库存盘点方案
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 结果
     */
    public int updateWmStockTakingPlan(WmStockTakingPlan wmStockTakingPlan);

    /**
     * 删除库存盘点方案
     * 
     * @param planId 库存盘点方案主键
     * @return 结果
     */
    public int deleteWmStockTakingPlanByPlanId(Long planId);

    /**
     * 批量删除库存盘点方案
     * 
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmStockTakingPlanByPlanIds(Long[] planIds);
}
