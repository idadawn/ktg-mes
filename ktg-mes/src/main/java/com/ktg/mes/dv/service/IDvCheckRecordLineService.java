package com.ktg.mes.dv.service;

import java.util.List;
import com.ktg.mes.dv.domain.DvCheckRecordLine;

/**
 * 设备点检记录行Service接口
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public interface IDvCheckRecordLineService 
{
    /**
     * 查询设备点检记录行
     * 
     * @param lineId 设备点检记录行主键
     * @return 设备点检记录行
     */
    public DvCheckRecordLine selectDvCheckRecordLineByLineId(Long lineId);

    /**
     * 查询设备点检记录行列表
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 设备点检记录行集合
     */
    public List<DvCheckRecordLine> selectDvCheckRecordLineList(DvCheckRecordLine dvCheckRecordLine);

    /**
     * 新增设备点检记录行
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 结果
     */
    public int insertDvCheckRecordLine(DvCheckRecordLine dvCheckRecordLine);

    /**
     * 修改设备点检记录行
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 结果
     */
    public int updateDvCheckRecordLine(DvCheckRecordLine dvCheckRecordLine);

    /**
     * 批量删除设备点检记录行
     * 
     * @param lineIds 需要删除的设备点检记录行主键集合
     * @return 结果
     */
    public int deleteDvCheckRecordLineByLineIds(Long[] lineIds);

    /**
     * 删除设备点检记录行信息
     * 
     * @param lineId 设备点检记录行主键
     * @return 结果
     */
    public int deleteDvCheckRecordLineByLineId(Long lineId);

    /**
     * 根据记录ID删除设备点检记录行信息
     *
     * @param recordId 记录ID
     * @return 结果
     */
    public int deleteDvCheckRecordLineByRecordId(Long recordId);
}
