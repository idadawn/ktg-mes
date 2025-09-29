package com.ktg.mes.pro.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProAndonConfigMapper;
import com.ktg.mes.pro.domain.ProAndonConfig;
import com.ktg.mes.pro.service.IProAndonConfigService;

/**
 * 安灯呼叫配置Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-04-28
 */
@Service
public class ProAndonConfigServiceImpl implements IProAndonConfigService 
{
    @Autowired
    private ProAndonConfigMapper proAndonConfigMapper;

    /**
     * 查询安灯呼叫配置
     * 
     * @param configId 安灯呼叫配置主键
     * @return 安灯呼叫配置
     */
    @Override
    public ProAndonConfig selectProAndonConfigByConfigId(Long configId)
    {
        return proAndonConfigMapper.selectProAndonConfigByConfigId(configId);
    }

    /**
     * 查询安灯呼叫配置列表
     * 
     * @param proAndonConfig 安灯呼叫配置
     * @return 安灯呼叫配置
     */
    @Override
    public List<ProAndonConfig> selectProAndonConfigList(ProAndonConfig proAndonConfig)
    {
        return proAndonConfigMapper.selectProAndonConfigList(proAndonConfig);
    }

    /**
     * 新增安灯呼叫配置
     * 
     * @param proAndonConfig 安灯呼叫配置
     * @return 结果
     */
    @Override
    public int insertProAndonConfig(ProAndonConfig proAndonConfig)
    {
        proAndonConfig.setCreateTime(DateUtils.getNowDate());
        return proAndonConfigMapper.insertProAndonConfig(proAndonConfig);
    }

    /**
     * 修改安灯呼叫配置
     * 
     * @param proAndonConfig 安灯呼叫配置
     * @return 结果
     */
    @Override
    public int updateProAndonConfig(ProAndonConfig proAndonConfig)
    {
        proAndonConfig.setUpdateTime(DateUtils.getNowDate());
        return proAndonConfigMapper.updateProAndonConfig(proAndonConfig);
    }

    /**
     * 批量删除安灯呼叫配置
     * 
     * @param configIds 需要删除的安灯呼叫配置主键
     * @return 结果
     */
    @Override
    public int deleteProAndonConfigByConfigIds(Long[] configIds)
    {
        return proAndonConfigMapper.deleteProAndonConfigByConfigIds(configIds);
    }

    /**
     * 删除安灯呼叫配置信息
     * 
     * @param configId 安灯呼叫配置主键
     * @return 结果
     */
    @Override
    public int deleteProAndonConfigByConfigId(Long configId)
    {
        return proAndonConfigMapper.deleteProAndonConfigByConfigId(configId);
    }
}
