package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmProductRecptDetailMapper;
import com.ktg.mes.wm.domain.WmProductRecptDetail;
import com.ktg.mes.wm.service.IWmProductRecptDetailService;

/**
 * 产品入库记录明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-13
 */
@Service
public class WmProductRecptDetailServiceImpl implements IWmProductRecptDetailService 
{
    @Autowired
    private WmProductRecptDetailMapper wmProductRecptDetailMapper;

    /**
     * 查询产品入库记录明细
     * 
     * @param detailId 产品入库记录明细主键
     * @return 产品入库记录明细
     */
    @Override
    public WmProductRecptDetail selectWmProductRecptDetailByDetailId(Long detailId)
    {
        return wmProductRecptDetailMapper.selectWmProductRecptDetailByDetailId(detailId);
    }

    /**
     * 查询产品入库记录明细列表
     * 
     * @param wmProductRecptDetail 产品入库记录明细
     * @return 产品入库记录明细
     */
    @Override
    public List<WmProductRecptDetail> selectWmProductRecptDetailList(WmProductRecptDetail wmProductRecptDetail)
    {
        return wmProductRecptDetailMapper.selectWmProductRecptDetailList(wmProductRecptDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmProductRecptDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增产品入库记录明细
     * 
     * @param wmProductRecptDetail 产品入库记录明细
     * @return 结果
     */
    @Override
    public int insertWmProductRecptDetail(WmProductRecptDetail wmProductRecptDetail)
    {
        wmProductRecptDetail.setCreateTime(DateUtils.getNowDate());
        return wmProductRecptDetailMapper.insertWmProductRecptDetail(wmProductRecptDetail);
    }

    /**
     * 修改产品入库记录明细
     * 
     * @param wmProductRecptDetail 产品入库记录明细
     * @return 结果
     */
    @Override
    public int updateWmProductRecptDetail(WmProductRecptDetail wmProductRecptDetail)
    {
        wmProductRecptDetail.setUpdateTime(DateUtils.getNowDate());
        return wmProductRecptDetailMapper.updateWmProductRecptDetail(wmProductRecptDetail);
    }

    /**
     * 批量删除产品入库记录明细
     * 
     * @param detailIds 需要删除的产品入库记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptDetailByDetailIds(Long[] detailIds)
    {
        return wmProductRecptDetailMapper.deleteWmProductRecptDetailByDetailIds(detailIds);
    }

    /**
     * 删除产品入库记录明细信息
     * 
     * @param detailId 产品入库记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptDetailByDetailId(Long detailId)
    {
        return wmProductRecptDetailMapper.deleteWmProductRecptDetailByDetailId(detailId);
    }
}
