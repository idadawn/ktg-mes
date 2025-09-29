package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscRecptDetailMapper;
import com.ktg.mes.wm.domain.WmMiscRecptDetail;
import com.ktg.mes.wm.service.IWmMiscRecptDetailService;

/**
 * 杂项入库单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscRecptDetailServiceImpl implements IWmMiscRecptDetailService 
{
    @Autowired
    private WmMiscRecptDetailMapper wmMiscRecptDetailMapper;

    /**
     * 查询杂项入库单明细
     * 
     * @param detailId 杂项入库单明细主键
     * @return 杂项入库单明细
     */
    @Override
    public WmMiscRecptDetail selectWmMiscRecptDetailByDetailId(Long detailId)
    {
        return wmMiscRecptDetailMapper.selectWmMiscRecptDetailByDetailId(detailId);
    }

    /**
     * 查询杂项入库单明细列表
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 杂项入库单明细
     */
    @Override
    public List<WmMiscRecptDetail> selectWmMiscRecptDetailList(WmMiscRecptDetail wmMiscRecptDetail)
    {
        return wmMiscRecptDetailMapper.selectWmMiscRecptDetailList(wmMiscRecptDetail);
    }

    /**
     * 新增杂项入库单明细
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 结果
     */
    @Override
    public int insertWmMiscRecptDetail(WmMiscRecptDetail wmMiscRecptDetail)
    {
        wmMiscRecptDetail.setCreateTime(DateUtils.getNowDate());
        return wmMiscRecptDetailMapper.insertWmMiscRecptDetail(wmMiscRecptDetail);
    }

    /**
     * 修改杂项入库单明细
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 结果
     */
    @Override
    public int updateWmMiscRecptDetail(WmMiscRecptDetail wmMiscRecptDetail)
    {
        wmMiscRecptDetail.setUpdateTime(DateUtils.getNowDate());
        return wmMiscRecptDetailMapper.updateWmMiscRecptDetail(wmMiscRecptDetail);
    }

    /**
     * 批量删除杂项入库单明细
     * 
     * @param detailIds 需要删除的杂项入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptDetailByDetailIds(Long[] detailIds)
    {
        return wmMiscRecptDetailMapper.deleteWmMiscRecptDetailByDetailIds(detailIds);
    }

    @Override
    public int deleteWmMiscRecptDetailByLineId(Long lineId) {
        return wmMiscRecptDetailMapper.deleteWmMiscRecptDetailByLineId(lineId);
    }

    /**
     * 删除杂项入库单明细信息
     * 
     * @param detailId 杂项入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptDetailByDetailId(Long detailId)
    {
        return wmMiscRecptDetailMapper.deleteWmMiscRecptDetailByDetailId(detailId);
    }
}
