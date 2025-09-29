package com.ktg.mes.md.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.mes.md.domain.MdProductSip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.md.mapper.MdProductSopMapper;
import com.ktg.mes.md.domain.MdProductSop;
import com.ktg.mes.md.service.IMdProductSopService;

/**
 * 产品SOPService业务层处理
 * 
 * @author yinjinlu
 * @date 2022-07-26
 */
@Service
public class MdProductSopServiceImpl implements IMdProductSopService
{
    @Autowired
    private MdProductSopMapper mdProductSopMapper;

    /**
     * 查询产品SOP
     * 
     * @param sopId 产品SOP主键
     * @return 产品SOP
     */
    @Override
    public MdProductSop selectMdProductSopBySopId(Long sopId)
    {
        return mdProductSopMapper.selectMdProductSopBySopId(sopId);
    }

    /**
     * 查询产品SOP列表
     * 
     * @param mdProdutSop 产品SOP
     * @return 产品SOP
     */
    @Override
    public List<MdProductSop> selectMdProductSopList(MdProductSop mdProdutSop)
    {
        List<MdProductSop> mdProductSops = mdProductSopMapper.selectMdProductSopList(mdProdutSop);
        List<MdProductSop> collect = mdProductSops.stream().sorted(Comparator.comparing(MdProductSop::getOrderNum)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 新增产品SOP
     *
     * @param mdProdutSop 产品SOP
     * @return 结果
     */
    @Override
    public AjaxResult insertMdProductSop(MdProductSop mdProdutSop)
    {
        // 查询当前物料下所有的SOP
        MdProductSop parame = new MdProductSop();
        parame.setItemId(mdProdutSop.getItemId());
        List<MdProductSop> list = mdProductSopMapper.selectMdProductSopList(mdProdutSop);
        for (MdProductSop item : list) {
            if (item.getOrderNum() == mdProdutSop.getOrderNum()) {
                return AjaxResult.error("展示序号已存在");
            }
        }
        mdProdutSop.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success(mdProductSopMapper.insertMdProductSop(mdProdutSop));
    }

    /**
     * 修改产品SOP
     *
     * @param mdProdutSop 产品SOP
     * @return 结果
     */
    @Override
    public AjaxResult updateMdProductSop(MdProductSop mdProdutSop)
    {
        // 查询当前物料下所有的SOP
        MdProductSop parame = new MdProductSop();
        parame.setItemId(mdProdutSop.getItemId());
        List<MdProductSop> list = mdProductSopMapper.selectMdProductSopList(mdProdutSop);
        for (MdProductSop item : list) {
            if (item.getOrderNum() == mdProdutSop.getOrderNum()) {
                return AjaxResult.error("展示序号已存在");
            }
        }
        mdProdutSop.setUpdateTime(DateUtils.getNowDate());
        return AjaxResult.success(mdProductSopMapper.updateMdProductSop(mdProdutSop));
    }

    /**
     * 批量删除产品SOP
     * 
     * @param sopIds 需要删除的产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteMdProductSopBySopIds(Long[] sopIds)
    {
        return mdProductSopMapper.deleteMdProductSopBySopIds(sopIds);
    }

    /**
     * 删除产品SOP信息
     * 
     * @param sopId 产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteMdProductSopBySopId(Long sopId)
    {
        return mdProductSopMapper.deleteMdProductSopBySopId(sopId);
    }
}
