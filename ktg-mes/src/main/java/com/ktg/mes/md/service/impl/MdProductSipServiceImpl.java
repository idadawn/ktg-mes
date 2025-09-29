package com.ktg.mes.md.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.md.mapper.MdProductSipMapper;
import com.ktg.mes.md.domain.MdProductSip;
import com.ktg.mes.md.service.IMdProductSipService;

/**
 * 产品SIPService业务层处理
 * 
 * @author yinjinlu
 * @date 2023-10-31
 */
@Service
public class MdProductSipServiceImpl implements IMdProductSipService 
{
    @Autowired
    private MdProductSipMapper mdProductSipMapper;

    /**
     * 查询产品SIP
     * 
     * @param sipId 产品SIP主键
     * @return 产品SIP
     */
    @Override
    public MdProductSip selectMdProductSipBySipId(Long sipId)
    {
        return mdProductSipMapper.selectMdProductSipBySipId(sipId);
    }

    /**
     * 查询产品SIP列表
     * 
     * @param mdProductSip 产品SIP
     * @return 产品SIP
     */
    @Override
    public List<MdProductSip> selectMdProductSipList(MdProductSip mdProductSip)
    {
        List<MdProductSip> mdProductSips = mdProductSipMapper.selectMdProductSipList(mdProductSip);
        List<MdProductSip> collect = mdProductSips.stream().sorted(Comparator.comparing(MdProductSip::getOrderNum)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 新增产品SIP
     *
     * @param mdProductSip 产品SIP
     * @return 结果
     */
    @Override
    public AjaxResult insertMdProductSip(MdProductSip mdProductSip)
    {
        // 查询当前物料下所有的SIP
        MdProductSip parame = new MdProductSip();
        parame.setItemId(mdProductSip.getItemId());
        List<MdProductSip> list = mdProductSipMapper.selectMdProductSipList(parame);
        for (MdProductSip item : list) {
            if (item.getOrderNum() == mdProductSip.getOrderNum()) {
                return AjaxResult.error("展示序号已存在");
            }
        }
        mdProductSip.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success(mdProductSipMapper.insertMdProductSip(mdProductSip));
    }

    /**
     * 修改产品SIP
     *
     * @param mdProductSip 产品SIP
     * @return 结果
     */
    @Override
    public AjaxResult updateMdProductSip(MdProductSip mdProductSip)
    {
        // 查询当前物料下所有的SIP
        MdProductSip parame = new MdProductSip();
        parame.setItemId(mdProductSip.getItemId());
        List<MdProductSip> list = mdProductSipMapper.selectMdProductSipList(parame);
        for (MdProductSip item : list) {
            if (item.getOrderNum() == mdProductSip.getOrderNum()) {
                return AjaxResult.error("展示序号已存在");
            }
        }
        mdProductSip.setUpdateTime(DateUtils.getNowDate());
        return AjaxResult.success(mdProductSipMapper.updateMdProductSip(mdProductSip));
    }

    /**
     * 批量删除产品SIP
     * 
     * @param sipIds 需要删除的产品SIP主键
     * @return 结果
     */
    @Override
    public int deleteMdProductSipBySipIds(Long[] sipIds)
    {
        return mdProductSipMapper.deleteMdProductSipBySipIds(sipIds);
    }

    /**
     * 删除产品SIP信息
     * 
     * @param sipId 产品SIP主键
     * @return 结果
     */
    @Override
    public int deleteMdProductSipBySipId(Long sipId)
    {
        return mdProductSipMapper.deleteMdProductSipBySipId(sipId);
    }
}
