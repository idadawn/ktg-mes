package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmArrivalNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmSalesNoticeMapper;
import com.ktg.mes.wm.domain.WmSalesNotice;
import com.ktg.mes.wm.service.IWmSalesNoticeService;

/**
 * 发货通知单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
@Service
public class WmSalesNoticeServiceImpl implements IWmSalesNoticeService 
{
    @Autowired
    private WmSalesNoticeMapper wmSalesNoticeMapper;

    /**
     * 查询发货通知单
     * 
     * @param noticeId 发货通知单主键
     * @return 发货通知单
     */
    @Override
    public WmSalesNotice selectWmSalesNoticeByNoticeId(Long noticeId)
    {
        return wmSalesNoticeMapper.selectWmSalesNoticeByNoticeId(noticeId);
    }

    /**
     * 查询发货通知单列表
     * 
     * @param wmSalesNotice 发货通知单
     * @return 发货通知单
     */
    @Override
    public List<WmSalesNotice> selectWmSalesNoticeList(WmSalesNotice wmSalesNotice)
    {
        return wmSalesNoticeMapper.selectWmSalesNoticeList(wmSalesNotice);
    }

    @Override
    public String checkCodeUnique(WmSalesNotice wmSalesNotice) {
        WmSalesNotice notice = wmSalesNoticeMapper.checkCodeUnique(wmSalesNotice);
        Long noticeId = notice == null? -1L:notice.getNoticeId();
        if(StringUtils.isNotNull(notice) && notice.getNoticeId().longValue() != noticeId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增发货通知单
     * 
     * @param wmSalesNotice 发货通知单
     * @return 结果
     */
    @Override
    public int insertWmSalesNotice(WmSalesNotice wmSalesNotice)
    {
        wmSalesNotice.setCreateTime(DateUtils.getNowDate());
        return wmSalesNoticeMapper.insertWmSalesNotice(wmSalesNotice);
    }

    /**
     * 修改发货通知单
     * 
     * @param wmSalesNotice 发货通知单
     * @return 结果
     */
    @Override
    public int updateWmSalesNotice(WmSalesNotice wmSalesNotice)
    {
        wmSalesNotice.setUpdateTime(DateUtils.getNowDate());
        return wmSalesNoticeMapper.updateWmSalesNotice(wmSalesNotice);
    }

    /**
     * 批量删除发货通知单
     * 
     * @param noticeIds 需要删除的发货通知单主键
     * @return 结果
     */
    @Override
    public int deleteWmSalesNoticeByNoticeIds(Long[] noticeIds)
    {
        return wmSalesNoticeMapper.deleteWmSalesNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除发货通知单信息
     * 
     * @param noticeId 发货通知单主键
     * @return 结果
     */
    @Override
    public int deleteWmSalesNoticeByNoticeId(Long noticeId)
    {
        return wmSalesNoticeMapper.deleteWmSalesNoticeByNoticeId(noticeId);
    }
}
