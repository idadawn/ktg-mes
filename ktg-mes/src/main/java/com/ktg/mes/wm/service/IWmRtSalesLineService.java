package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmRtSalesLine;

/**
 * 产品销售退货行Service接口
 * 
 * @author yinjinlu
 * @date 2022-10-06
 */
public interface IWmRtSalesLineService
{
    /**
     * 查询产品销售退货行
     * 
     * @param lineId 产品销售退货行主键
     * @return 产品销售退货行
     */
    public WmRtSalesLine selectWmRtSalesLineByLineId(Long lineId);


    /**
     * 查询产品销售退货行
     *
     * @param lineId 产品销售退货行主键
     * @return 产品销售退货行
     */
    public WmRtSalesLine selectWmRtSalesLineWithQuantityByLineId(Long lineId);


    /**
     * 查询产品销售退货行列表
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 产品销售退货行集合
     */
    public List<WmRtSalesLine> selectWmRtSalesLineList(WmRtSalesLine wmRtSalesLine);

    /**
     * 查询产品销售退货行列表
     *
     * @param wmRtSalesLine 产品销售退货行
     * @return 产品销售退货行集合
     */
    public List<WmRtSalesLine> selectWmRtSalesLineWithQuantityList(WmRtSalesLine wmRtSalesLine);

    /**
     * 查询产品销售退货行列表（含明细）
     *
     * @param wmRtSalesLine 产品销售退货行
     * @return 产品销售退货行集合
     */
    public List<WmRtSalesLine> selectWmRtSalesLineWithDetailList(WmRtSalesLine wmRtSalesLine);

    /**
     * 新增产品销售退货行
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 结果
     */
    public int insertWmRtSalesLine(WmRtSalesLine wmRtSalesLine);

    /**
     * 修改产品销售退货行
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 结果
     */
    public int updateWmRtSalesLine(WmRtSalesLine wmRtSalesLine);

    /**
     * 批量删除产品销售退货行
     * 
     * @param lineIds 需要删除的产品销售退货行主键集合
     * @return 结果
     */
    public int deleteWmRtSalesLineByLineIds(Long[] lineIds);

    /**
     * 删除产品销售退货行信息
     * 
     * @param lineId 产品销售退货行主键
     * @return 结果
     */
    public int deleteWmRtSalesLineByLineId(Long lineId);

    /**
     * 根据退货单ID删除所有行
     * @param rtId
     * @return
     */
    public int deleteByRtId(Long rtId);
}
