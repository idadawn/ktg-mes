package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmStockTakingPlanMapper;
import com.ktg.mes.wm.domain.WmStockTakingPlan;
import com.ktg.mes.wm.service.IWmStockTakingPlanService;

/**
 * 库存盘点方案Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-21
 */
@Service
public class WmStockTakingPlanServiceImpl implements IWmStockTakingPlanService 
{
    @Autowired
    private WmStockTakingPlanMapper wmStockTakingPlanMapper;

    /**
     * 查询库存盘点方案
     * 
     * @param planId 库存盘点方案主键
     * @return 库存盘点方案
     */
    @Override
    public WmStockTakingPlan selectWmStockTakingPlanByPlanId(Long planId)
    {
        return wmStockTakingPlanMapper.selectWmStockTakingPlanByPlanId(planId);
    }

    /**
     * 查询库存盘点方案列表
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 库存盘点方案
     */
    @Override
    public List<WmStockTakingPlan> selectWmStockTakingPlanList(WmStockTakingPlan wmStockTakingPlan)
    {
        return wmStockTakingPlanMapper.selectWmStockTakingPlanList(wmStockTakingPlan);
    }

    @Override
    public String checkUnique(WmStockTakingPlan wmStockTakingPlan) {
        WmStockTakingPlan stp = wmStockTakingPlanMapper.checkUnique(wmStockTakingPlan);
        Long planId = wmStockTakingPlan.getPlanId() == null? -1L : wmStockTakingPlan.getPlanId();
        if(StringUtils.isNotNull(stp) && stp.getPlanId().longValue()!= planId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增库存盘点方案
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 结果
     */
    @Override
    public int insertWmStockTakingPlan(WmStockTakingPlan wmStockTakingPlan)
    {
        wmStockTakingPlan.setCreateTime(DateUtils.getNowDate());
        return wmStockTakingPlanMapper.insertWmStockTakingPlan(wmStockTakingPlan);
    }

    /**
     * 修改库存盘点方案
     * 
     * @param wmStockTakingPlan 库存盘点方案
     * @return 结果
     */
    @Override
    public int updateWmStockTakingPlan(WmStockTakingPlan wmStockTakingPlan)
    {
        wmStockTakingPlan.setUpdateTime(DateUtils.getNowDate());
        return wmStockTakingPlanMapper.updateWmStockTakingPlan(wmStockTakingPlan);
    }

    /**
     * 批量删除库存盘点方案
     * 
     * @param planIds 需要删除的库存盘点方案主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingPlanByPlanIds(Long[] planIds)
    {
        return wmStockTakingPlanMapper.deleteWmStockTakingPlanByPlanIds(planIds);
    }

    /**
     * 删除库存盘点方案信息
     * 
     * @param planId 库存盘点方案主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingPlanByPlanId(Long planId)
    {
        return wmStockTakingPlanMapper.deleteWmStockTakingPlanByPlanId(planId);
    }
}
