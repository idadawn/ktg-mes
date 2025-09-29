package com.ktg.mes.dv.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.dv.mapper.DvCheckRecordMapper;
import com.ktg.mes.dv.domain.DvCheckRecord;
import com.ktg.mes.dv.service.IDvCheckRecordService;

/**
 * 设备点检记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@Service
public class DvCheckRecordServiceImpl implements IDvCheckRecordService 
{
    @Autowired
    private DvCheckRecordMapper dvCheckRecordMapper;

    /**
     * 查询设备点检记录
     * 
     * @param recordId 设备点检记录主键
     * @return 设备点检记录
     */
    @Override
    public DvCheckRecord selectDvCheckRecordByRecordId(Long recordId)
    {
        return dvCheckRecordMapper.selectDvCheckRecordByRecordId(recordId);
    }

    /**
     * 查询设备点检记录列表
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 设备点检记录
     */
    @Override
    public List<DvCheckRecord> selectDvCheckRecordList(DvCheckRecord dvCheckRecord)
    {
        return dvCheckRecordMapper.selectDvCheckRecordList(dvCheckRecord);
    }

    /**
     * 新增设备点检记录
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 结果
     */
    @Override
    public int insertDvCheckRecord(DvCheckRecord dvCheckRecord)
    {
        dvCheckRecord.setCreateTime(DateUtils.getNowDate());
        return dvCheckRecordMapper.insertDvCheckRecord(dvCheckRecord);
    }

    /**
     * 修改设备点检记录
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 结果
     */
    @Override
    public int updateDvCheckRecord(DvCheckRecord dvCheckRecord)
    {
        dvCheckRecord.setUpdateTime(DateUtils.getNowDate());
        return dvCheckRecordMapper.updateDvCheckRecord(dvCheckRecord);
    }

    /**
     * 批量删除设备点检记录
     * 
     * @param recordIds 需要删除的设备点检记录主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckRecordByRecordIds(Long[] recordIds)
    {
        return dvCheckRecordMapper.deleteDvCheckRecordByRecordIds(recordIds);
    }

    /**
     * 删除设备点检记录信息
     * 
     * @param recordId 设备点检记录主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckRecordByRecordId(Long recordId)
    {
        return dvCheckRecordMapper.deleteDvCheckRecordByRecordId(recordId);
    }
}
