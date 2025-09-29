package com.ktg.mes.dv.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.dv.mapper.DvMaintenRecordMapper;
import com.ktg.mes.dv.domain.DvMaintenRecord;
import com.ktg.mes.dv.service.IDvMaintenRecordService;

/**
 * 设备保养记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@Service
public class DvMaintenRecordServiceImpl implements IDvMaintenRecordService 
{
    @Autowired
    private DvMaintenRecordMapper dvMaintenRecordMapper;

    /**
     * 查询设备保养记录
     * 
     * @param recordId 设备保养记录主键
     * @return 设备保养记录
     */
    @Override
    public DvMaintenRecord selectDvMaintenRecordByRecordId(Long recordId)
    {
        return dvMaintenRecordMapper.selectDvMaintenRecordByRecordId(recordId);
    }

    /**
     * 查询设备保养记录列表
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 设备保养记录
     */
    @Override
    public List<DvMaintenRecord> selectDvMaintenRecordList(DvMaintenRecord dvMaintenRecord)
    {
        return dvMaintenRecordMapper.selectDvMaintenRecordList(dvMaintenRecord);
    }

    /**
     * 新增设备保养记录
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 结果
     */
    @Override
    public int insertDvMaintenRecord(DvMaintenRecord dvMaintenRecord)
    {
        dvMaintenRecord.setCreateTime(DateUtils.getNowDate());
        return dvMaintenRecordMapper.insertDvMaintenRecord(dvMaintenRecord);
    }

    /**
     * 修改设备保养记录
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 结果
     */
    @Override
    public int updateDvMaintenRecord(DvMaintenRecord dvMaintenRecord)
    {
        dvMaintenRecord.setUpdateTime(DateUtils.getNowDate());
        return dvMaintenRecordMapper.updateDvMaintenRecord(dvMaintenRecord);
    }

    /**
     * 批量删除设备保养记录
     * 
     * @param recordIds 需要删除的设备保养记录主键
     * @return 结果
     */
    @Override
    public int deleteDvMaintenRecordByRecordIds(Long[] recordIds)
    {
        return dvMaintenRecordMapper.deleteDvMaintenRecordByRecordIds(recordIds);
    }

    /**
     * 删除设备保养记录信息
     * 
     * @param recordId 设备保养记录主键
     * @return 结果
     */
    @Override
    public int deleteDvMaintenRecordByRecordId(Long recordId)
    {
        return dvMaintenRecordMapper.deleteDvMaintenRecordByRecordId(recordId);
    }
}
