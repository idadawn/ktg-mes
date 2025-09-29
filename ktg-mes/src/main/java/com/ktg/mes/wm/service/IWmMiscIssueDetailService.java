package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscIssueDetail;

/**
 * 杂项出库单明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface IWmMiscIssueDetailService 
{
    /**
     * 查询杂项出库单明细
     * 
     * @param detailId 杂项出库单明细主键
     * @return 杂项出库单明细
     */
    public WmMiscIssueDetail selectWmMiscIssueDetailByDetailId(Long detailId);

    /**
     * 查询杂项出库单明细列表
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 杂项出库单明细集合
     */
    public List<WmMiscIssueDetail> selectWmMiscIssueDetailList(WmMiscIssueDetail wmMiscIssueDetail);

    /**
     * 新增杂项出库单明细
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 结果
     */
    public int insertWmMiscIssueDetail(WmMiscIssueDetail wmMiscIssueDetail);

    /**
     * 修改杂项出库单明细
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 结果
     */
    public int updateWmMiscIssueDetail(WmMiscIssueDetail wmMiscIssueDetail);

    /**
     * 批量删除杂项出库单明细
     * 
     * @param detailIds 需要删除的杂项出库单明细主键集合
     * @return 结果
     */
    public int deleteWmMiscIssueDetailByDetailIds(Long[] detailIds);

    /**
     * 根据行ID删除对应的明细记录
     * @param lineId
     * @return
     */
    public int deleteWmMiscIssueDetailByLineId(Long lineId);

    /**
     * 删除杂项出库单明细信息
     * 
     * @param detailId 杂项出库单明细主键
     * @return 结果
     */
    public int deleteWmMiscIssueDetailByDetailId(Long detailId);
}
