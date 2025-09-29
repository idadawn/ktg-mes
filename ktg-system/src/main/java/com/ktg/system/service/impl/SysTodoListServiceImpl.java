package com.ktg.system.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.model.LoginUser;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.system.mapper.SysTodoListMapper;
import com.ktg.system.domain.SysTodoList;
import com.ktg.system.service.ISysTodoListService;

/**
 * 通用代办Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
@Service
public class SysTodoListServiceImpl implements ISysTodoListService 
{
    @Autowired
    private SysTodoListMapper sysTodoListMapper;

    /**
     * 查询通用代办
     * 
     * @param taskId 通用代办主键
     * @return 通用代办
     */
    @Override
    public SysTodoList selectSysTodoListByTaskId(Long taskId)
    {
        return sysTodoListMapper.selectSysTodoListByTaskId(taskId);
    }

    /**
     * 查询通用代办列表
     * 
     * @param sysTodoList 通用代办
     * @return 通用代办
     */
    @Override
    public List<SysTodoList> selectSysTodoListList(SysTodoList sysTodoList)
    {
        return sysTodoListMapper.selectSysTodoListList(sysTodoList);
    }

    /**
     * 查询我的代办列表
     * @param processStatus
     * @return
     */
    @Override
    public List<SysTodoList> selectMyTotoList(String processStatus) {
        LoginUser user =SecurityUtils.getLoginUser();
        SysTodoList param = new SysTodoList();
        param.setOwnerId(user.getUserId());
        param.setStatus(UserConstants.TODOLIST_STATUS_NORMAL);
        List<SysTodoList> list = sysTodoListMapper.selectSysTodoListList(param);
        return list;
    }

    /**
     * 新增通用代办
     * 
     * @param sysTodoList 通用代办
     * @return 结果
     */
    @Override
    public int insertSysTodoList(SysTodoList sysTodoList)
    {
        sysTodoList.setCreateTime(DateUtils.getNowDate());
        return sysTodoListMapper.insertSysTodoList(sysTodoList);
    }

    /**
     * 修改通用代办
     * 
     * @param sysTodoList 通用代办
     * @return 结果
     */
    @Override
    public int updateSysTodoList(SysTodoList sysTodoList)
    {
        sysTodoList.setUpdateTime(DateUtils.getNowDate());
        return sysTodoListMapper.updateSysTodoList(sysTodoList);
    }

    /**
     * 批量删除通用代办
     * 
     * @param taskIds 需要删除的通用代办主键
     * @return 结果
     */
    @Override
    public int deleteSysTodoListByTaskIds(Long[] taskIds)
    {
        return sysTodoListMapper.deleteSysTodoListByTaskIds(taskIds);
    }

    /**
     * 删除通用代办信息
     * 
     * @param taskId 通用代办主键
     * @return 结果
     */
    @Override
    public int deleteSysTodoListByTaskId(Long taskId)
    {
        return sysTodoListMapper.deleteSysTodoListByTaskId(taskId);
    }
}
