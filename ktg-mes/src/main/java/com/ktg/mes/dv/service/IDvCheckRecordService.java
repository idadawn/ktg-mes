package com.ktg.mes.dv.service;

import java.util.List;
import com.ktg.mes.dv.domain.DvCheckRecord;

/**
 * 设备点检记录Service接口
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public interface IDvCheckRecordService 
{
    /**
     * 查询设备点检记录
     * 
     * @param recordId 设备点检记录主键
     * @return 设备点检记录
     */
    public DvCheckRecord selectDvCheckRecordByRecordId(Long recordId);

    /**
     * 查询设备点检记录列表
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 设备点检记录集合
     */
    public List<DvCheckRecord> selectDvCheckRecordList(DvCheckRecord dvCheckRecord);

    /**
     * 新增设备点检记录
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 结果
     */
    public int insertDvCheckRecord(DvCheckRecord dvCheckRecord);

    /**
     * 修改设备点检记录
     * 
     * @param dvCheckRecord 设备点检记录
     * @return 结果
     */
    public int updateDvCheckRecord(DvCheckRecord dvCheckRecord);

    /**
     * 批量删除设备点检记录
     * 
     * @param recordIds 需要删除的设备点检记录主键集合
     * @return 结果
     */
    public int deleteDvCheckRecordByRecordIds(Long[] recordIds);

    /**
     * 删除设备点检记录信息
     * 
     * @param recordId 设备点检记录主键
     * @return 结果
     */
    public int deleteDvCheckRecordByRecordId(Long recordId);
}
