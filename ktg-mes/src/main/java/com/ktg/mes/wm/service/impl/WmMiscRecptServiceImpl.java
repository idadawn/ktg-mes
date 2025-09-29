package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmItemRecpt;
import com.ktg.mes.wm.domain.tx.MiscRecptTxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMiscRecptMapper;
import com.ktg.mes.wm.domain.WmMiscRecpt;
import com.ktg.mes.wm.service.IWmMiscRecptService;

/**
 * 杂项入库单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@Service
public class WmMiscRecptServiceImpl implements IWmMiscRecptService 
{
    @Autowired
    private WmMiscRecptMapper wmMiscRecptMapper;

    /**
     * 查询杂项入库单
     * 
     * @param recptId 杂项入库单主键
     * @return 杂项入库单
     */
    @Override
    public WmMiscRecpt selectWmMiscRecptByRecptId(Long recptId)
    {
        return wmMiscRecptMapper.selectWmMiscRecptByRecptId(recptId);
    }

    /**
     * 查询杂项入库单列表
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 杂项入库单
     */
    @Override
    public List<WmMiscRecpt> selectWmMiscRecptList(WmMiscRecpt wmMiscRecpt)
    {
        return wmMiscRecptMapper.selectWmMiscRecptList(wmMiscRecpt);
    }

    @Override
    public String checkRecptCodeUnique(WmMiscRecpt wmMiscRecpt) {
        WmMiscRecpt miscRecpt = wmMiscRecptMapper.checkRecptCodeUnique(wmMiscRecpt);
        Long recptId = wmMiscRecpt.getRecptId()==null?-1L:wmMiscRecpt.getRecptId();
        if(StringUtils.isNotNull(miscRecpt) && miscRecpt.getRecptId().longValue() != recptId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<MiscRecptTxBean> getTxBeans(Long recptId) {
        return wmMiscRecptMapper.getTxBeans(recptId);
    }

    /**
     * 新增杂项入库单
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 结果
     */
    @Override
    public int insertWmMiscRecpt(WmMiscRecpt wmMiscRecpt)
    {
        wmMiscRecpt.setCreateTime(DateUtils.getNowDate());
        return wmMiscRecptMapper.insertWmMiscRecpt(wmMiscRecpt);
    }

    /**
     * 修改杂项入库单
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 结果
     */
    @Override
    public int updateWmMiscRecpt(WmMiscRecpt wmMiscRecpt)
    {
        wmMiscRecpt.setUpdateTime(DateUtils.getNowDate());
        return wmMiscRecptMapper.updateWmMiscRecpt(wmMiscRecpt);
    }

    /**
     * 批量删除杂项入库单
     * 
     * @param recptIds 需要删除的杂项入库单主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptByRecptIds(Long[] recptIds)
    {
        return wmMiscRecptMapper.deleteWmMiscRecptByRecptIds(recptIds);
    }

    /**
     * 删除杂项入库单信息
     * 
     * @param recptId 杂项入库单主键
     * @return 结果
     */
    @Override
    public int deleteWmMiscRecptByRecptId(Long recptId)
    {
        return wmMiscRecptMapper.deleteWmMiscRecptByRecptId(recptId);
    }
}
