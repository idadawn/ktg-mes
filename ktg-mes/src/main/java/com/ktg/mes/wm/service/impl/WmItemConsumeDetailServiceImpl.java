package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmItemConsumeDetailMapper;
import com.ktg.mes.wm.domain.WmItemConsumeDetail;
import com.ktg.mes.wm.service.IWmItemConsumeDetailService;

/**
 * 物料消耗记录明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-07
 */
@Service
public class WmItemConsumeDetailServiceImpl implements IWmItemConsumeDetailService 
{
    @Autowired
    private WmItemConsumeDetailMapper wmItemConsumeDetailMapper;

    /**
     * 查询物料消耗记录明细
     * 
     * @param detailId 物料消耗记录明细主键
     * @return 物料消耗记录明细
     */
    @Override
    public WmItemConsumeDetail selectWmItemConsumeDetailByDetailId(Long detailId)
    {
        return wmItemConsumeDetailMapper.selectWmItemConsumeDetailByDetailId(detailId);
    }

    /**
     * 查询物料消耗记录明细列表
     * 
     * @param wmItemConsumeDetail 物料消耗记录明细
     * @return 物料消耗记录明细
     */
    @Override
    public List<WmItemConsumeDetail> selectWmItemConsumeDetailList(WmItemConsumeDetail wmItemConsumeDetail)
    {
        return wmItemConsumeDetailMapper.selectWmItemConsumeDetailList(wmItemConsumeDetail);
    }

    /**
     * 新增物料消耗记录明细
     * 
     * @param wmItemConsumeDetail 物料消耗记录明细
     * @return 结果
     */
    @Override
    public int insertWmItemConsumeDetail(WmItemConsumeDetail wmItemConsumeDetail)
    {
        wmItemConsumeDetail.setCreateTime(DateUtils.getNowDate());
        return wmItemConsumeDetailMapper.insertWmItemConsumeDetail(wmItemConsumeDetail);
    }

    /**
     * 修改物料消耗记录明细
     * 
     * @param wmItemConsumeDetail 物料消耗记录明细
     * @return 结果
     */
    @Override
    public int updateWmItemConsumeDetail(WmItemConsumeDetail wmItemConsumeDetail)
    {
        wmItemConsumeDetail.setUpdateTime(DateUtils.getNowDate());
        return wmItemConsumeDetailMapper.updateWmItemConsumeDetail(wmItemConsumeDetail);
    }

    /**
     * 批量删除物料消耗记录明细
     * 
     * @param detailIds 需要删除的物料消耗记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmItemConsumeDetailByDetailIds(Long[] detailIds)
    {
        return wmItemConsumeDetailMapper.deleteWmItemConsumeDetailByDetailIds(detailIds);
    }

    /**
     * 删除物料消耗记录明细信息
     * 
     * @param detailId 物料消耗记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmItemConsumeDetailByDetailId(Long detailId)
    {
        return wmItemConsumeDetailMapper.deleteWmItemConsumeDetailByDetailId(detailId);
    }
}
