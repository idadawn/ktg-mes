package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscIssueLineMapper;
import com.ktg.mes.wm.domain.WmMiscIssueLine;
import com.ktg.mes.wm.service.IWmMiscIssueLineService;

/**
 * 杂项出库单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscIssueLineServiceImpl implements IWmMiscIssueLineService 
{
    @Autowired
    private WmMiscIssueLineMapper wmMiscIssueLineMapper;

    /**
     * 查询杂项出库单行
     * 
     * @param lineId 杂项出库单行主键
     * @return 杂项出库单行
     */
    @Override
    public WmMiscIssueLine selectWmMiscIssueLineByLineId(Long lineId)
    {
        return wmMiscIssueLineMapper.selectWmMiscIssueLineByLineId(lineId);
    }

    /**
     * 查询杂项出库单行列表
     * 
     * @param wmMiscIssueLine 杂项出库单行
     * @return 杂项出库单行
     */
    @Override
    public List<WmMiscIssueLine> selectWmMiscIssueLineList(WmMiscIssueLine wmMiscIssueLine)
    {
        return wmMiscIssueLineMapper.selectWmMiscIssueLineList(wmMiscIssueLine);
    }

    /**
     * 新增杂项出库单行
     * 
     * @param wmMiscIssueLine 杂项出库单行
     * @return 结果
     */
    @Override
    public int insertWmMiscIssueLine(WmMiscIssueLine wmMiscIssueLine)
    {
        wmMiscIssueLine.setCreateTime(DateUtils.getNowDate());
        return wmMiscIssueLineMapper.insertWmMiscIssueLine(wmMiscIssueLine);
    }

    /**
     * 修改杂项出库单行
     * 
     * @param wmMiscIssueLine 杂项出库单行
     * @return 结果
     */
    @Override
    public int updateWmMiscIssueLine(WmMiscIssueLine wmMiscIssueLine)
    {
        wmMiscIssueLine.setUpdateTime(DateUtils.getNowDate());
        return wmMiscIssueLineMapper.updateWmMiscIssueLine(wmMiscIssueLine);
    }

    /**
     * 批量删除杂项出库单行
     * 
     * @param lineIds 需要删除的杂项出库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueLineByLineIds(Long[] lineIds)
    {
        return wmMiscIssueLineMapper.deleteWmMiscIssueLineByLineIds(lineIds);
    }

    @Override
    public int deleteWmMiscIssueDetailByLineId(Long lineId) {
        return wmMiscIssueLineMapper.deleteWmMiscIssueLineByLineId(lineId);
    }

    /**
     * 删除杂项出库单行信息
     * 
     * @param lineId 杂项出库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueLineByLineId(Long lineId)
    {
        return wmMiscIssueLineMapper.deleteWmMiscIssueLineByLineId(lineId);
    }
}
