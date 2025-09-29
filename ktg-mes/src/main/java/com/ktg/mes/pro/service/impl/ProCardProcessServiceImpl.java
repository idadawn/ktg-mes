package com.ktg.mes.pro.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProCardProcessMapper;
import com.ktg.mes.pro.domain.ProCardProcess;
import com.ktg.mes.pro.service.IProCardProcessService;

/**
 * 工序流转卡-工序信息Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
@Service
public class ProCardProcessServiceImpl implements IProCardProcessService 
{
    @Autowired
    private ProCardProcessMapper proCardProcessMapper;

    /**
     * 查询工序流转卡-工序信息
     * 
     * @param recordId 工序流转卡-工序信息主键
     * @return 工序流转卡-工序信息
     */
    @Override
    public ProCardProcess selectProCardProcessByRecordId(Long recordId)
    {
        return proCardProcessMapper.selectProCardProcessByRecordId(recordId);
    }

    /**
     * 查询工序流转卡-工序信息列表
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 工序流转卡-工序信息
     */
    @Override
    public List<ProCardProcess> selectProCardProcessList(ProCardProcess proCardProcess)
    {
        return proCardProcessMapper.selectProCardProcessList(proCardProcess);
    }

    /**
     * 新增工序流转卡-工序信息
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 结果
     */
    @Override
    public int insertProCardProcess(ProCardProcess proCardProcess)
    {
        proCardProcess.setCreateTime(DateUtils.getNowDate());
        return proCardProcessMapper.insertProCardProcess(proCardProcess);
    }

    /**
     * 修改工序流转卡-工序信息
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 结果
     */
    @Override
    public int updateProCardProcess(ProCardProcess proCardProcess)
    {
        proCardProcess.setUpdateTime(DateUtils.getNowDate());
        return proCardProcessMapper.updateProCardProcess(proCardProcess);
    }

    /**
     * 批量删除工序流转卡-工序信息
     * 
     * @param recordIds 需要删除的工序流转卡-工序信息主键
     * @return 结果
     */
    @Override
    public int deleteProCardProcessByRecordIds(Long[] recordIds)
    {
        return proCardProcessMapper.deleteProCardProcessByRecordIds(recordIds);
    }

    /**
     * 删除工序流转卡-工序信息信息
     * 
     * @param recordId 工序流转卡-工序信息主键
     * @return 结果
     */
    @Override
    public int deleteProCardProcessByRecordId(Long recordId)
    {
        return proCardProcessMapper.deleteProCardProcessByRecordId(recordId);
    }
}
