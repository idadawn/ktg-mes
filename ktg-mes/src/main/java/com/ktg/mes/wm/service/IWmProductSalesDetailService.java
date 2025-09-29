package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmProductSalesDetail;

/**
 * 产品销售出库记录明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-03-14
 */
public interface IWmProductSalesDetailService 
{
    /**
     * 查询产品销售出库记录明细
     * 
     * @param detailId 产品销售出库记录明细主键
     * @return 产品销售出库记录明细
     */
    public WmProductSalesDetail selectWmProductSalesDetailByDetailId(Long detailId);

    /**
     * 查询产品销售出库记录明细列表
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 产品销售出库记录明细集合
     */
    public List<WmProductSalesDetail> selectWmProductSalesDetailList(WmProductSalesDetail wmProductSalesDetail);


    /**
     * 检查销售出库单行与明细行数量是否一致
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增产品销售出库记录明细
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 结果
     */
    public int insertWmProductSalesDetail(WmProductSalesDetail wmProductSalesDetail);

    /**
     * 修改产品销售出库记录明细
     * 
     * @param wmProductSalesDetail 产品销售出库记录明细
     * @return 结果
     */
    public int updateWmProductSalesDetail(WmProductSalesDetail wmProductSalesDetail);

    /**
     * 批量删除产品销售出库记录明细
     * 
     * @param detailIds 需要删除的产品销售出库记录明细主键集合
     * @return 结果
     */
    public int deleteWmProductSalesDetailByDetailIds(Long[] detailIds);

    /**
     * 删除产品销售出库记录明细信息
     * 
     * @param detailId 产品销售出库记录明细主键
     * @return 结果
     */
    public int deleteWmProductSalesDetailByDetailId(Long detailId);
}
