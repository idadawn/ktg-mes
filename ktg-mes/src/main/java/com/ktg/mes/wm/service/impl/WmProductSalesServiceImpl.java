package com.ktg.mes.wm.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmProductSalesLine;
import com.ktg.mes.wm.domain.tx.ProductSalesTxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmProductSalesMapper;
import com.ktg.mes.wm.domain.WmProductSales;
import com.ktg.mes.wm.service.IWmProductSalesService;

/**
 * 销售出库单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-10-04
 */
@Service
public class WmProductSalesServiceImpl implements IWmProductSalesService
{
    @Autowired
    private WmProductSalesMapper wmProductSalesMapper;

    /**
     * 查询销售出库单
     * 
     * @param salesId 销售出库单主键
     * @return 销售出库单
     */
    @Override
    public WmProductSales selectWmProductSalesBySalesId(Long salesId)
    {
        return wmProductSalesMapper.selectWmProductSalesBySalesId(salesId);
    }

    /**
     * 查询销售出库单列表
     * 
     * @param wmProductSales 销售出库单
     * @return 销售出库单
     */
    @Override
    public List<WmProductSales> selectWmProductSalesList(WmProductSales wmProductSales)
    {
        return wmProductSalesMapper.selectWmProductSalesList(wmProductSales);
    }

    @Override
    public List<ProductSalesTxBean> getTxBeans(Long salesId) {
        return wmProductSalesMapper.getTxBeans(salesId);
    }

    @Override
    public String checkUnique(WmProductSales wmProductSales) {
        WmProductSales sales = wmProductSalesMapper.checkUnique(wmProductSales);
        Long salesId = wmProductSales.getSalesId() ==null? -1L:wmProductSales.getSalesId();
        if(StringUtils.isNotNull(sales) && salesId.longValue() != sales.getSalesId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增销售出库单
     * 
     * @param wmProductSales 销售出库单
     * @return 结果
     */
    @Override
    public int insertWmProductSales(WmProductSales wmProductSales)
    {
        wmProductSales.setCreateTime(DateUtils.getNowDate());
        return wmProductSalesMapper.insertWmProductSales(wmProductSales);
    }

    /**
     * 修改销售出库单
     * 
     * @param wmProductSales 销售出库单
     * @return 结果
     */
    @Override
    public int updateWmProductSales(WmProductSales wmProductSales)
    {
        wmProductSales.setUpdateTime(DateUtils.getNowDate());
        return wmProductSalesMapper.updateWmProductSales(wmProductSales);
    }

    /**
     * 批量删除销售出库单
     * 
     * @param salesIds 需要删除的销售出库单主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesBySalesIds(Long[] salesIds)
    {
        return wmProductSalesMapper.deleteWmProductSalesBySalesIds(salesIds);
    }

    /**
     * 删除销售出库单信息
     * 
     * @param salesId 销售出库单主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesBySalesId(Long salesId)
    {
        return wmProductSalesMapper.deleteWmProductSalesBySalesId(salesId);
    }

    /**
     * 根据客户 id 获取产品清单数据
     * @param clientId
     * @return
     */
    @Override
    public AjaxResult getItem(Long clientId) {
        List<WmProductSalesLine> salesLines =  wmProductSalesMapper.getItem(clientId);
        List<WmProductSalesLine> collect = salesLines.stream()
                .collect(Collectors.toMap(
                        WmProductSalesLine::getItemId,
                        Function.identity(),
                        (existing, replacement) -> existing
                )).values().stream().collect(Collectors.toList());
        return AjaxResult.success(collect);
    }

    /**
     * 根据客户 id 获取销售记录数据
     * @param clientId
     * @return
     */
    @Override
    public AjaxResult getSaleRecord(Long clientId) {
        List<WmProductSales> saleRecordVOS = wmProductSalesMapper.getSaleRecord(clientId);
        return AjaxResult.success(saleRecordVOS);
    }
}
