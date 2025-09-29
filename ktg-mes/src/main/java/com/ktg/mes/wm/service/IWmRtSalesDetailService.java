package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmRtSalesDetail;

/**
 * 销售退货记录明细Service接口
 * 
 * @author yinjinlu
 * @date 2025-03-16
 */
public interface IWmRtSalesDetailService 
{
    /**
     * 查询销售退货记录明细
     * 
     * @param detailId 销售退货记录明细主键
     * @return 销售退货记录明细
     */
    public WmRtSalesDetail selectWmRtSalesDetailByDetailId(Long detailId);

    /**
     * 查询销售退货记录明细列表
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 销售退货记录明细集合
     */
    public List<WmRtSalesDetail> selectWmRtSalesDetailList(WmRtSalesDetail wmRtSalesDetail);


    /**
     * 检查退货行上的数量是否与明细行总数量一致
     * @param lineId
     * @return
     */
    public String checkQuantity(Long lineId);

    /**
     * 新增销售退货记录明细
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 结果
     */
    public int insertWmRtSalesDetail(WmRtSalesDetail wmRtSalesDetail);

    /**
     * 修改销售退货记录明细
     * 
     * @param wmRtSalesDetail 销售退货记录明细
     * @return 结果
     */
    public int updateWmRtSalesDetail(WmRtSalesDetail wmRtSalesDetail);

    /**
     * 批量删除销售退货记录明细
     * 
     * @param detailIds 需要删除的销售退货记录明细主键集合
     * @return 结果
     */
    public int deleteWmRtSalesDetailByDetailIds(Long[] detailIds);

    /**
     * 删除销售退货记录明细信息
     * 
     * @param detailId 销售退货记录明细主键
     * @return 结果
     */
    public int deleteWmRtSalesDetailByDetailId(Long detailId);
}
