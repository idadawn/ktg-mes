package com.ktg.mes.pro.service;

import java.util.List;
import com.ktg.mes.pro.domain.ProAndonRecord;

/**
 * 安灯呼叫记录Service接口
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
public interface IProAndonRecordService 
{
    /**
     * 查询安灯呼叫记录
     * 
     * @param recordId 安灯呼叫记录主键
     * @return 安灯呼叫记录
     */
    public ProAndonRecord selectProAndonRecordByRecordId(Long recordId);

    /**
     * 查询安灯呼叫记录列表
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 安灯呼叫记录集合
     */
    public List<ProAndonRecord> selectProAndonRecordList(ProAndonRecord proAndonRecord);

    /**
     * 新增安灯呼叫记录
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 结果
     */
    public int insertProAndonRecord(ProAndonRecord proAndonRecord);

    /**
     * 修改安灯呼叫记录
     * 
     * @param proAndonRecord 安灯呼叫记录
     * @return 结果
     */
    public int updateProAndonRecord(ProAndonRecord proAndonRecord);

    /**
     * 批量删除安灯呼叫记录
     * 
     * @param recordIds 需要删除的安灯呼叫记录主键集合
     * @return 结果
     */
    public int deleteProAndonRecordByRecordIds(Long[] recordIds);

    /**
     * 删除安灯呼叫记录信息
     * 
     * @param recordId 安灯呼叫记录主键
     * @return 结果
     */
    public int deleteProAndonRecordByRecordId(Long recordId);
}
