package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMaterialrequestNoticeMapper;
import com.ktg.mes.wm.domain.WmMaterialrequestNotice;
import com.ktg.mes.wm.service.IWmMaterialrequestNoticeService;

/**
 * 备料通知单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
@Service
public class WmMaterialrequestNoticeServiceImpl implements IWmMaterialrequestNoticeService 
{
    @Autowired
    private WmMaterialrequestNoticeMapper wmMaterialrequestNoticeMapper;

    /**
     * 查询备料通知单
     * 
     * @param noticeId 备料通知单主键
     * @return 备料通知单
     */
    @Override
    public WmMaterialrequestNotice selectWmMaterialrequestNoticeByNoticeId(Long noticeId)
    {
        return wmMaterialrequestNoticeMapper.selectWmMaterialrequestNoticeByNoticeId(noticeId);
    }

    /**
     * 查询备料通知单列表
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 备料通知单
     */
    @Override
    public List<WmMaterialrequestNotice> selectWmMaterialrequestNoticeList(WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        return wmMaterialrequestNoticeMapper.selectWmMaterialrequestNoticeList(wmMaterialrequestNotice);
    }

    /**
     * 新增备料通知单
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 结果
     */
    @Override
    public int insertWmMaterialrequestNotice(WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        wmMaterialrequestNotice.setCreateTime(DateUtils.getNowDate());
        return wmMaterialrequestNoticeMapper.insertWmMaterialrequestNotice(wmMaterialrequestNotice);
    }

    /**
     * 修改备料通知单
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 结果
     */
    @Override
    public int updateWmMaterialrequestNotice(WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        wmMaterialrequestNotice.setUpdateTime(DateUtils.getNowDate());
        return wmMaterialrequestNoticeMapper.updateWmMaterialrequestNotice(wmMaterialrequestNotice);
    }

    /**
     * 批量删除备料通知单
     * 
     * @param noticeIds 需要删除的备料通知单主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialrequestNoticeByNoticeIds(Long[] noticeIds)
    {
        return wmMaterialrequestNoticeMapper.deleteWmMaterialrequestNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除备料通知单信息
     * 
     * @param noticeId 备料通知单主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialrequestNoticeByNoticeId(Long noticeId)
    {
        return wmMaterialrequestNoticeMapper.deleteWmMaterialrequestNoticeByNoticeId(noticeId);
    }
}
