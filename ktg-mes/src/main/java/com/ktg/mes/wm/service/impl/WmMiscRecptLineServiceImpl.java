package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscRecptLineMapper;
import com.ktg.mes.wm.domain.WmMiscRecptLine;
import com.ktg.mes.wm.service.IWmMiscRecptLineService;

/**
 * 杂项入库单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscRecptLineServiceImpl implements IWmMiscRecptLineService 
{
    @Autowired
    private WmMiscRecptLineMapper wmMiscRecptLineMapper;

    /**
     * 查询杂项入库单行
     * 
     * @param lineId 杂项入库单行主键
     * @return 杂项入库单行
     */
    @Override
    public WmMiscRecptLine selectWmMiscRecptLineByLineId(Long lineId)
    {
        return wmMiscRecptLineMapper.selectWmMiscRecptLineByLineId(lineId);
    }

    /**
     * 查询杂项入库单行列表
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 杂项入库单行
     */
    @Override
    public List<WmMiscRecptLine> selectWmMiscRecptLineList(WmMiscRecptLine wmMiscRecptLine)
    {
        return wmMiscRecptLineMapper.selectWmMiscRecptLineList(wmMiscRecptLine);
    }

    /**
     * 新增杂项入库单行
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 结果
     */
    @Override
    public int insertWmMiscRecptLine(WmMiscRecptLine wmMiscRecptLine)
    {
        wmMiscRecptLine.setCreateTime(DateUtils.getNowDate());
        return wmMiscRecptLineMapper.insertWmMiscRecptLine(wmMiscRecptLine);
    }

    /**
     * 修改杂项入库单行
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 结果
     */
    @Override
    public int updateWmMiscRecptLine(WmMiscRecptLine wmMiscRecptLine)
    {
        wmMiscRecptLine.setUpdateTime(DateUtils.getNowDate());
        return wmMiscRecptLineMapper.updateWmMiscRecptLine(wmMiscRecptLine);
    }

    /**
     * 批量删除杂项入库单行
     * 
     * @param lineIds 需要删除的杂项入库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptLineByLineIds(Long[] lineIds)
    {
        return wmMiscRecptLineMapper.deleteWmMiscRecptLineByLineIds(lineIds);
    }

    /**
     * 删除杂项入库单行信息
     * 
     * @param lineId 杂项入库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptLineByLineId(Long lineId)
    {
        return wmMiscRecptLineMapper.deleteWmMiscRecptLineByLineId(lineId);
    }
}
