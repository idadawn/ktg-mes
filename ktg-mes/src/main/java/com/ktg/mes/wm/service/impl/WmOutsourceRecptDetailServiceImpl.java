package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmOutsourceRecptDetailMapper;
import com.ktg.mes.wm.domain.WmOutsourceRecptDetail;
import com.ktg.mes.wm.service.IWmOutsourceRecptDetailService;

/**
 * 外协入库单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-04-12
 */
@Service
public class WmOutsourceRecptDetailServiceImpl implements IWmOutsourceRecptDetailService 
{
    @Autowired
    private WmOutsourceRecptDetailMapper wmOutsourceRecptDetailMapper;

    /**
     * 查询外协入库单明细
     * 
     * @param detailId 外协入库单明细主键
     * @return 外协入库单明细
     */
    @Override
    public WmOutsourceRecptDetail selectWmOutsourceRecptDetailByDetailId(Long detailId)
    {
        return wmOutsourceRecptDetailMapper.selectWmOutsourceRecptDetailByDetailId(detailId);
    }

    /**
     * 查询外协入库单明细列表
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 外协入库单明细
     */
    @Override
    public List<WmOutsourceRecptDetail> selectWmOutsourceRecptDetailList(WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        return wmOutsourceRecptDetailMapper.selectWmOutsourceRecptDetailList(wmOutsourceRecptDetail);
    }

    @Override
    public String checkQuantity(Long lineId) {
        return wmOutsourceRecptDetailMapper.checkQuantity(lineId);
    }

    /**
     * 新增外协入库单明细
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 结果
     */
    @Override
    public int insertWmOutsourceRecptDetail(WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        wmOutsourceRecptDetail.setCreateTime(DateUtils.getNowDate());
        return wmOutsourceRecptDetailMapper.insertWmOutsourceRecptDetail(wmOutsourceRecptDetail);
    }

    /**
     * 修改外协入库单明细
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 结果
     */
    @Override
    public int updateWmOutsourceRecptDetail(WmOutsourceRecptDetail wmOutsourceRecptDetail)
    {
        wmOutsourceRecptDetail.setUpdateTime(DateUtils.getNowDate());
        return wmOutsourceRecptDetailMapper.updateWmOutsourceRecptDetail(wmOutsourceRecptDetail);
    }

    /**
     * 批量删除外协入库单明细
     * 
     * @param detailIds 需要删除的外协入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceRecptDetailByDetailIds(Long[] detailIds)
    {
        return wmOutsourceRecptDetailMapper.deleteWmOutsourceRecptDetailByDetailIds(detailIds);
    }

    /**
     * 删除外协入库单明细信息
     * 
     * @param detailId 外协入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceRecptDetailByDetailId(Long detailId)
    {
        return wmOutsourceRecptDetailMapper.deleteWmOutsourceRecptDetailByDetailId(detailId);
    }
}
