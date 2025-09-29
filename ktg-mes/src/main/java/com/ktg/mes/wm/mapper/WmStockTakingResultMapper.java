package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmStockTakingResult;

/**
 * 库存盘点结果Mapper接口
 * 
 * @author yinjinlu
 * @date 2023-08-22
 */
public interface WmStockTakingResultMapper 
{
    /**
     * 查询库存盘点结果
     * 
     * @param resultId 库存盘点结果主键
     * @return 库存盘点结果
     */
    public WmStockTakingResult selectWmStockTakingResultByResultId(Long resultId);

    /**
     * 查询库存盘点结果列表
     * 
     * @param wmStockTakingResult 库存盘点结果
     * @return 库存盘点结果集合
     */
    public List<WmStockTakingResult> selectWmStockTakingResultList(WmStockTakingResult wmStockTakingResult);

    /**
     * 新增库存盘点结果
     * 
     * @param wmStockTakingResult 库存盘点结果
     * @return 结果
     */
    public int insertWmStockTakingResult(WmStockTakingResult wmStockTakingResult);

    /**
     * 修改库存盘点结果
     * 
     * @param wmStockTakingResult 库存盘点结果
     * @return 结果
     */
    public int updateWmStockTakingResult(WmStockTakingResult wmStockTakingResult);

    /**
     * 删除库存盘点结果
     * 
     * @param resultId 库存盘点结果主键
     * @return 结果
     */
    public int deleteWmStockTakingResultByResultId(Long resultId);

    /**
     * 批量删除库存盘点结果
     * 
     * @param resultIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmStockTakingResultByResultIds(Long[] resultIds);

    /**
     * 根据盘点单ID删除所有盘点结果
     * @param takingId
     * @return
     */
    public int deleteWmStockTakingResultByTakingId(Long takingId);
}
