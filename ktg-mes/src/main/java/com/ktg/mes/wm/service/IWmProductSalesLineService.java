package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmProductSalesLine;
import com.ktg.mes.wm.domain.WmRtSalesLine;

/**
 * 产品销售出库行Service接口
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
public interface IWmProductSalesLineService
{
    /**
     * 查询产品销售出库行
     * 
     * @param lineId 产品销售出库行主键
     * @return 产品销售出库行
     */
    public WmProductSalesLine selectWmProductSalesLineByLineId(Long lineId);

    /**
     * 查询产品销售出库行（带拣货数量）
     *
     * @param lineId 产品销售出库行主键
     * @return 产品销售出库行
     */
    public WmProductSalesLine selectWmProductSalesLineWithQuantityByLineId(Long lineId);

    /**
     * 查询产品销售出库行列表
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 产品销售出库行集合
     */
    public List<WmProductSalesLine> selectWmProductSalesLineList(WmProductSalesLine wmProductSalesLine);

    /**
     * 查询产品销售出库行列表（含拣货数量）
     *
     * @param wmProductSalesLine 产品销售出库行
     * @return 产品销售出库行集合
     */
    public List<WmProductSalesLine> selectWmProductSalesLineWithQuantityList(WmProductSalesLine wmProductSalesLine);


    /**
     * 查询产品销售出库行列表
     *
     * @param wmProductSalesLine 产品销售出库行（包含明细）
     * @return 产品销售出库行集合
     */
    public List<WmProductSalesLine> selectWmProductSalesLineWithDetailList(WmProductSalesLine wmProductSalesLine);

    /**
     * 新增产品销售出库行
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 结果
     */
    public int insertWmProductSalesLine(WmProductSalesLine wmProductSalesLine);

    /**
     * 修改产品销售出库行
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 结果
     */
    public int updateWmProductSalesLine(WmProductSalesLine wmProductSalesLine);

    /**
     * 批量删除产品销售出库行
     * 
     * @param lineIds 需要删除的产品销售出库行主键集合
     * @return 结果
     */
    public int deleteWmProductSalesLineByLineIds(Long[] lineIds);

    /**
     * 删除产品销售出库行信息
     * 
     * @param lineId 产品销售出库行主键
     * @return 结果
     */
    public int deleteWmProductSalesLineByLineId(Long lineId);

    /**
     * 根据出库单头删除所有行
     * @param salesId
     * @return
     */
    public int deleteBySalesId(Long salesId);
}
