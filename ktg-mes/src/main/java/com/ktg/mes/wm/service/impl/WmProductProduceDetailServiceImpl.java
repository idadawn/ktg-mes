package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmProductProduceDetailMapper;
import com.ktg.mes.wm.domain.WmProductProduceDetail;
import com.ktg.mes.wm.service.IWmProductProduceDetailService;

/**
 * 产品产出记录明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-11
 */
@Service
public class WmProductProduceDetailServiceImpl implements IWmProductProduceDetailService 
{
    @Autowired
    private WmProductProduceDetailMapper wmProductProduceDetailMapper;

    /**
     * 查询产品产出记录明细
     * 
     * @param detailId 产品产出记录明细主键
     * @return 产品产出记录明细
     */
    @Override
    public WmProductProduceDetail selectWmProductProduceDetailByDetailId(Long detailId)
    {
        return wmProductProduceDetailMapper.selectWmProductProduceDetailByDetailId(detailId);
    }

    /**
     * 查询产品产出记录明细列表
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 产品产出记录明细
     */
    @Override
    public List<WmProductProduceDetail> selectWmProductProduceDetailList(WmProductProduceDetail wmProductProduceDetail)
    {
        return wmProductProduceDetailMapper.selectWmProductProduceDetailList(wmProductProduceDetail);
    }

    /**
     * 新增产品产出记录明细
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 结果
     */
    @Override
    public int insertWmProductProduceDetail(WmProductProduceDetail wmProductProduceDetail)
    {
        wmProductProduceDetail.setCreateTime(DateUtils.getNowDate());
        return wmProductProduceDetailMapper.insertWmProductProduceDetail(wmProductProduceDetail);
    }

    /**
     * 修改产品产出记录明细
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 结果
     */
    @Override
    public int updateWmProductProduceDetail(WmProductProduceDetail wmProductProduceDetail)
    {
        wmProductProduceDetail.setUpdateTime(DateUtils.getNowDate());
        return wmProductProduceDetailMapper.updateWmProductProduceDetail(wmProductProduceDetail);
    }

    /**
     * 批量删除产品产出记录明细
     * 
     * @param detailIds 需要删除的产品产出记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductProduceDetailByDetailIds(Long[] detailIds)
    {
        return wmProductProduceDetailMapper.deleteWmProductProduceDetailByDetailIds(detailIds);
    }

    /**
     * 删除产品产出记录明细信息
     * 
     * @param detailId 产品产出记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductProduceDetailByDetailId(Long detailId)
    {
        return wmProductProduceDetailMapper.deleteWmProductProduceDetailByDetailId(detailId);
    }
}
