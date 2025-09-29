package com.ktg.mes.pro.mapper;

import java.util.List;
import com.ktg.mes.pro.domain.ProCardProcess;

/**
 * 工序流转卡-工序信息Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
public interface ProCardProcessMapper 
{
    /**
     * 查询工序流转卡-工序信息
     * 
     * @param recordId 工序流转卡-工序信息主键
     * @return 工序流转卡-工序信息
     */
    public ProCardProcess selectProCardProcessByRecordId(Long recordId);

    /**
     * 查询工序流转卡-工序信息列表
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 工序流转卡-工序信息集合
     */
    public List<ProCardProcess> selectProCardProcessList(ProCardProcess proCardProcess);

    /**
     * 新增工序流转卡-工序信息
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 结果
     */
    public int insertProCardProcess(ProCardProcess proCardProcess);

    /**
     * 修改工序流转卡-工序信息
     * 
     * @param proCardProcess 工序流转卡-工序信息
     * @return 结果
     */
    public int updateProCardProcess(ProCardProcess proCardProcess);

    /**
     * 删除工序流转卡-工序信息
     * 
     * @param recordId 工序流转卡-工序信息主键
     * @return 结果
     */
    public int deleteProCardProcessByRecordId(Long recordId);

    /**
     * 批量删除工序流转卡-工序信息
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProCardProcessByRecordIds(Long[] recordIds);
}
