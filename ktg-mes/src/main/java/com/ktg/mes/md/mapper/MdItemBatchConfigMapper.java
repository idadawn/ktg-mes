package com.ktg.mes.md.mapper;

import java.util.List;
import com.ktg.mes.md.domain.MdItemBatchConfig;

/**
 * 物料批次属性配置Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-02-05
 */
public interface MdItemBatchConfigMapper 
{
    /**
     * 查询物料批次属性配置
     * 
     * @param configId 物料批次属性配置主键
     * @return 物料批次属性配置
     */
    public MdItemBatchConfig selectMdItemBatchConfigByConfigId(Long configId);

    /**
     * 查询物料批次属性配置列表
     * 
     * @param mdItemBatchConfig 物料批次属性配置
     * @return 物料批次属性配置集合
     */
    public List<MdItemBatchConfig> selectMdItemBatchConfigList(MdItemBatchConfig mdItemBatchConfig);


    public MdItemBatchConfig getMdItemBatchConfigByItemId(Long itemId);

    /**
     * 新增物料批次属性配置
     * 
     * @param mdItemBatchConfig 物料批次属性配置
     * @return 结果
     */
    public int insertMdItemBatchConfig(MdItemBatchConfig mdItemBatchConfig);

    /**
     * 修改物料批次属性配置
     * 
     * @param mdItemBatchConfig 物料批次属性配置
     * @return 结果
     */
    public int updateMdItemBatchConfig(MdItemBatchConfig mdItemBatchConfig);

    /**
     * 删除物料批次属性配置
     * 
     * @param configId 物料批次属性配置主键
     * @return 结果
     */
    public int deleteMdItemBatchConfigByConfigId(Long configId);

    /**
     * 批量删除物料批次属性配置
     * 
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdItemBatchConfigByConfigIds(Long[] configIds);
}
