package com.ktg.mes.pro.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProAndonRecordMapper;
import com.ktg.mes.pro.domain.ProAndonRecord;
import com.ktg.mes.pro.service.IProAndonRecordService;

/**
 * 安灯呼叫记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
@Service
public class ProAndonRecordServiceImpl implements IProAndonRecordService 
{
    @Autowired
    private ProAndonRecordMapper proAndonRecordMapper;

    /**
     * 查询安灯呼叫记录
     * 
     * @param recordId 安灯呼叫记录主键
     * @return 安灯呼叫记录
     */
    @Override
    public ProAndonRecord selectProAndonRecordByRecordId(Long recordId)
    {
        return proAndonRecordMapper.selectProAndonRecordByRecordId(recordId);
    }

    /**
     * 查询安灯呼叫记录列表
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 安灯呼叫记录
     */
    @Override
    public List<ProAndonRecord> selectProAndonRecordList(ProAndonRecord proAndonRecord)
    {
        return proAndonRecordMapper.selectProAndonRecordList(proAndonRecord);
    }

    /**
     * 新增安灯呼叫记录
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 结果
     */
    @Override
    public int insertProAndonRecord(ProAndonRecord proAndonRecord)
    {
        proAndonRecord.setCreateTime(DateUtils.getNowDate());
        return proAndonRecordMapper.insertProAndonRecord(proAndonRecord);
    }

    /**
     * 修改安灯呼叫记录
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 结果
     */
    @Override
    public int updateProAndonRecord(ProAndonRecord proAndonRecord)
    {
        proAndonRecord.setUpdateTime(DateUtils.getNowDate());
        return proAndonRecordMapper.updateProAndonRecord(proAndonRecord);
    }

    /**
     * 批量删除安灯呼叫记录
     * 
     * @param recordIds 需要删除的安灯呼叫记录主键
     * @return 结果
     */
    @Override
    public int deleteProAndonRecordByRecordIds(Long[] recordIds)
    {
        return proAndonRecordMapper.deleteProAndonRecordByRecordIds(recordIds);
    }

    /**
     * 删除安灯呼叫记录信息
     * 
     * @param recordId 安灯呼叫记录主键
     * @return 结果
     */
    @Override
    public int deleteProAndonRecordByRecordId(Long recordId)
    {
        return proAndonRecordMapper.deleteProAndonRecordByRecordId(recordId);
    }
}
