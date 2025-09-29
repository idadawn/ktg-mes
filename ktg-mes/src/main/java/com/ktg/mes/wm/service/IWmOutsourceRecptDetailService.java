package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmOutsourceRecptDetail;

/**
 * 外协入库单明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-04-12
 */
public interface IWmOutsourceRecptDetailService 
{
    /**
     * 查询外协入库单明细
     * 
     * @param detailId 外协入库单明细主键
     * @return 外协入库单明细
     */
    public WmOutsourceRecptDetail selectWmOutsourceRecptDetailByDetailId(Long detailId);

    /**
     * 查询外协入库单明细列表
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 外协入库单明细集合
     */
    public List<WmOutsourceRecptDetail> selectWmOutsourceRecptDetailList(WmOutsourceRecptDetail wmOutsourceRecptDetail);


    /**
     * 检查某一行的明细数量是不是超出行上的数量
     * G:超出
     * E:等于
     * L:小于
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增外协入库单明细
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 结果
     */
    public int insertWmOutsourceRecptDetail(WmOutsourceRecptDetail wmOutsourceRecptDetail);

    /**
     * 修改外协入库单明细
     * 
     * @param wmOutsourceRecptDetail 外协入库单明细
     * @return 结果
     */
    public int updateWmOutsourceRecptDetail(WmOutsourceRecptDetail wmOutsourceRecptDetail);

    /**
     * 批量删除外协入库单明细
     * 
     * @param detailIds 需要删除的外协入库单明细主键集合
     * @return 结果
     */
    public int deleteWmOutsourceRecptDetailByDetailIds(Long[] detailIds);

    /**
     * 删除外协入库单明细信息
     * 
     * @param detailId 外协入库单明细主键
     * @return 结果
     */
    public int deleteWmOutsourceRecptDetailByDetailId(Long detailId);
}
