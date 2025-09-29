package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmRtIssueDetailMapper;
import com.ktg.mes.wm.domain.WmRtIssueDetail;
import com.ktg.mes.wm.service.IWmRtIssueDetailService;

/**
 * 生产退料单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-05
 */
@Service
public class WmRtIssueDetailServiceImpl implements IWmRtIssueDetailService 
{
    @Autowired
    private WmRtIssueDetailMapper wmRtIssueDetailMapper;

    /**
     * 查询生产退料单明细
     * 
     * @param detailId 生产退料单明细主键
     * @return 生产退料单明细
     */
    @Override
    public WmRtIssueDetail selectWmRtIssueDetailByDetailId(Long detailId)
    {
        return wmRtIssueDetailMapper.selectWmRtIssueDetailByDetailId(detailId);
    }

    /**
     * 查询生产退料单明细列表
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 生产退料单明细
     */
    @Override
    public List<WmRtIssueDetail> selectWmRtIssueDetailList(WmRtIssueDetail wmRtIssueDetail)
    {
        return wmRtIssueDetailMapper.selectWmRtIssueDetailList(wmRtIssueDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmRtIssueDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增生产退料单明细
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 结果
     */
    @Override
    public int insertWmRtIssueDetail(WmRtIssueDetail wmRtIssueDetail)
    {
        wmRtIssueDetail.setCreateTime(DateUtils.getNowDate());
        return wmRtIssueDetailMapper.insertWmRtIssueDetail(wmRtIssueDetail);
    }

    /**
     * 修改生产退料单明细
     * 
     * @param wmRtIssueDetail 生产退料单明细
     * @return 结果
     */
    @Override
    public int updateWmRtIssueDetail(WmRtIssueDetail wmRtIssueDetail)
    {
        wmRtIssueDetail.setUpdateTime(DateUtils.getNowDate());
        return wmRtIssueDetailMapper.updateWmRtIssueDetail(wmRtIssueDetail);
    }

    /**
     * 批量删除生产退料单明细
     * 
     * @param detailIds 需要删除的生产退料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueDetailByDetailIds(Long[] detailIds)
    {
        return wmRtIssueDetailMapper.deleteWmRtIssueDetailByDetailIds(detailIds);
    }

    /**
     * 删除生产退料单明细信息
     * 
     * @param detailId 生产退料单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueDetailByDetailId(Long detailId)
    {
        return wmRtIssueDetailMapper.deleteWmRtIssueDetailByDetailId(detailId);
    }
}
