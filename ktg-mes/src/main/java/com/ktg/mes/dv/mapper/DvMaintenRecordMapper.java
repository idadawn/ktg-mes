package com.ktg.mes.dv.mapper;

import java.util.List;
import com.ktg.mes.dv.domain.DvMaintenRecord;

/**
 * 设备保养记录Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public interface DvMaintenRecordMapper 
{
    /**
     * 查询设备保养记录
     * 
     * @param recordId 设备保养记录主键
     * @return 设备保养记录
     */
    public DvMaintenRecord selectDvMaintenRecordByRecordId(Long recordId);

    /**
     * 查询设备保养记录列表
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 设备保养记录集合
     */
    public List<DvMaintenRecord> selectDvMaintenRecordList(DvMaintenRecord dvMaintenRecord);

    /**
     * 新增设备保养记录
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 结果
     */
    public int insertDvMaintenRecord(DvMaintenRecord dvMaintenRecord);

    /**
     * 修改设备保养记录
     * 
     * @param dvMaintenRecord 设备保养记录
     * @return 结果
     */
    public int updateDvMaintenRecord(DvMaintenRecord dvMaintenRecord);

    /**
     * 删除设备保养记录
     * 
     * @param recordId 设备保养记录主键
     * @return 结果
     */
    public int deleteDvMaintenRecordByRecordId(Long recordId);

    /**
     * 批量删除设备保养记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDvMaintenRecordByRecordIds(Long[] recordIds);
}
