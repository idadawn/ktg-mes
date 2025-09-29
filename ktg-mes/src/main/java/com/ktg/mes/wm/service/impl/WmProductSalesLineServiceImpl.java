package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmProductSalesLineMapper;
import com.ktg.mes.wm.domain.WmProductSalesLine;
import com.ktg.mes.wm.service.IWmProductSalesLineService;

/**
 * 产品销售出库行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
@Service
public class WmProductSalesLineServiceImpl implements IWmProductSalesLineService
{
    @Autowired
    private WmProductSalesLineMapper wmProductSalesLineMapper;

    /**
     * 查询产品销售出库行
     * 
     * @param lineId 产品销售出库行主键
     * @return 产品销售出库行
     */
    @Override
    public WmProductSalesLine selectWmProductSalesLineByLineId(Long lineId)
    {
        return wmProductSalesLineMapper.selectWmProductSalesLineByLineId(lineId);
    }

    @Override
    public WmProductSalesLine selectWmProductSalesLineWithQuantityByLineId(Long lineId) {
        return wmProductSalesLineMapper.selectWmProductSalesLineWithQuantityByLineId(lineId);
    }

    /**
     * 查询产品销售出库行列表
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 产品销售出库行
     */
    @Override
    public List<WmProductSalesLine> selectWmProductSalesLineList(WmProductSalesLine wmProductSalesLine)
    {
        return wmProductSalesLineMapper.selectWmProductSalesLineList(wmProductSalesLine);
    }

    @Override
    public List<WmProductSalesLine> selectWmProductSalesLineWithQuantityList(WmProductSalesLine wmProductSalesLine) {
        return wmProductSalesLineMapper.selectWmProductSalesLineWithQuantityList(wmProductSalesLine);
    }

    @Override
    public List<WmProductSalesLine> selectWmProductSalesLineWithDetailList(WmProductSalesLine wmProductSalesLine) {
        return wmProductSalesLineMapper.selectWmProductSalesLineWithDetailList(wmProductSalesLine);
    }

    /**
     * 新增产品销售出库行
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 结果
     */
    @Override
    public int insertWmProductSalesLine(WmProductSalesLine wmProductSalesLine)
    {
        wmProductSalesLine.setCreateTime(DateUtils.getNowDate());
        return wmProductSalesLineMapper.insertWmProductSalesLine(wmProductSalesLine);
    }

    /**
     * 修改产品销售出库行
     * 
     * @param wmProductSalesLine 产品销售出库行
     * @return 结果
     */
    @Override
    public int updateWmProductSalesLine(WmProductSalesLine wmProductSalesLine)
    {
        wmProductSalesLine.setUpdateTime(DateUtils.getNowDate());
        return wmProductSalesLineMapper.updateWmProductSalesLine(wmProductSalesLine);
    }

    /**
     * 批量删除产品销售出库行
     * 
     * @param lineIds 需要删除的产品销售出库行主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesLineByLineIds(Long[] lineIds)
    {
        return wmProductSalesLineMapper.deleteWmProductSalesLineByLineIds(lineIds);
    }

    /**
     * 删除产品销售出库行信息
     * 
     * @param lineId 产品销售出库行主键
     * @return 结果
     */
    @Override
    public int deleteWmProductSalesLineByLineId(Long lineId)
    {
        return wmProductSalesLineMapper.deleteWmProductSalesLineByLineId(lineId);
    }

    @Override
    public int deleteBySalesId(Long salesId) {
        return wmProductSalesLineMapper.deleteBySalesId(salesId);
    }
}
