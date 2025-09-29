package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmSalesNotice;

/**
 * 发货通知单Service接口
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
public interface IWmSalesNoticeService 
{
    /**
     * 查询发货通知单
     * 
     * @param noticeId 发货通知单主键
     * @return 发货通知单
     */
    public WmSalesNotice selectWmSalesNoticeByNoticeId(Long noticeId);

    /**
     * 查询发货通知单列表
     * 
     * @param wmSalesNotice 发货通知单
     * @return 发货通知单集合
     */
    public List<WmSalesNotice> selectWmSalesNoticeList(WmSalesNotice wmSalesNotice);


    public String checkCodeUnique(WmSalesNotice wmSalesNotice);


    /**
     * 新增发货通知单
     * 
     * @param wmSalesNotice 发货通知单
     * @return 结果
     */
    public int insertWmSalesNotice(WmSalesNotice wmSalesNotice);

    /**
     * 修改发货通知单
     * 
     * @param wmSalesNotice 发货通知单
     * @return 结果
     */
    public int updateWmSalesNotice(WmSalesNotice wmSalesNotice);

    /**
     * 批量删除发货通知单
     * 
     * @param noticeIds 需要删除的发货通知单主键集合
     * @return 结果
     */
    public int deleteWmSalesNoticeByNoticeIds(Long[] noticeIds);

    /**
     * 删除发货通知单信息
     * 
     * @param noticeId 发货通知单主键
     * @return 结果
     */
    public int deleteWmSalesNoticeByNoticeId(Long noticeId);
}
