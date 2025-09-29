package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmArrivalNoticeLineMapper;
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;
import com.ktg.mes.wm.service.IWmArrivalNoticeLineService;

/**
 * 到货通知单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
@Service
public class WmArrivalNoticeLineServiceImpl implements IWmArrivalNoticeLineService 
{
    @Autowired
    private WmArrivalNoticeLineMapper wmArrivalNoticeLineMapper;

    /**
     * 查询到货通知单行
     * 
     * @param lineId 到货通知单行主键
     * @return 到货通知单行
     */
    @Override
    public WmArrivalNoticeLine selectWmArrivalNoticeLineByLineId(Long lineId)
    {
        return wmArrivalNoticeLineMapper.selectWmArrivalNoticeLineByLineId(lineId);
    }

    /**
     * 查询到货通知单行列表
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 到货通知单行
     */
    @Override
    public List<WmArrivalNoticeLine> selectWmArrivalNoticeLineList(WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        return wmArrivalNoticeLineMapper.selectWmArrivalNoticeLineList(wmArrivalNoticeLine);
    }

    /**
     * 新增到货通知单行
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 结果
     */
    @Override
    public int insertWmArrivalNoticeLine(WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        wmArrivalNoticeLine.setCreateTime(DateUtils.getNowDate());
        return wmArrivalNoticeLineMapper.insertWmArrivalNoticeLine(wmArrivalNoticeLine);
    }

    /**
     * 修改到货通知单行
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 结果
     */
    @Override
    public int updateWmArrivalNoticeLine(WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        wmArrivalNoticeLine.setUpdateTime(DateUtils.getNowDate());
        return wmArrivalNoticeLineMapper.updateWmArrivalNoticeLine(wmArrivalNoticeLine);
    }

    /**
     * 批量删除到货通知单行
     * 
     * @param lineIds 需要删除的到货通知单行主键
     * @return 结果
     */
    @Override
    public int deleteWmArrivalNoticeLineByLineIds(Long[] lineIds)
    {
        return wmArrivalNoticeLineMapper.deleteWmArrivalNoticeLineByLineIds(lineIds);
    }

    /**
     * 删除到货通知单行信息
     * 
     * @param lineId 到货通知单行主键
     * @return 结果
     */
    @Override
    public int deleteWmArrivalNoticeLineByLineId(Long lineId)
    {
        return wmArrivalNoticeLineMapper.deleteWmArrivalNoticeLineByLineId(lineId);
    }

    /**
     * 根据通知单ID删除所有通知单行
     * @param noticeId
     * @return
     */
    @Override
    public int deleteByNoticeId(Long noticeId) {
        return wmArrivalNoticeLineMapper.deleteByNoticeId(noticeId);
    }


}
