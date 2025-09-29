package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmRtSalesDetailMapper;
import com.ktg.mes.wm.domain.WmRtSalesDetail;
import com.ktg.mes.wm.service.IWmRtSalesDetailService;

/**
 * 销售退货记录明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-16
 */
@Service
public class WmRtSalesDetailServiceImpl implements IWmRtSalesDetailService 
{
    @Autowired
    private WmRtSalesDetailMapper wmRtSalesDetailMapper;

    /**
     * 查询销售退货记录明细
     * 
     * @param detailId 销售退货记录明细主键
     * @return 销售退货记录明细
     */
    @Override
    public WmRtSalesDetail selectWmRtSalesDetailByDetailId(Long detailId)
    {
        return wmRtSalesDetailMapper.selectWmRtSalesDetailByDetailId(detailId);
    }

    /**
     * 查询销售退货记录明细列表
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 销售退货记录明细
     */
    @Override
    public List<WmRtSalesDetail> selectWmRtSalesDetailList(WmRtSalesDetail wmRtSalesDetail)
    {
        return wmRtSalesDetailMapper.selectWmRtSalesDetailList(wmRtSalesDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmRtSalesDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增销售退货记录明细
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 结果
     */
    @Override
    public int insertWmRtSalesDetail(WmRtSalesDetail wmRtSalesDetail)
    {
        wmRtSalesDetail.setCreateTime(DateUtils.getNowDate());
        return wmRtSalesDetailMapper.insertWmRtSalesDetail(wmRtSalesDetail);
    }

    /**
     * 修改销售退货记录明细
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 结果
     */
    @Override
    public int updateWmRtSalesDetail(WmRtSalesDetail wmRtSalesDetail)
    {
        wmRtSalesDetail.setUpdateTime(DateUtils.getNowDate());
        return wmRtSalesDetailMapper.updateWmRtSalesDetail(wmRtSalesDetail);
    }

    /**
     * 批量删除销售退货记录明细
     * 
     * @param detailIds 需要删除的销售退货记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesDetailByDetailIds(Long[] detailIds)
    {
        return wmRtSalesDetailMapper.deleteWmRtSalesDetailByDetailIds(detailIds);
    }

    /**
     * 删除销售退货记录明细信息
     * 
     * @param detailId 销售退货记录明细主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesDetailByDetailId(Long detailId)
    {
        return wmRtSalesDetailMapper.deleteWmRtSalesDetailByDetailId(detailId);
    }
}
