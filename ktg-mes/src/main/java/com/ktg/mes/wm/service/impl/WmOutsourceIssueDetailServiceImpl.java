package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmOutsourceIssueDetailMapper;
import com.ktg.mes.wm.domain.WmOutsourceIssueDetail;
import com.ktg.mes.wm.service.IWmOutsourceIssueDetailService;

/**
 * 外协领料单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-04-12
 */
@Service
public class WmOutsourceIssueDetailServiceImpl implements IWmOutsourceIssueDetailService 
{
    @Autowired
    private WmOutsourceIssueDetailMapper wmOutsourceIssueDetailMapper;

    /**
     * 查询外协领料单明细
     * 
     * @param detailId 外协领料单明细主键
     * @return 外协领料单明细
     */
    @Override
    public WmOutsourceIssueDetail selectWmOutsourceIssueDetailByDetailId(Long detailId)
    {
        return wmOutsourceIssueDetailMapper.selectWmOutsourceIssueDetailByDetailId(detailId);
    }

    /**
     * 查询外协领料单明细列表
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 外协领料单明细
     */
    @Override
    public List<WmOutsourceIssueDetail> selectWmOutsourceIssueDetailList(WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        return wmOutsourceIssueDetailMapper.selectWmOutsourceIssueDetailList(wmOutsourceIssueDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmOutsourceIssueDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增外协领料单明细
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 结果
     */
    @Override
    public int insertWmOutsourceIssueDetail(WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        wmOutsourceIssueDetail.setCreateTime(DateUtils.getNowDate());
        return wmOutsourceIssueDetailMapper.insertWmOutsourceIssueDetail(wmOutsourceIssueDetail);
    }

    /**
     * 修改外协领料单明细
     * 
     * @param wmOutsourceIssueDetail 外协领料单明细
     * @return 结果
     */
    @Override
    public int updateWmOutsourceIssueDetail(WmOutsourceIssueDetail wmOutsourceIssueDetail)
    {
        wmOutsourceIssueDetail.setUpdateTime(DateUtils.getNowDate());
        return wmOutsourceIssueDetailMapper.updateWmOutsourceIssueDetail(wmOutsourceIssueDetail);
    }

    /**
     * 批量删除外协领料单明细
     * 
     * @param detailIds 需要删除的外协领料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceIssueDetailByDetailIds(Long[] detailIds)
    {
        return wmOutsourceIssueDetailMapper.deleteWmOutsourceIssueDetailByDetailIds(detailIds);
    }

    /**
     * 删除外协领料单明细信息
     * 
     * @param detailId 外协领料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceIssueDetailByDetailId(Long detailId)
    {
        return wmOutsourceIssueDetailMapper.deleteWmOutsourceIssueDetailByDetailId(detailId);
    }
}
