package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;

/**
 * 到货通知单行Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
public interface WmArrivalNoticeLineMapper 
{
    /**
     * 查询到货通知单行
     * 
     * @param lineId 到货通知单行主键
     * @return 到货通知单行
     */
    public WmArrivalNoticeLine selectWmArrivalNoticeLineByLineId(Long lineId);

    /**
     * 查询到货通知单行列表
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 到货通知单行集合
     */
    public List<WmArrivalNoticeLine> selectWmArrivalNoticeLineList(WmArrivalNoticeLine wmArrivalNoticeLine);


    /**
     * 获取指定通知单下所有待检测，但未检测的行
     * @param noticeId
     * @return
     */
    public List<WmArrivalNoticeLine> selectUncheckedLine(Long noticeId);


    /**
     * 新增到货通知单行
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 结果
     */
    public int insertWmArrivalNoticeLine(WmArrivalNoticeLine wmArrivalNoticeLine);

    /**
     * 修改到货通知单行
     * 
     * @param wmArrivalNoticeLine 到货通知单行
     * @return 结果
     */
    public int updateWmArrivalNoticeLine(WmArrivalNoticeLine wmArrivalNoticeLine);

    /**
     * 删除到货通知单行
     * 
     * @param lineId 到货通知单行主键
     * @return 结果
     */
    public int deleteWmArrivalNoticeLineByLineId(Long lineId);

    /**
     * 批量删除到货通知单行
     * 
     * @param lineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmArrivalNoticeLineByLineIds(Long[] lineIds);

    /**
     * 根据通知单号删除所有行
     * @param noticeId
     * @return
     */
    public int deleteByNoticeId(Long noticeId);

}
