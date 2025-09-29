package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscRecptDetail;

/**
 * 杂项入库单明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface WmMiscRecptDetailMapper 
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
     * 删除杂项入库单明细
     * 
     * @param detailId 杂项入库单明细主键
     * @return 结果
     */
    public int deleteWmMiscRecptDetailByDetailId(Long detailId);


    public int deleteWmMiscRecptDetailByLineId(Long lineId);

    /**
     * 批量删除杂项入库单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmMiscRecptDetailByDetailIds(Long[] detailIds);
}
