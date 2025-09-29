package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmMaterialrequestNoticeLine;

/**
 * 备料通知单明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
public interface WmMaterialrequestNoticeLineMapper 
{
    /**
     * 查询备料通知单明细
     * 
     * @param lineId 备料通知单明细主键
     * @return 备料通知单明细
     */
    public WmMaterialrequestNoticeLine selectWmMaterialrequestNoticeLineByLineId(Long lineId);

    /**
     * 查询备料通知单明细列表
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 备料通知单明细集合
     */
    public List<WmMaterialrequestNoticeLine> selectWmMaterialrequestNoticeLineList(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine);

    /**
     * 新增备料通知单明细
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 结果
     */
    public int insertWmMaterialrequestNoticeLine(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine);

    /**
     * 修改备料通知单明细
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 结果
     */
    public int updateWmMaterialrequestNoticeLine(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine);

    /**
     * 删除备料通知单明细
     * 
     * @param lineId 备料通知单明细主键
     * @return 结果
     */
    public int deleteWmMaterialrequestNoticeLineByLineId(Long lineId);

    /**
     * 批量删除备料通知单明细
     * 
     * @param lineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmMaterialrequestNoticeLineByLineIds(Long[] lineIds);
}
