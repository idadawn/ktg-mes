package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmItemRecptDetailMapper;
import com.ktg.mes.wm.domain.WmItemRecptDetail;
import com.ktg.mes.wm.service.IWmItemRecptDetailService;

/**
 * 物料入库单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-02-28
 */
@Service
public class WmItemRecptDetailServiceImpl implements IWmItemRecptDetailService 
{
    @Autowired
    private WmItemRecptDetailMapper wmItemRecptDetailMapper;

    /**
     * 查询物料入库单明细
     * 
     * @param detailId 物料入库单明细主键
     * @return 物料入库单明细
     */
    @Override
    public WmItemRecptDetail selectWmItemRecptDetailByDetailId(Long detailId)
    {
        return wmItemRecptDetailMapper.selectWmItemRecptDetailByDetailId(detailId);
    }

    /**
     * 查询物料入库单明细列表
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 物料入库单明细
     */
    @Override
    public List<WmItemRecptDetail> selectWmItemRecptDetailList(WmItemRecptDetail wmItemRecptDetail)
    {
        return wmItemRecptDetailMapper.selectWmItemRecptDetailList(wmItemRecptDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmItemRecptDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增物料入库单明细
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 结果
     */
    @Override
    public int insertWmItemRecptDetail(WmItemRecptDetail wmItemRecptDetail)
    {
        wmItemRecptDetail.setCreateTime(DateUtils.getNowDate());
        return wmItemRecptDetailMapper.insertWmItemRecptDetail(wmItemRecptDetail);
    }

    /**
     * 修改物料入库单明细
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 结果
     */
    @Override
    public int updateWmItemRecptDetail(WmItemRecptDetail wmItemRecptDetail)
    {
        wmItemRecptDetail.setUpdateTime(DateUtils.getNowDate());
        return wmItemRecptDetailMapper.updateWmItemRecptDetail(wmItemRecptDetail);
    }

    /**
     * 批量删除物料入库单明细
     * 
     * @param detailIds 需要删除的物料入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmItemRecptDetailByDetailIds(Long[] detailIds)
    {
        return wmItemRecptDetailMapper.deleteWmItemRecptDetailByDetailIds(detailIds);
    }

    /**
     * 删除物料入库单明细信息
     * 
     * @param detailId 物料入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmItemRecptDetailByDetailId(Long detailId)
    {
        return wmItemRecptDetailMapper.deleteWmItemRecptDetailByDetailId(detailId);
    }
}
