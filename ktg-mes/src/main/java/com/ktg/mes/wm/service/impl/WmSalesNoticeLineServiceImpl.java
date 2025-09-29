package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmSalesNoticeLineMapper;
import com.ktg.mes.wm.domain.WmSalesNoticeLine;
import com.ktg.mes.wm.service.IWmSalesNoticeLineService;

/**
 * 发货通知单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
@Service
public class WmSalesNoticeLineServiceImpl implements IWmSalesNoticeLineService 
{
    @Autowired
    private WmSalesNoticeLineMapper wmSalesNoticeLineMapper;

    /**
     * 查询发货通知单行
     * 
     * @param lineId 发货通知单行主键
     * @return 发货通知单行
     */
    @Override
    public WmSalesNoticeLine selectWmSalesNoticeLineByLineId(Long lineId)
    {
        return wmSalesNoticeLineMapper.selectWmSalesNoticeLineByLineId(lineId);
    }

    /**
     * 查询发货通知单行列表
     * 
     * @param wmSalesNoticeLine 发货通知单行
     * @return 发货通知单行
     */
    @Override
    public List<WmSalesNoticeLine> selectWmSalesNoticeLineList(WmSalesNoticeLine wmSalesNoticeLine)
    {
        return wmSalesNoticeLineMapper.selectWmSalesNoticeLineList(wmSalesNoticeLine);
    }

    /**
     * 新增发货通知单行
     * 
     * @param wmSalesNoticeLine 发货通知单行
     * @return 结果
     */
    @Override
    public int insertWmSalesNoticeLine(WmSalesNoticeLine wmSalesNoticeLine)
    {
        wmSalesNoticeLine.setCreateTime(DateUtils.getNowDate());
        return wmSalesNoticeLineMapper.insertWmSalesNoticeLine(wmSalesNoticeLine);
    }

    /**
     * 修改发货通知单行
     * 
     * @param wmSalesNoticeLine 发货通知单行
     * @return 结果
     */
    @Override
    public int updateWmSalesNoticeLine(WmSalesNoticeLine wmSalesNoticeLine)
    {
        wmSalesNoticeLine.setUpdateTime(DateUtils.getNowDate());
        return wmSalesNoticeLineMapper.updateWmSalesNoticeLine(wmSalesNoticeLine);
    }

    /**
     * 批量删除发货通知单行
     * 
     * @param lineIds 需要删除的发货通知单行主键
     * @return 结果
     */
    @Override
    public int deleteWmSalesNoticeLineByLineIds(Long[] lineIds)
    {
        return wmSalesNoticeLineMapper.deleteWmSalesNoticeLineByLineIds(lineIds);
    }

    /**
     * 删除发货通知单行信息
     * 
     * @param lineId 发货通知单行主键
     * @return 结果
     */
    @Override
    public int deleteWmSalesNoticeLineByLineId(Long lineId)
    {
        return wmSalesNoticeLineMapper.deleteWmSalesNoticeLineByLineId(lineId);
    }
}
