package com.ktg.mes.wm.service.impl;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.mes.wm.domain.WmStockTakingLine;
import com.ktg.mes.wm.mapper.WmStockTakingLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmStockTakingResultMapper;
import com.ktg.mes.wm.domain.WmStockTakingResult;
import com.ktg.mes.wm.service.IWmStockTakingResultService;
import org.springframework.util.CollectionUtils;

/**
 * 库存盘点结果Service业务层处理
 * 
 * @author yinjinlu
 * @date 2023-08-22
 */
@Service
public class WmStockTakingResultServiceImpl implements IWmStockTakingResultService 
{
    @Autowired
    private WmStockTakingResultMapper wmStockTakingResultMapper;

    @Autowired
    private WmStockTakingLineMapper wmStockTakingLineMapper;

    /**
     * 查询库存盘点结果
     * 
     * @param resultId 库存盘点结果主键
     * @return 库存盘点结果
     */
    @Override
    public WmStockTakingResult selectWmStockTakingResultByResultId(Long resultId)
    {
        return wmStockTakingResultMapper.selectWmStockTakingResultByResultId(resultId);
    }

    /**
     * 查询库存盘点结果列表
     * 
     * @param wmStockTakingResult 库存盘点结果
     * @return 库存盘点结果
     */
    @Override
    public List<WmStockTakingResult> selectWmStockTakingResultList(WmStockTakingResult wmStockTakingResult)
    {
        return wmStockTakingResultMapper.selectWmStockTakingResultList(wmStockTakingResult);
    }

    /**
     * 新增库存盘点结果
     * 1.根据传入的储位、批次、物料查找对应的行信息
     *   1.1 如果没有找到则新增一个行，用传入的信息更新行信息，设置盘点状态为“盘盈”；保存结果信息，绑定对应的行ID。
     *   1.2 如果找到了，则更新行信息中的盘点数量和盘点状态；保存结果信息。
     *
     *
     * @param wmStockTakingResult 库存盘点结果
     * @return 结果
     */
    @Override
    public int insertWmStockTakingResult(WmStockTakingResult wmStockTakingResult)
    {

        WmStockTakingLine param = new WmStockTakingLine();
        param.setAreaId(wmStockTakingResult.getAreaId());
        param.setBatchId(wmStockTakingResult.getBatchId());
        param.setItemId(wmStockTakingResult.getItemId());
        WmStockTakingLine line = null;
        List<WmStockTakingLine> lines = wmStockTakingLineMapper.selectWmStockTakingLineList(param);
        if(!CollectionUtils.isEmpty(lines)){
            line = lines.get(0);
        }else{
            line = new WmStockTakingLine();
            line.setTakingId(wmStockTakingResult.getTakingId());
            line.setItemId(wmStockTakingResult.getItemId());
            line.setItemCode(wmStockTakingResult.getItemCode());
            line.setItemName(wmStockTakingResult.getItemName());
            line.setSpecification(wmStockTakingResult.getSpecification());
            line.setUnitOfMeasure(wmStockTakingResult.getUnitOfMeasure());
            line.setUnitName(wmStockTakingResult.getUnitName());
            line.setBatchId(wmStockTakingResult.getBatchId());
            line.setBatchCode(wmStockTakingResult.getBatchCode());
            line.setAreaId(wmStockTakingResult.getAreaId());
            line.setAreaCode(wmStockTakingResult.getAreaCode());
            line.setAreaName(wmStockTakingResult.getAreaName());
            line.setWarehouseId(wmStockTakingResult.getWarehouseId());
            line.setWarehouseCode(wmStockTakingResult.getWarehouseCode());
            line.setWarehouseName(wmStockTakingResult.getWarehouseName());
            line.setLocationId(wmStockTakingResult.getLocationId());
            line.setLocationCode(wmStockTakingResult.getLocationCode());
            line.setLocationName(wmStockTakingResult.getLocationName());
            line.setQuantity(wmStockTakingResult.getQuantity());
            line.setTakingQuantity(wmStockTakingResult.getQuantity());
            line.setTakingStatus(UserConstants.WM_STOCK_TAKING_STATUS_PROFIT);
            wmStockTakingLineMapper.insertWmStockTakingLine(line);
        }
        wmStockTakingResult.setLineId(line.getLineId());
        wmStockTakingResult.setCreateTime(DateUtils.getNowDate());
        int ret = wmStockTakingResultMapper.insertWmStockTakingResult(wmStockTakingResult);
        wmStockTakingLineMapper.updateTakingStatus(line);
        return ret;
    }

    /**
     * 修改库存盘点结果
     * 
     * @param wmStockTakingResult 库存盘点结果
     * @return 结果
     */
    @Override
    public int updateWmStockTakingResult(WmStockTakingResult wmStockTakingResult)
    {
        WmStockTakingLine line = wmStockTakingLineMapper.selectWmStockTakingLineByLineId(wmStockTakingResult.getLineId());
        wmStockTakingResult.setUpdateTime(DateUtils.getNowDate());
        int ret = wmStockTakingResultMapper.updateWmStockTakingResult(wmStockTakingResult);
        wmStockTakingLineMapper.updateTakingStatus(line);
        return ret;
    }

    /**
     * 批量删除库存盘点结果
     * 
     * @param resultIds 需要删除的库存盘点结果主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingResultByResultIds(Long[] resultIds)
    {
        return wmStockTakingResultMapper.deleteWmStockTakingResultByResultIds(resultIds);
    }

    /**
     * 删除库存盘点结果信息
     * 
     * @param resultId 库存盘点结果主键
     * @return 结果
     */
    @Override
    public int deleteWmStockTakingResultByResultId(Long resultId)
    {
        return wmStockTakingResultMapper.deleteWmStockTakingResultByResultId(resultId);
    }

    @Override
    public int deleteWmStockTakingResultByTakingId(Long takingId) {
        return wmStockTakingResultMapper.deleteWmStockTakingResultByTakingId(takingId);
    }
}
