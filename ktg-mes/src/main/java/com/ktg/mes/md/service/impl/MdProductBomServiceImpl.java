package com.ktg.mes.md.service.impl;

import java.util.List;

import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.MapCircleUtils;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.service.IMdItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.md.mapper.MdProductBomMapper;
import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.service.IMdProductBomService;

/**
 * 产品BOM关系Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-09
 */
@Service
public class MdProductBomServiceImpl implements IMdProductBomService 
{
    @Autowired
    private MdProductBomMapper mdProductBomMapper;

    @Autowired
    private IMdItemService itemService;

    /**
     * 查询产品BOM关系
     * 
     * @param bomId 产品BOM关系主键
     * @return 产品BOM关系
     */
    @Override
    public MdProductBom selectMdProductBomByBomId(Long bomId)
    {
        return mdProductBomMapper.selectMdProductBomByBomId(bomId);
    }

    /**
     * 查询产品BOM关系列表
     * 
     * @param mdProductBom 产品BOM关系
     * @return 产品BOM关系
     */
    @Override
    public List<MdProductBom> selectMdProductBomList(MdProductBom mdProductBom)
    {
        return mdProductBomMapper.selectMdProductBomList(mdProductBom);
    }

    /**
     * 新增产品BOM关系
     *
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    @Override
    public AjaxResult insertMdProductBom(MdProductBom mdProductBom)
    {
        // 首先构建图数据
        MapCircleUtils mapCircleUtils = MapCircleUtils.createInstance();
        List<MdProductBom> mdProductBoms = selectBomAll();
        // 将相关数据构建成图结构
        mdProductBoms.forEach(item -> {
            mapCircleUtils.addEdge(item.getItemId(), item.getBomItemId());
        });
        mapCircleUtils.addEdge(mdProductBom.getItemId(), mdProductBom.getBomItemId());
        // 查询图结构是否存在闭环
        if (mapCircleUtils.hasCycle()) {
            // 存在闭环，无法新增
            return AjaxResult.error("BOM物料存在闭环，无法新增");
        }

        // 不存在闭环，可以新增
        mdProductBom.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success(mdProductBomMapper.insertMdProductBom(mdProductBom));
    }

    /**
     * 修改产品BOM关系
     * 
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    @Override
    public int updateMdProductBom(MdProductBom mdProductBom)
    {
        mdProductBom.setUpdateTime(DateUtils.getNowDate());
        return mdProductBomMapper.updateMdProductBom(mdProductBom);
    }

    /**
     * 批量删除产品BOM关系
     * 
     * @param bomIds 需要删除的产品BOM关系主键
     * @return 结果
     */
    @Override
    public int deleteMdProductBomByBomIds(Long[] bomIds)
    {
        return mdProductBomMapper.deleteMdProductBomByBomIds(bomIds);
    }

    /**
     * 删除产品BOM关系信息
     * 
     * @param bomId 产品BOM关系主键
     * @return 结果
     */
    @Override
    public int deleteMdProductBomByBomId(Long bomId)
    {
        return mdProductBomMapper.deleteMdProductBomByBomId(bomId);
    }

    public List<MdProductBom> selectBomAll() {
        return mdProductBomMapper.selectAll();
    }
}
