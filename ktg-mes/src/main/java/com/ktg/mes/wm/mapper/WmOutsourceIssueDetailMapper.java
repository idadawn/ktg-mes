package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmOutsourceIssueDetail;

/**
 * 外协领料单明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-04-12
 */
public interface WmOutsourceIssueDetailMapper 
{
    /**
     * 查询外协领料单明细
     * 
     * @param detailId 外协领料单明细主键
     * @return 外协领料单明细
     */
    public WmOutsourceIssueDetail selectWmOutsourceIssueDetailByDetailId(Long detailId);

    /**
     * 查询外协领料单明细列表
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 外协领料单明细集合
     */
    public List<WmOutsourceIssueDetail> selectWmOutsourceIssueDetailList(WmOutsourceIssueDetail wmOutsourceIssueDetail);

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
     * 新增外协领料单明细
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 结果
     */
    public int insertWmOutsourceIssueDetail(WmOutsourceIssueDetail wmOutsourceIssueDetail);

    /**
     * 修改外协领料单明细
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 结果
     */
    public int updateWmOutsourceIssueDetail(WmOutsourceIssueDetail wmOutsourceIssueDetail);

    /**
     * 删除外协领料单明细
     * 
     * @param detailId 外协领料单明细主键
     * @return 结果
     */
    public int deleteWmOutsourceIssueDetailByDetailId(Long detailId);

    /**
     * 批量删除外协领料单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmOutsourceIssueDetailByDetailIds(Long[] detailIds);
}
