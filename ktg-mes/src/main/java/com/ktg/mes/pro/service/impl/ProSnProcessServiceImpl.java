package com.ktg.mes.pro.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProSnProcessMapper;
import com.ktg.mes.pro.domain.ProSnProcess;
import com.ktg.mes.pro.service.IProSnProcessService;

/**
 * SN流转-工序信息Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-11-22
 */
@Service
public class ProSnProcessServiceImpl implements IProSnProcessService 
{
    @Autowired
    private ProSnProcessMapper proSnProcessMapper;

    /**
     * 查询SN流转-工序信息
     * 
     * @param recordId SN流转-工序信息主键
     * @return SN流转-工序信息
     */
    @Override
    public ProSnProcess selectProSnProcessByRecordId(Long recordId)
    {
        return proSnProcessMapper.selectProSnProcessByRecordId(recordId);
    }

    /**
     * 查询SN流转-工序信息列表
     * 
     * @param proSnProcess SN流转-工序信息
     * @return SN流转-工序信息
     */
    @Override
    public List<ProSnProcess> selectProSnProcessList(ProSnProcess proSnProcess)
    {
        return proSnProcessMapper.selectProSnProcessList(proSnProcess);
    }

    /**
     * 新增SN流转-工序信息
     * 
     * @param proSnProcess SN流转-工序信息
     * @return 结果
     */
    @Override
    public int insertProSnProcess(ProSnProcess proSnProcess)
    {
        proSnProcess.setCreateTime(DateUtils.getNowDate());
        return proSnProcessMapper.insertProSnProcess(proSnProcess);
    }

    /**
     * 修改SN流转-工序信息
     * 
     * @param proSnProcess SN流转-工序信息
     * @return 结果
     */
    @Override
    public int updateProSnProcess(ProSnProcess proSnProcess)
    {
        proSnProcess.setUpdateTime(DateUtils.getNowDate());
        return proSnProcessMapper.updateProSnProcess(proSnProcess);
    }

    /**
     * 批量删除SN流转-工序信息
     * 
     * @param recordIds 需要删除的SN流转-工序信息主键
     * @return 结果
     */
    @Override
    public int deleteProSnProcessByRecordIds(Long[] recordIds)
    {
        return proSnProcessMapper.deleteProSnProcessByRecordIds(recordIds);
    }

    /**
     * 删除SN流转-工序信息信息
     * 
     * @param recordId SN流转-工序信息主键
     * @return 结果
     */
    @Override
    public int deleteProSnProcessByRecordId(Long recordId)
    {
        return proSnProcessMapper.deleteProSnProcessByRecordId(recordId);
    }
}
