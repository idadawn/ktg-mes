package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmIssueDetail;

/**
 * 生产领料单明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-03-04
 */
public interface IWmIssueDetailService 
{
    /**
     * 查询生产领料单明细
     * 
     * @param detailId 生产领料单明细主键
     * @return 生产领料单明细
     */
    public WmIssueDetail selectWmIssueDetailByDetailId(Long detailId);

    /**
     * 查询生产领料单明细列表
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 生产领料单明细集合
     */
    public List<WmIssueDetail> selectWmIssueDetailList(WmIssueDetail wmIssueDetail);


    /**
     * 检查某一行的明细数量是不是超出行上的数量
     * G:超出
     * E:等于
     * L:小于
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增生产领料单明细
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 结果
     */
    public int insertWmIssueDetail(WmIssueDetail wmIssueDetail);

    /**
     * 修改生产领料单明细
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 结果
     */
    public int updateWmIssueDetail(WmIssueDetail wmIssueDetail);

    /**
     * 批量删除生产领料单明细
     * 
     * @param detailIds 需要删除的生产领料单明细主键集合
     * @return 结果
     */
    public int deleteWmIssueDetailByDetailIds(Long[] detailIds);

    /**
     * 删除生产领料单明细信息
     * 
     * @param detailId 生产领料单明细主键
     * @return 结果
     */
    public int deleteWmIssueDetailByDetailId(Long detailId);
}
