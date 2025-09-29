package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmMaterialrequestNotice;

/**
 * 备料通知单Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
public interface WmMaterialrequestNoticeMapper 
{
    /**
     * 查询备料通知单
     * 
     * @param noticeId 备料通知单主键
     * @return 备料通知单
     */
    public WmMaterialrequestNotice selectWmMaterialrequestNoticeByNoticeId(Long noticeId);

    /**
     * 查询备料通知单列表
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 备料通知单集合
     */
    public List<WmMaterialrequestNotice> selectWmMaterialrequestNoticeList(WmMaterialrequestNotice wmMaterialrequestNotice);

    /**
     * 新增备料通知单
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 结果
     */
    public int insertWmMaterialrequestNotice(WmMaterialrequestNotice wmMaterialrequestNotice);

    /**
     * 修改备料通知单
     * 
     * @param wmMaterialrequestNotice 备料通知单
     * @return 结果
     */
    public int updateWmMaterialrequestNotice(WmMaterialrequestNotice wmMaterialrequestNotice);

    /**
     * 删除备料通知单
     * 
     * @param noticeId 备料通知单主键
     * @return 结果
     */
    public int deleteWmMaterialrequestNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除备料通知单
     * 
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmMaterialrequestNoticeByNoticeIds(Long[] noticeIds);
}
