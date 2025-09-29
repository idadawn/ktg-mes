package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmBatch;

/**
 * 批次记录Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-02-24
 */
public interface WmBatchMapper 
{
    /**
     * 查询批次记录
     * 
     * @param batchId 批次记录主键
     * @return 批次记录
     */
    public WmBatch selectWmBatchByBatchId(Long batchId);

    /**
     * 根据批次号查询批次记录
     * @param batchCode
     * @return
     */
    public WmBatch selectWmBatchByBatchCode(String batchCode);

    /**
     * 查询批次记录列表
     * 
     * @param wmBatch 批次记录
     * @return 批次记录集合
     */
    public List<WmBatch> selectWmBatchList(WmBatch wmBatch);


    /**
     * 根据批次号查询，当前批次被哪些工单的哪些批次产品给消耗掉了
     * @param batchCode
     * @return
     */
    public List<WmBatch> selectForwardBatchList(String batchCode);


    /**
     * 根据批次号查询，当前批次的产品都用到了哪些批次的物资
     * @param batchCode
     * @return
     */
    public List<WmBatch> selectBackwardBatchList(String batchCode);

    /**
     * 新增批次记录
     * 
     * @param wmBatch 批次记录
     * @return 结果
     */
    public int insertWmBatch(WmBatch wmBatch);

    /**
     * 修改批次记录
     * 
     * @param wmBatch 批次记录
     * @return 结果
     */
    public int updateWmBatch(WmBatch wmBatch);

    /**
     * 删除批次记录
     * 
     * @param batchId 批次记录主键
     * @return 结果
     */
    public int deleteWmBatchByBatchId(Long batchId);

    /**
     * 批量删除批次记录
     * 
     * @param batchIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmBatchByBatchIds(Long[] batchIds);

    /**
     * 根据传入的参数信息，查询已有的批次
     * @param wmBatch
     * @return
     */
    public WmBatch getBatchCodeByParams(WmBatch wmBatch);

    /**
     * 根据传入的批次号，和某些属性，判断这些属性是否与当前批次匹配，如果匹配则返回当前批次，否则生成新的批次
     * 应用场景是：例如某个批次的物资，其质量状态发生变化，此时传入批次号和质量状态参数。如果批次规则中包含质量状态，则根据质量状态参数判断是否与当前批次匹配，如果匹配则返回当前批次，否则生成新的批次（其余属性不变）。
     * @param wmBatch
     * @return
     */
    public WmBatch checkBatchCodeByBatchAndProperties(WmBatch wmBatch);

}
