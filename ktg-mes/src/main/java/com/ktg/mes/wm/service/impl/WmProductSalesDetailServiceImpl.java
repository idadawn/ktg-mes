package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmProductSalesDetailMapper;
import com.ktg.mes.wm.domain.WmProductSalesDetail;
import com.ktg.mes.wm.service.IWmProductSalesDetailService;

/**
 * 产品销售出库记录明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
@Service
public class WmProductSalesDetailServiceImpl implements IWmProductSalesDetailService 
{
    @Autowired
    private WmProductSalesDetailMapper wmProductSalesDetailMapper;

    /**
     * 查询产品销售出库记录明细
     * 
     * @param detailId 产品销售出库记录明细主键
     * @return 产品销售出库记录明细
     */
    @Override
    public WmProductSalesDetail selectWmProductSalesDetailByDetailId(Long detailId)
    {
        return wmProductSalesDetailMapper.selectWmProductSalesDetailByDetailId(detailId);
    }

    /**
     * 查询产品销售出库记录明细列表
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 产品销售出库记录明细
     */
    @Override
    public List<WmProductSalesDetail> selectWmProductSalesDetailList(WmProductSalesDetail wmProductSalesDetail)
    {
        return wmProductSalesDetailMapper.selectWmProductSalesDetailList(wmProductSalesDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmProductSalesDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增产品销售出库记录明细
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 结果
     */
    @Override
    public int insertWmProductSalesDetail(WmProductSalesDetail wmProductSalesDetail)
    {
        wmProductSalesDetail.setCreateTime(DateUtils.getNowDate());
        return wmProductSalesDetailMapper.insertWmProductSalesDetail(wmProductSalesDetail);
    }

    /**
     * 修改产品销售出库记录明细
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 结果
     */
    @Override
    public int updateWmProductSalesDetail(WmProductSalesDetail wmProductSalesDetail)
    {
        wmProductSalesDetail.setUpdateTime(DateUtils.getNowDate());
        return wmProductSalesDetailMapper.updateWmProductSalesDetail(wmProductSalesDetail);
    }

    /**
     * 批量删除产品销售出库记录明细
     * 
     * @param detailIds 需要删除的产品销售出库记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesDetailByDetailIds(Long[] detailIds)
    {
        return wmProductSalesDetailMapper.deleteWmProductSalesDetailByDetailIds(detailIds);
    }

    /**
     * 删除产品销售出库记录明细信息
     * 
     * @param detailId 产品销售出库记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesDetailByDetailId(Long detailId)
    {
        return wmProductSalesDetailMapper.deleteWmProductSalesDetailByDetailId(detailId);
    }
}
