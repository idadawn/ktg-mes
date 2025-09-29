package com.ktg.mes.dv.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.dv.mapper.DvCheckRecordLineMapper;
import com.ktg.mes.dv.domain.DvCheckRecordLine;
import com.ktg.mes.dv.service.IDvCheckRecordLineService;

/**
 * 设备点检记录行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@Service
public class DvCheckRecordLineServiceImpl implements IDvCheckRecordLineService 
{
    @Autowired
    private DvCheckRecordLineMapper dvCheckRecordLineMapper;

    /**
     * 查询设备点检记录行
     * 
     * @param lineId 设备点检记录行主键
     * @return 设备点检记录行
     */
    @Override
    public DvCheckRecordLine selectDvCheckRecordLineByLineId(Long lineId)
    {
        return dvCheckRecordLineMapper.selectDvCheckRecordLineByLineId(lineId);
    }

    /**
     * 查询设备点检记录行列表
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 设备点检记录行
     */
    @Override
    public List<DvCheckRecordLine> selectDvCheckRecordLineList(DvCheckRecordLine dvCheckRecordLine)
    {
        return dvCheckRecordLineMapper.selectDvCheckRecordLineList(dvCheckRecordLine);
    }

    /**
     * 新增设备点检记录行
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 结果
     */
    @Override
    public int insertDvCheckRecordLine(DvCheckRecordLine dvCheckRecordLine)
    {
        dvCheckRecordLine.setCreateTime(DateUtils.getNowDate());
        return dvCheckRecordLineMapper.insertDvCheckRecordLine(dvCheckRecordLine);
    }

    /**
     * 修改设备点检记录行
     * 
     * @param dvCheckRecordLine 设备点检记录行
     * @return 结果
     */
    @Override
    public int updateDvCheckRecordLine(DvCheckRecordLine dvCheckRecordLine)
    {
        dvCheckRecordLine.setUpdateTime(DateUtils.getNowDate());
        return dvCheckRecordLineMapper.updateDvCheckRecordLine(dvCheckRecordLine);
    }

    /**
     * 批量删除设备点检记录行
     * 
     * @param lineIds 需要删除的设备点检记录行主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckRecordLineByLineIds(Long[] lineIds)
    {
        return dvCheckRecordLineMapper.deleteDvCheckRecordLineByLineIds(lineIds);
    }

    /**
     * 删除设备点检记录行信息
     * 
     * @param lineId 设备点检记录行主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckRecordLineByLineId(Long lineId)
    {
        return dvCheckRecordLineMapper.deleteDvCheckRecordLineByLineId(lineId);
    }

    @Override
    public int deleteDvCheckRecordLineByRecordId(Long recordId) {
        return dvCheckRecordLineMapper.deleteDvCheckRecordLineByRecordId(recordId);
    }
}
