package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscIssueDetailMapper;
import com.ktg.mes.wm.domain.WmMiscIssueDetail;
import com.ktg.mes.wm.service.IWmMiscIssueDetailService;

/**
 * 杂项出库单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscIssueDetailServiceImpl implements IWmMiscIssueDetailService 
{
    @Autowired
    private WmMiscIssueDetailMapper wmMiscIssueDetailMapper;

    /**
     * 查询杂项出库单明细
     * 
     * @param detailId 杂项出库单明细主键
     * @return 杂项出库单明细
     */
    @Override
    public WmMiscIssueDetail selectWmMiscIssueDetailByDetailId(Long detailId)
    {
        return wmMiscIssueDetailMapper.selectWmMiscIssueDetailByDetailId(detailId);
    }

    /**
     * 查询杂项出库单明细列表
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 杂项出库单明细
     */
    @Override
    public List<WmMiscIssueDetail> selectWmMiscIssueDetailList(WmMiscIssueDetail wmMiscIssueDetail)
    {
        return wmMiscIssueDetailMapper.selectWmMiscIssueDetailList(wmMiscIssueDetail);
    }

    /**
     * 新增杂项出库单明细
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 结果
     */
    @Override
    public int insertWmMiscIssueDetail(WmMiscIssueDetail wmMiscIssueDetail)
    {
        wmMiscIssueDetail.setCreateTime(DateUtils.getNowDate());
        return wmMiscIssueDetailMapper.insertWmMiscIssueDetail(wmMiscIssueDetail);
    }

    /**
     * 修改杂项出库单明细
     * 
     * @param wmMiscIssueDetail 杂项出库单明细
     * @return 结果
     */
    @Override
    public int updateWmMiscIssueDetail(WmMiscIssueDetail wmMiscIssueDetail)
    {
        wmMiscIssueDetail.setUpdateTime(DateUtils.getNowDate());
        return wmMiscIssueDetailMapper.updateWmMiscIssueDetail(wmMiscIssueDetail);
    }

    /**
     * 批量删除杂项出库单明细
     * 
     * @param detailIds 需要删除的杂项出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueDetailByDetailIds(Long[] detailIds)
    {
        return wmMiscIssueDetailMapper.deleteWmMiscIssueDetailByDetailIds(detailIds);
    }

    @Override
    public int deleteWmMiscIssueDetailByLineId(Long lineId) {
        return wmMiscIssueDetailMapper.deleteWmMiscIssueDetailByLineId(lineId);
    }

    /**
     * 删除杂项出库单明细信息
     * 
     * @param detailId 杂项出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueDetailByDetailId(Long detailId)
    {
        return wmMiscIssueDetailMapper.deleteWmMiscIssueDetailByDetailId(detailId);
    }
}
