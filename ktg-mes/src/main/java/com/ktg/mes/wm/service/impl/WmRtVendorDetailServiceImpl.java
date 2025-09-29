package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmRtVendorDetailMapper;
import com.ktg.mes.wm.domain.WmRtVendorDetail;
import com.ktg.mes.wm.service.IWmRtVendorDetailService;

/**
 * 采购退货单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-03
 */
@Service
public class WmRtVendorDetailServiceImpl implements IWmRtVendorDetailService 
{
    @Autowired
    private WmRtVendorDetailMapper wmRtVendorDetailMapper;

    /**
     * 查询采购退货单明细
     * 
     * @param detailId 采购退货单明细主键
     * @return 采购退货单明细
     */
    @Override
    public WmRtVendorDetail selectWmRtVendorDetailByDetailId(Long detailId)
    {
        return wmRtVendorDetailMapper.selectWmRtVendorDetailByDetailId(detailId);
    }

    /**
     * 查询采购退货单明细列表
     * 
     * @param wmRtVendorDetail 采购退货单明细
     * @return 采购退货单明细
     */
    @Override
    public List<WmRtVendorDetail> selectWmRtVendorDetailList(WmRtVendorDetail wmRtVendorDetail)
    {
        return wmRtVendorDetailMapper.selectWmRtVendorDetailList(wmRtVendorDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmRtVendorDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增采购退货单明细
     * 
     * @param wmRtVendorDetail 采购退货单明细
     * @return 结果
     */
    @Override
    public int insertWmRtVendorDetail(WmRtVendorDetail wmRtVendorDetail)
    {
        wmRtVendorDetail.setCreateTime(DateUtils.getNowDate());
        return wmRtVendorDetailMapper.insertWmRtVendorDetail(wmRtVendorDetail);
    }

    /**
     * 修改采购退货单明细
     * 
     * @param wmRtVendorDetail 采购退货单明细
     * @return 结果
     */
    @Override
    public int updateWmRtVendorDetail(WmRtVendorDetail wmRtVendorDetail)
    {
        wmRtVendorDetail.setUpdateTime(DateUtils.getNowDate());
        return wmRtVendorDetailMapper.updateWmRtVendorDetail(wmRtVendorDetail);
    }

    /**
     * 批量删除采购退货单明细
     * 
     * @param detailIds 需要删除的采购退货单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtVendorDetailByDetailIds(Long[] detailIds)
    {
        return wmRtVendorDetailMapper.deleteWmRtVendorDetailByDetailIds(detailIds);
    }

    /**
     * 删除采购退货单明细信息
     * 
     * @param detailId 采购退货单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtVendorDetailByDetailId(Long detailId)
    {
        return wmRtVendorDetailMapper.deleteWmRtVendorDetailByDetailId(detailId);
    }
}
