package com.ktg.mes.dv.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.dv.mapper.DvMaintenRecordLineMapper;
import com.ktg.mes.dv.domain.DvMaintenRecordLine;
import com.ktg.mes.dv.service.IDvMaintenRecordLineService;

/**
 * 设备保养记录行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@Service
public class DvMaintenRecordLineServiceImpl implements IDvMaintenRecordLineService 
{
    @Autowired
    private DvMaintenRecordLineMapper dvMaintenRecordLineMapper;

    /**
     * 查询设备保养记录行
     * 
     * @param lineId 设备保养记录行主键
     * @return 设备保养记录行
     */
    @Override
    public DvMaintenRecordLine selectDvMaintenRecordLineByLineId(Long lineId)
    {
        return dvMaintenRecordLineMapper.selectDvMaintenRecordLineByLineId(lineId);
    }

    /**
     * 查询设备保养记录行列表
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 设备保养记录行
     */
    @Override
    public List<DvMaintenRecordLine> selectDvMaintenRecordLineList(DvMaintenRecordLine dvMaintenRecordLine)
    {
        return dvMaintenRecordLineMapper.selectDvMaintenRecordLineList(dvMaintenRecordLine);
    }

    /**
     * 新增设备保养记录行
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 结果
     */
    @Override
    public int insertDvMaintenRecordLine(DvMaintenRecordLine dvMaintenRecordLine)
    {
        dvMaintenRecordLine.setCreateTime(DateUtils.getNowDate());
        return dvMaintenRecordLineMapper.insertDvMaintenRecordLine(dvMaintenRecordLine);
    }

    /**
     * 修改设备保养记录行
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 结果
     */
    @Override
    public int updateDvMaintenRecordLine(DvMaintenRecordLine dvMaintenRecordLine)
    {
        dvMaintenRecordLine.setUpdateTime(DateUtils.getNowDate());
        return dvMaintenRecordLineMapper.updateDvMaintenRecordLine(dvMaintenRecordLine);
    }

    /**
     * 批量删除设备保养记录行
     * 
     * @param lineIds 需要删除的设备保养记录行主键
     * @return 结果
     */
    @Override
    public int deleteDvMaintenRecordLineByLineIds(Long[] lineIds)
    {
        return dvMaintenRecordLineMapper.deleteDvMaintenRecordLineByLineIds(lineIds);
    }

    /**
     * 删除设备保养记录行信息
     * 
     * @param lineId 设备保养记录行主键
     * @return 结果
     */
    @Override
    public int deleteDvMaintenRecordLineByLineId(Long lineId)
    {
        return dvMaintenRecordLineMapper.deleteDvMaintenRecordLineByLineId(lineId);
    }
}
