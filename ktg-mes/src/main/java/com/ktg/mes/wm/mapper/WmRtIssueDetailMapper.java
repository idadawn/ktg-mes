package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmRtIssueDetail;

/**
 * 生产退料单明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-03-05
 */
public interface WmRtIssueDetailMapper 
{
    /**
     * 查询生产退料单明细
     * 
     * @param detailId 生产退料单明细主键
     * @return 生产退料单明细
     */
    public WmRtIssueDetail selectWmRtIssueDetailByDetailId(Long detailId);

    /**
     * 查询生产退料单明细列表
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 生产退料单明细集合
     */
    public List<WmRtIssueDetail> selectWmRtIssueDetailList(WmRtIssueDetail wmRtIssueDetail);

    /**
     * 检查退料行上的数量是否与明细行总数量一致
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增生产退料单明细
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 结果
     */
    public int insertWmRtIssueDetail(WmRtIssueDetail wmRtIssueDetail);

    /**
     * 修改生产退料单明细
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 结果
     */
    public int updateWmRtIssueDetail(WmRtIssueDetail wmRtIssueDetail);

    /**
     * 删除生产退料单明细
     * 
     * @param detailId 生产退料单明细主键
     * @return 结果
     */
    public int deleteWmRtIssueDetailByDetailId(Long detailId);

    /**
     * 批量删除生产退料单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmRtIssueDetailByDetailIds(Long[] detailIds);
}
