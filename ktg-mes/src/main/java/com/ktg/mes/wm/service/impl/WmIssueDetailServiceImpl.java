package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmIssueDetailMapper;
import com.ktg.mes.wm.domain.WmIssueDetail;
import com.ktg.mes.wm.service.IWmIssueDetailService;

/**
 * 生产领料单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-04
 */
@Service
public class WmIssueDetailServiceImpl implements IWmIssueDetailService 
{
    @Autowired
    private WmIssueDetailMapper wmIssueDetailMapper;

    /**
     * 查询生产领料单明细
     * 
     * @param detailId 生产领料单明细主键
     * @return 生产领料单明细
     */
    @Override
    public WmIssueDetail selectWmIssueDetailByDetailId(Long detailId)
    {
        return wmIssueDetailMapper.selectWmIssueDetailByDetailId(detailId);
    }

    /**
     * 查询生产领料单明细列表
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 生产领料单明细
     */
    @Override
    public List<WmIssueDetail> selectWmIssueDetailList(WmIssueDetail wmIssueDetail)
    {
        return wmIssueDetailMapper.selectWmIssueDetailList(wmIssueDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmIssueDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增生产领料单明细
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 结果
     */
    @Override
    public int insertWmIssueDetail(WmIssueDetail wmIssueDetail)
    {
        wmIssueDetail.setCreateTime(DateUtils.getNowDate());
        return wmIssueDetailMapper.insertWmIssueDetail(wmIssueDetail);
    }

    /**
     * 修改生产领料单明细
     * 
     * @param wmIssueDetail 生产领料单明细
     * @return 结果
     */
    @Override
    public int updateWmIssueDetail(WmIssueDetail wmIssueDetail)
    {
        wmIssueDetail.setUpdateTime(DateUtils.getNowDate());
        return wmIssueDetailMapper.updateWmIssueDetail(wmIssueDetail);
    }

    /**
     * 批量删除生产领料单明细
     * 
     * @param detailIds 需要删除的生产领料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmIssueDetailByDetailIds(Long[] detailIds)
    {
        return wmIssueDetailMapper.deleteWmIssueDetailByDetailIds(detailIds);
    }

    /**
     * 删除生产领料单明细信息
     * 
     * @param detailId 生产领料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmIssueDetailByDetailId(Long detailId)
    {
        return wmIssueDetailMapper.deleteWmIssueDetailByDetailId(detailId);
    }
}
