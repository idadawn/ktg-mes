package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmMiscRecpt;
import com.ktg.mes.wm.domain.tx.MiscIssueTxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscIssueMapper;
import com.ktg.mes.wm.domain.WmMiscIssue;
import com.ktg.mes.wm.service.IWmMiscIssueService;

/**
 * 杂项出库单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscIssueServiceImpl implements IWmMiscIssueService 
{
    @Autowired
    private WmMiscIssueMapper wmMiscIssueMapper;

    /**
     * 查询杂项出库单
     * 
     * @param issueId 杂项出库单主键
     * @return 杂项出库单
     */
    @Override
    public WmMiscIssue selectWmMiscIssueByIssueId(Long issueId)
    {
        return wmMiscIssueMapper.selectWmMiscIssueByIssueId(issueId);
    }

    /**
     * 查询杂项出库单列表
     * 
     * @param wmMiscIssue 杂项出库单
     * @return 杂项出库单
     */
    @Override
    public List<WmMiscIssue> selectWmMiscIssueList(WmMiscIssue wmMiscIssue)
    {
        return wmMiscIssueMapper.selectWmMiscIssueList(wmMiscIssue);
    }

    @Override
    public List<MiscIssueTxBean> getTxBeans(Long issueId) {
        return wmMiscIssueMapper.getTxBeans(issueId);
    }

    @Override
    public String checkUnique(WmMiscIssue wmMiscIssue) {
        WmMiscIssue miscIssue = wmMiscIssueMapper.checkUnique(wmMiscIssue);
        Long issueId = wmMiscIssue.getIssueId()==null?-1L:wmMiscIssue.getIssueId();
        if(StringUtils.isNotNull(miscIssue) && miscIssue.getIssueId().longValue() != issueId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增杂项出库单
     * 
     * @param wmMiscIssue 杂项出库单
     * @return 结果
     */
    @Override
    public int insertWmMiscIssue(WmMiscIssue wmMiscIssue)
    {
        wmMiscIssue.setCreateTime(DateUtils.getNowDate());
        return wmMiscIssueMapper.insertWmMiscIssue(wmMiscIssue);
    }

    /**
     * 修改杂项出库单
     * 
     * @param wmMiscIssue 杂项出库单
     * @return 结果
     */
    @Override
    public int updateWmMiscIssue(WmMiscIssue wmMiscIssue)
    {
        wmMiscIssue.setUpdateTime(DateUtils.getNowDate());
        return wmMiscIssueMapper.updateWmMiscIssue(wmMiscIssue);
    }

    /**
     * 批量删除杂项出库单
     * 
     * @param issueIds 需要删除的杂项出库单主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueByIssueIds(Long[] issueIds)
    {
        return wmMiscIssueMapper.deleteWmMiscIssueByIssueIds(issueIds);
    }

    /**
     * 删除杂项出库单信息
     * 
     * @param issueId 杂项出库单主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscIssueByIssueId(Long issueId)
    {
        return wmMiscIssueMapper.deleteWmMiscIssueByIssueId(issueId);
    }
}
