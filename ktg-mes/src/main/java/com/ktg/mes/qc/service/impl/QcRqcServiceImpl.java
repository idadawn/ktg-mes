package com.ktg.mes.qc.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.qc.mapper.QcRqcMapper;
import com.ktg.mes.qc.domain.QcRqc;
import com.ktg.mes.qc.service.IQcRqcService;

/**
 * 退料检验单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
@Service
public class QcRqcServiceImpl implements IQcRqcService 
{
    @Autowired
    private QcRqcMapper qcRqcMapper;

    /**
     * 查询退料检验单
     * 
     * @param rqcId 退料检验单主键
     * @return 退料检验单
     */
    @Override
    public QcRqc selectQcRqcByRqcId(Long rqcId)
    {
        return qcRqcMapper.selectQcRqcByRqcId(rqcId);
    }

    /**
     * 查询退料检验单列表
     * 
     * @param qcRqc 退料检验单
     * @return 退料检验单
     */
    @Override
    public List<QcRqc> selectQcRqcList(QcRqc qcRqc)
    {
        return qcRqcMapper.selectQcRqcList(qcRqc);
    }


    @Override
    public String checkCodeUnique(QcRqc qcRqc) {
        QcRqc rqc = qcRqcMapper.checkCodeUnique(qcRqc);
        Long rqcId = qcRqc.getRqcId()==null?-1L:qcRqc.getRqcId();
        if(StringUtils.isNotNull(rqc) && rqc.getRqcId().longValue() != rqcId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增退料检验单
     * 
     * @param qcRqc 退料检验单
     * @return 结果
     */
    @Override
    public int insertQcRqc(QcRqc qcRqc)
    {
        qcRqc.setCreateTime(DateUtils.getNowDate());
        return qcRqcMapper.insertQcRqc(qcRqc);
    }

    /**
     * 修改退料检验单
     * 
     * @param qcRqc 退料检验单
     * @return 结果
     */
    @Override
    public int updateQcRqc(QcRqc qcRqc)
    {
        qcRqc.setUpdateTime(DateUtils.getNowDate());
        return qcRqcMapper.updateQcRqc(qcRqc);
    }

    /**
     * 批量删除退料检验单
     * 
     * @param rqcIds 需要删除的退料检验单主键
     * @return 结果
     */
    @Override
    public int deleteQcRqcByRqcIds(Long[] rqcIds)
    {
        return qcRqcMapper.deleteQcRqcByRqcIds(rqcIds);
    }

    /**
     * 删除退料检验单信息
     * 
     * @param rqcId 退料检验单主键
     * @return 结果
     */
    @Override
    public int deleteQcRqcByRqcId(Long rqcId)
    {
        return qcRqcMapper.deleteQcRqcByRqcId(rqcId);
    }
}
