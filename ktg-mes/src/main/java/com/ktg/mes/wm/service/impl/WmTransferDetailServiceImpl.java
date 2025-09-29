package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmTransferDetailMapper;
import com.ktg.mes.wm.domain.WmTransferDetail;
import com.ktg.mes.wm.service.IWmTransferDetailService;

/**
 * 转移调拨单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-18
 */
@Service
public class WmTransferDetailServiceImpl implements IWmTransferDetailService 
{
    @Autowired
    private WmTransferDetailMapper wmTransferDetailMapper;

    /**
     * 查询转移调拨单明细
     * 
     * @param detailId 转移调拨单明细主键
     * @return 转移调拨单明细
     */
    @Override
    public WmTransferDetail selectWmTransferDetailByDetailId(Long detailId)
    {
        return wmTransferDetailMapper.selectWmTransferDetailByDetailId(detailId);
    }

    /**
     * 查询转移调拨单明细列表
     * 
     * @param wmTransferDetail 转移调拨单明细
     * @return 转移调拨单明细
     */
    @Override
    public List<WmTransferDetail> selectWmTransferDetailList(WmTransferDetail wmTransferDetail)
    {
        return wmTransferDetailMapper.selectWmTransferDetailList(wmTransferDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmTransferDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增转移调拨单明细
     * 
     * @param wmTransferDetail 转移调拨单明细
     * @return 结果
     */
    @Override
    public int insertWmTransferDetail(WmTransferDetail wmTransferDetail)
    {
        wmTransferDetail.setCreateTime(DateUtils.getNowDate());
        return wmTransferDetailMapper.insertWmTransferDetail(wmTransferDetail);
    }

    /**
     * 修改转移调拨单明细
     * 
     * @param wmTransferDetail 转移调拨单明细
     * @return 结果
     */
    @Override
    public int updateWmTransferDetail(WmTransferDetail wmTransferDetail)
    {
        wmTransferDetail.setUpdateTime(DateUtils.getNowDate());
        return wmTransferDetailMapper.updateWmTransferDetail(wmTransferDetail);
    }

    /**
     * 批量删除转移调拨单明细
     * 
     * @param detailIds 需要删除的转移调拨单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmTransferDetailByDetailIds(Long[] detailIds)
    {
        return wmTransferDetailMapper.deleteWmTransferDetailByDetailIds(detailIds);
    }

    /**
     * 删除转移调拨单明细信息
     * 
     * @param detailId 转移调拨单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmTransferDetailByDetailId(Long detailId)
    {
        return wmTransferDetailMapper.deleteWmTransferDetailByDetailId(detailId);
    }
}
