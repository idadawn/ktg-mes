package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscRecptDetail;

/**
 * 杂项入库单明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface IWmMiscRecptDetailService 
{
    /**
     * 查询杂项入库单明细
     * 
     * @param detailId 杂项入库单明细主键
     * @return 杂项入库单明细
     */
    public WmMiscRecptDetail selectWmMiscRecptDetailByDetailId(Long detailId);

    /**
     * 查询杂项入库单明细列表
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 杂项入库单明细集合
     */
    public List<WmMiscRecptDetail> selectWmMiscRecptDetailList(WmMiscRecptDetail wmMiscRecptDetail);

    /**
     * 新增杂项入库单明细
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 结果
     */
    public int insertWmMiscRecptDetail(WmMiscRecptDetail wmMiscRecptDetail);

    /**
     * 修改杂项入库单明细
     * 
     * @param wmMiscRecptDetail 杂项入库单明细
     * @return 结果
     */
    public int updateWmMiscRecptDetail(WmMiscRecptDetail wmMiscRecptDetail);

    /**
     * 批量删除杂项入库单明细
     * 
     * @param detailIds 需要删除的杂项入库单明细主键集合
     * @return 结果
     */
    public int deleteWmMiscRecptDetailByDetailIds(Long[] detailIds);

    /**
     * 根据行ID删除对应的明细记录ID
     * @param lineId
     * @return
     */
    public int deleteWmMiscRecptDetailByLineId(Long lineId);

    /**
     * 删除杂项入库单明细信息
     * 
     * @param detailId 杂项入库单明细主键
     * @return 结果
     */
    public int deleteWmMiscRecptDetailByDetailId(Long detailId);
}
