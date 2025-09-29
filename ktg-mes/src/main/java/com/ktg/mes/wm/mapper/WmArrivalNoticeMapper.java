package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmArrivalNotice;

/**
 * 到货通知单Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
public interface WmArrivalNoticeMapper 
{
    /**
     * 查询到货通知单
     * 
     * @param noticeId 到货通知单主键
     * @return 到货通知单
     */
    public WmArrivalNotice selectWmArrivalNoticeByNoticeId(Long noticeId);

    /**
     * 查询到货通知单列表
     * 
     * @param wmArrivalNotice 到货通知单
     * @return 到货通知单集合
     */
    public List<WmArrivalNotice> selectWmArrivalNoticeList(WmArrivalNotice wmArrivalNotice);


    /**
     * 检查通知单编码是否唯一
     * @return
     */
    public WmArrivalNotice checkRnCodeUnique(WmArrivalNotice wmArrivalNotice);


    /**
     * 新增到货通知单
     * 
     * @param wmArrivalNotice 到货通知单
     * @return 结果
     */
    public int insertWmArrivalNotice(WmArrivalNotice wmArrivalNotice);

    /**
     * 修改到货通知单
     * 
     * @param wmArrivalNotice 到货通知单
     * @return 结果
     */
    public int updateWmArrivalNotice(WmArrivalNotice wmArrivalNotice);

    /**
     * 删除到货通知单
     * 
     * @param noticeId 到货通知单主键
     * @return 结果
     */
    public int deleteWmArrivalNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除到货通知单
     * 
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmArrivalNoticeByNoticeIds(Long[] noticeIds);
}
