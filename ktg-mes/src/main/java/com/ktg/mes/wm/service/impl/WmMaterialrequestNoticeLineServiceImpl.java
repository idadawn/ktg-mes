package com.ktg.mes.wm.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmMaterialrequestNoticeLineMapper;
import com.ktg.mes.wm.domain.WmMaterialrequestNoticeLine;
import com.ktg.mes.wm.service.IWmMaterialrequestNoticeLineService;

/**
 * 备料通知单明细Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
@Service
public class WmMaterialrequestNoticeLineServiceImpl implements IWmMaterialrequestNoticeLineService 
{
    @Autowired
    private WmMaterialrequestNoticeLineMapper wmMaterialrequestNoticeLineMapper;

    /**
     * 查询备料通知单明细
     * 
     * @param lineId 备料通知单明细主键
     * @return 备料通知单明细
     */
    @Override
    public WmMaterialrequestNoticeLine selectWmMaterialrequestNoticeLineByLineId(Long lineId)
    {
        return wmMaterialrequestNoticeLineMapper.selectWmMaterialrequestNoticeLineByLineId(lineId);
    }

    /**
     * 查询备料通知单明细列表
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 备料通知单明细
     */
    @Override
    public List<WmMaterialrequestNoticeLine> selectWmMaterialrequestNoticeLineList(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        return wmMaterialrequestNoticeLineMapper.selectWmMaterialrequestNoticeLineList(wmMaterialrequestNoticeLine);
    }

    /**
     * 新增备料通知单明细
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 结果
     */
    @Override
    public int insertWmMaterialrequestNoticeLine(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        wmMaterialrequestNoticeLine.setCreateTime(DateUtils.getNowDate());
        return wmMaterialrequestNoticeLineMapper.insertWmMaterialrequestNoticeLine(wmMaterialrequestNoticeLine);
    }

    /**
     * 修改备料通知单明细
     * 
     * @param wmMaterialrequestNoticeLine 备料通知单明细
     * @return 结果
     */
    @Override
    public int updateWmMaterialrequestNoticeLine(WmMaterialrequestNoticeLine wmMaterialrequestNoticeLine)
    {
        wmMaterialrequestNoticeLine.setUpdateTime(DateUtils.getNowDate());
        return wmMaterialrequestNoticeLineMapper.updateWmMaterialrequestNoticeLine(wmMaterialrequestNoticeLine);
    }

    /**
     * 批量删除备料通知单明细
     * 
     * @param lineIds 需要删除的备料通知单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialrequestNoticeLineByLineIds(Long[] lineIds)
    {
        return wmMaterialrequestNoticeLineMapper.deleteWmMaterialrequestNoticeLineByLineIds(lineIds);
    }

    /**
     * 删除备料通知单明细信息
     * 
     * @param lineId 备料通知单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialrequestNoticeLineByLineId(Long lineId)
    {
        return wmMaterialrequestNoticeLineMapper.deleteWmMaterialrequestNoticeLineByLineId(lineId);
    }
}
