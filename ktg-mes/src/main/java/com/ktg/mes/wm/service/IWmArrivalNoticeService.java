package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmArrivalNotice;

/**
 * 到货通知单Service接口
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
public interface IWmArrivalNoticeService 
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
    public String checkRnCodeUnique(WmArrivalNotice wmArrivalNotice);

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
     * 根据行上的检测状态，更新当前单据的状态
     * 如果所有需要进行检验的行已经绑定了检验单，则更新为APPROVED
     * @param noticeId
     */
    public void updateStatus(Long noticeId);

    /**
     * 批量删除到货通知单
     * 
     * @param noticeIds 需要删除的到货通知单主键集合
     * @return 结果
     */
    public int deleteWmArrivalNoticeByNoticeIds(Long[] noticeIds);

    /**
     * 删除到货通知单信息
     * 
     * @param noticeId 到货通知单主键
     * @return 结果
     */
    public int deleteWmArrivalNoticeByNoticeId(Long noticeId);
}
