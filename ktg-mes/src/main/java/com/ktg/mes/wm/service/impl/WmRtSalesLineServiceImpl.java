package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmRtSalesLineMapper;
import com.ktg.mes.wm.domain.WmRtSalesLine;
import com.ktg.mes.wm.service.IWmRtSalesLineService;

/**
 * 产品销售退货行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-10-06
 */
@Service
public class WmRtSalesLineServiceImpl implements IWmRtSalesLineService
{
    @Autowired
    private WmRtSalesLineMapper wmRtSalesLineMapper;

    /**
     * 查询产品销售退货行
     * 
     * @param lineId 产品销售退货行主键
     * @return 产品销售退货行
     */
    @Override
    public WmRtSalesLine selectWmRtSalesLineByLineId(Long lineId)
    {
        return wmRtSalesLineMapper.selectWmRtSalesLineByLineId(lineId);
    }

    @Override
    public WmRtSalesLine selectWmRtSalesLineWithQuantityByLineId(Long lineId) {
        return wmRtSalesLineMapper.selectWmRtSalesLineWithQuantityByLineId(lineId);
    }

    /**
     * 查询产品销售退货行列表
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 产品销售退货行
     */
    @Override
    public List<WmRtSalesLine> selectWmRtSalesLineList(WmRtSalesLine wmRtSalesLine)
    {
        return wmRtSalesLineMapper.selectWmRtSalesLineList(wmRtSalesLine);
    }

    @Override
    public List<WmRtSalesLine> selectWmRtSalesLineWithQuantityList(WmRtSalesLine wmRtSalesLine) {
        return wmRtSalesLineMapper.selectWmRtSalesLineWithQuantityList(wmRtSalesLine);
    }

    @Override
    public List<WmRtSalesLine> selectWmRtSalesLineWithDetailList(WmRtSalesLine wmRtSalesLine) {
        return wmRtSalesLineMapper.selectWmRtSalesLineWithDetailList(wmRtSalesLine);
    }

    /**
     * 新增产品销售退货行
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 结果
     */
    @Override
    public int insertWmRtSalesLine(WmRtSalesLine wmRtSalesLine)
    {
        wmRtSalesLine.setCreateTime(DateUtils.getNowDate());
        return wmRtSalesLineMapper.insertWmRtSalesLine(wmRtSalesLine);
    }

    /**
     * 修改产品销售退货行
     * 
     * @param wmRtSalesLine 产品销售退货行
     * @return 结果
     */
    @Override
    public int updateWmRtSalesLine(WmRtSalesLine wmRtSalesLine)
    {
        wmRtSalesLine.setUpdateTime(DateUtils.getNowDate());
        return wmRtSalesLineMapper.updateWmRtSalesLine(wmRtSalesLine);
    }

    /**
     * 批量删除产品销售退货行
     * 
     * @param lineIds 需要删除的产品销售退货行主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesLineByLineIds(Long[] lineIds)
    {
        return wmRtSalesLineMapper.deleteWmRtSalesLineByLineIds(lineIds);
    }

    /**
     * 删除产品销售退货行信息
     * 
     * @param lineId 产品销售退货行主键
     * @return 结果
     */
    @Override
    public int deleteWmRtSalesLineByLineId(Long lineId)
    {
        return wmRtSalesLineMapper.deleteWmRtSalesLineByLineId(lineId);
    }

    @Override
    public int deleteByRtId(Long rtId) {
        return wmRtSalesLineMapper.deleteByRtId(rtId);
    }
}
