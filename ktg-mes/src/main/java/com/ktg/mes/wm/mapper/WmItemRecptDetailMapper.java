package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmItemRecptDetail;

/**
 * 物料入库单明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-02-28
 */
public interface WmItemRecptDetailMapper 
{
    /**
     * 查询物料入库单明细
     * 
     * @param detailId 物料入库单明细主键
     * @return 物料入库单明细
     */
    public WmItemRecptDetail selectWmItemRecptDetailByDetailId(Long detailId);

    /**
     * 查询物料入库单明细列表
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 物料入库单明细集合
     */
    public List<WmItemRecptDetail> selectWmItemRecptDetailList(WmItemRecptDetail wmItemRecptDetail);

    /**
     * 检查某一行的明细数量是不是超出行上的数量
     * Y:超出
     * N:没超出
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增物料入库单明细
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 结果
     */
    public int insertWmItemRecptDetail(WmItemRecptDetail wmItemRecptDetail);

    /**
     * 修改物料入库单明细
     * 
     * @param wmItemRecptDetail 物料入库单明细
     * @return 结果
     */
    public int updateWmItemRecptDetail(WmItemRecptDetail wmItemRecptDetail);

    /**
     * 删除物料入库单明细
     * 
     * @param detailId 物料入库单明细主键
     * @return 结果
     */
    public int deleteWmItemRecptDetailByDetailId(Long detailId);

    /**
     * 批量删除物料入库单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmItemRecptDetailByDetailIds(Long[] detailIds);
}
