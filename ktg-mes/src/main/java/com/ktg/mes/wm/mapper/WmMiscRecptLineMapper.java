package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscRecptLine;

/**
 * 杂项入库单行Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface WmMiscRecptLineMapper 
{
    /**
     * 查询杂项入库单行
     * 
     * @param lineId 杂项入库单行主键
     * @return 杂项入库单行
     */
    public WmMiscRecptLine selectWmMiscRecptLineByLineId(Long lineId);

    /**
     * 查询杂项入库单行列表
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 杂项入库单行集合
     */
    public List<WmMiscRecptLine> selectWmMiscRecptLineList(WmMiscRecptLine wmMiscRecptLine);

    /**
     * 新增杂项入库单行
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 结果
     */
    public int insertWmMiscRecptLine(WmMiscRecptLine wmMiscRecptLine);

    /**
     * 修改杂项入库单行
     * 
     * @param wmMiscRecptLine 杂项入库单行
     * @return 结果
     */
    public int updateWmMiscRecptLine(WmMiscRecptLine wmMiscRecptLine);

    /**
     * 删除杂项入库单行
     * 
     * @param lineId 杂项入库单行主键
     * @return 结果
     */
    public int deleteWmMiscRecptLineByLineId(Long lineId);

    /**
     * 批量删除杂项入库单行
     * 
     * @param lineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmMiscRecptLineByLineIds(Long[] lineIds);
}
