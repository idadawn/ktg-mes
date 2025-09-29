package com.ktg.mes.qc.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.qc.mapper.QcResultMapper;
import com.ktg.mes.qc.domain.QcResult;
import com.ktg.mes.qc.service.IQcResultService;

/**
 * 检测结果记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
@Service
public class QcResultServiceImpl implements IQcResultService 
{
    @Autowired
    private QcResultMapper qcResultMapper;

    /**
     * 查询检测结果记录
     * 
     * @param resultId 检测结果记录主键
     * @return 检测结果记录
     */
    @Override
    public QcResult selectQcResultByResultId(Long resultId)
    {
        return qcResultMapper.selectQcResultByResultId(resultId);
    }

    /**
     * 查询检测结果记录列表
     * 
     * @param qcResult 检测结果记录
     * @return 检测结果记录
     */
    @Override
    public List<QcResult> selectQcResultList(QcResult qcResult)
    {
        return qcResultMapper.selectQcResultList(qcResult);
    }

    /**
     * 新增检测结果记录
     * 
     * @param qcResult 检测结果记录
     * @return 结果
     */
    @Override
    public int insertQcResult(QcResult qcResult)
    {
        qcResult.setCreateTime(DateUtils.getNowDate());
        return qcResultMapper.insertQcResult(qcResult);
    }

    /**
     * 修改检测结果记录
     * 
     * @param qcResult 检测结果记录
     * @return 结果
     */
    @Override
    public int updateQcResult(QcResult qcResult)
    {
        qcResult.setUpdateTime(DateUtils.getNowDate());
        return qcResultMapper.updateQcResult(qcResult);
    }

    /**
     * 批量删除检测结果记录
     * 
     * @param resultIds 需要删除的检测结果记录主键
     * @return 结果
     */
    @Override
    public int deleteQcResultByResultIds(Long[] resultIds)
    {
        return qcResultMapper.deleteQcResultByResultIds(resultIds);
    }

    /**
     * 删除检测结果记录信息
     * 
     * @param resultId 检测结果记录主键
     * @return 结果
     */
    @Override
    public int deleteQcResultByResultId(Long resultId)
    {
        return qcResultMapper.deleteQcResultByResultId(resultId);
    }
}
