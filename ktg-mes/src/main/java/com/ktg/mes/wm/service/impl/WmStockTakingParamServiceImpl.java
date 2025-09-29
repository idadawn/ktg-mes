package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmStockTakingParamMapper;
import com.ktg.mes.wm.domain.WmStockTakingParam;
import com.ktg.mes.wm.service.IWmStockTakingParamService;

/**
 * 库存盘点方案参数Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-31
 */
@Service
public class WmStockTakingParamServiceImpl implements IWmStockTakingParamService 
{
    @Autowired
    private WmStockTakingParamMapper wmStockTakingParamMapper;

    /**
     * 查询库存盘点方案参数
     * 
     * @param paramId 库存盘点方案参数主键
     * @return 库存盘点方案参数
     */
    @Override
    public WmStockTakingParam selectWmStockTakingParamByParamId(Long paramId)
    {
        return wmStockTakingParamMapper.selectWmStockTakingParamByParamId(paramId);
    }

    /**
     * 查询库存盘点方案参数列表
     * 
     * @param wmStockTakingParam 库存盘点方案参数
     * @return 库存盘点方案参数
     */
    @Override
    public List<WmStockTakingParam> selectWmStockTakingParamList(WmStockTakingParam wmStockTakingParam)
    {
        return wmStockTakingParamMapper.selectWmStockTakingParamList(wmStockTakingParam);
    }

    /**
     * 新增库存盘点方案参数
     * 
     * @param wmStockTakingParam 库存盘点方案参数
     * @return 结果
     */
    @Override
    public int insertWmStockTakingParam(WmStockTakingParam wmStockTakingParam)
    {
        wmStockTakingParam.setCreateTime(DateUtils.getNowDate());
        return wmStockTakingParamMapper.insertWmStockTakingParam(wmStockTakingParam);
    }

    /**
     * 修改库存盘点方案参数
     * 
     * @param wmStockTakingParam 库存盘点方案参数
     * @return 结果
     */
    @Override
    public int updateWmStockTakingParam(WmStockTakingParam wmStockTakingParam)
    {
        wmStockTakingParam.setUpdateTime(DateUtils.getNowDate());
        return wmStockTakingParamMapper.updateWmStockTakingParam(wmStockTakingParam);
    }

    /**
     * 批量删除库存盘点方案参数
     * 
     * @param paramIds 需要删除的库存盘点方案参数主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingParamByParamIds(Long[] paramIds)
    {
        return wmStockTakingParamMapper.deleteWmStockTakingParamByParamIds(paramIds);
    }

    /**
     * 删除库存盘点方案参数信息
     * 
     * @param paramId 库存盘点方案参数主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingParamByParamId(Long paramId)
    {
        return wmStockTakingParamMapper.deleteWmStockTakingParamByParamId(paramId);
    }
}
