package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.tx.RtSalesTxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmRtSalesMapper;
import com.ktg.mes.wm.domain.WmRtSales;
import com.ktg.mes.wm.service.IWmRtSalesService;

/**
 * 产品销售退货单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-10-06
 */
@Service
public class WmRtSalesServiceImpl implements IWmRtSalesService
{
    @Autowired
    private WmRtSalesMapper wmRtSalesMapper;

    /**
     * 查询产品销售退货单
     * 
     * @param rtId 产品销售退货单主键
     * @return 产品销售退货单
     */
    @Override
    public WmRtSales selectWmRtSalesByRtId(Long rtId)
    {
        return wmRtSalesMapper.selectWmRtSalesByRtId(rtId);
    }

    /**
     * 查询产品销售退货单列表
     * 
     * @param wmRtSales 产品销售退货单
     * @return 产品销售退货单
     */
    @Override
    public List<WmRtSales> selectWmRtSalesList(WmRtSales wmRtSales)
    {
        return wmRtSalesMapper.selectWmRtSalesList(wmRtSales);
    }

    @Override
    public List<RtSalesTxBean> getTxBeans(Long rtId) {
        return wmRtSalesMapper.getTxBeans(rtId);
    }

    @Override
    public String checkUnique(WmRtSales wmRtSales) {
        WmRtSales sales = wmRtSalesMapper.checkUnique(wmRtSales);
        Long rtId = wmRtSales.getRtId() == null? -1L: wmRtSales.getRtId();
        if(StringUtils.isNotNull(sales) && rtId.longValue() != sales.getRtId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增产品销售退货单
     * 
     * @param wmRtSales 产品销售退货单
     * @return 结果
     */
    @Override
    public int insertWmRtSales(WmRtSales wmRtSales)
    {
        wmRtSales.setCreateTime(DateUtils.getNowDate());
        return wmRtSalesMapper.insertWmRtSales(wmRtSales);
    }

    /**
     * 修改产品销售退货单
     * 
     * @param wmRtSales 产品销售退货单
     * @return 结果
     */
    @Override
    public int updateWmRtSales(WmRtSales wmRtSales)
    {
        wmRtSales.setUpdateTime(DateUtils.getNowDate());
        return wmRtSalesMapper.updateWmRtSales(wmRtSales);
    }

    /**
     * 批量删除产品销售退货单
     * 
     * @param rtIds 需要删除的产品销售退货单主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesByRtIds(Long[] rtIds)
    {
        return wmRtSalesMapper.deleteWmRtSalesByRtIds(rtIds);
    }

    /**
     * 删除产品销售退货单信息
     * 
     * @param rtId 产品销售退货单主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesByRtId(Long rtId)
    {
        return wmRtSalesMapper.deleteWmRtSalesByRtId(rtId);
    }
}
