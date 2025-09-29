package com.ktg.mes.pro.mapper;

import java.util.List;
import com.ktg.mes.pro.domain.ProSnProcess;

/**
 * SN流转-工序信息Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-11-22
 */
public interface ProSnProcessMapper 
{
    /**
     * 查询SN流转-工序信息
     * 
     * @param recordId SN流转-工序信息主键
     * @return SN流转-工序信息
     */
    public ProSnProcess selectProSnProcessByRecordId(Long recordId);

    /**
     * 查询SN流转-工序信息列表
     * 
     * @param proSnProcess SN流转-工序信息
     * @return SN流转-工序信息集合
     */
    public List<ProSnProcess> selectProSnProcessList(ProSnProcess proSnProcess);


    /**
     * 新增SN流转-工序信息
     * 
     * @param proSnProcess SN流转-工序信息
     * @return 结果
     */
    public int insertProSnProcess(ProSnProcess proSnProcess);

    /**
     * 修改SN流转-工序信息
     * 
     * @param proSnProcess SN流转-工序信息
     * @return 结果
     */
    public int updateProSnProcess(ProSnProcess proSnProcess);

    /**
     * 删除SN流转-工序信息
     * 
     * @param recordId SN流转-工序信息主键
     * @return 结果
     */
    public int deleteProSnProcessByRecordId(Long recordId);

    /**
     * 批量删除SN流转-工序信息
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProSnProcessByRecordIds(Long[] recordIds);
}
