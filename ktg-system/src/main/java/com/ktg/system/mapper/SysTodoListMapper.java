package com.ktg.system.mapper;

import java.util.List;
import com.ktg.system.domain.SysTodoList;

/**
 * 通用代办Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
public interface SysTodoListMapper 
{
    /**
     * 查询通用代办
     * 
     * @param taskId 通用代办主键
     * @return 通用代办
     */
    public SysTodoList selectSysTodoListByTaskId(Long taskId);

    /**
     * 查询通用代办列表
     * 
     * @param sysTodoList 通用代办
     * @return 通用代办集合
     */
    public List<SysTodoList> selectSysTodoListList(SysTodoList sysTodoList);

    /**
     * 新增通用代办
     * 
     * @param sysTodoList 通用代办
     * @return 结果
     */
    public int insertSysTodoList(SysTodoList sysTodoList);

    /**
     * 修改通用代办
     * 
     * @param sysTodoList 通用代办
     * @return 结果
     */
    public int updateSysTodoList(SysTodoList sysTodoList);

    /**
     * 删除通用代办
     * 
     * @param taskId 通用代办主键
     * @return 结果
     */
    public int deleteSysTodoListByTaskId(Long taskId);

    /**
     * 批量删除通用代办
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTodoListByTaskIds(Long[] taskIds);
}
