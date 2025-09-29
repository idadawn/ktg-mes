package com.ktg.mes.qc.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.qc.mapper.QcRqcLineMapper;
import com.ktg.mes.qc.domain.QcRqcLine;
import com.ktg.mes.qc.service.IQcRqcLineService;

/**
 * 退料检验单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
@Service
public class QcRqcLineServiceImpl implements IQcRqcLineService 
{
    @Autowired
    private QcRqcLineMapper qcRqcLineMapper;

    /**
     * 查询退料检验单行
     * 
     * @param lineId 退料检验单行主键
     * @return 退料检验单行
     */
    @Override
    public QcRqcLine selectQcRqcLineByLineId(Long lineId)
    {
        return qcRqcLineMapper.selectQcRqcLineByLineId(lineId);
    }

    /**
     * 查询退料检验单行列表
     * 
     * @param qcRqcLine 退料检验单行
     * @return 退料检验单行
     */
    @Override
    public List<QcRqcLine> selectQcRqcLineList(QcRqcLine qcRqcLine)
    {
        return qcRqcLineMapper.selectQcRqcLineList(qcRqcLine);
    }

    /**
     * 新增退料检验单行
     * 
     * @param qcRqcLine 退料检验单行
     * @return 结果
     */
    @Override
    public int insertQcRqcLine(QcRqcLine qcRqcLine)
    {
        qcRqcLine.setCreateTime(DateUtils.getNowDate());
        return qcRqcLineMapper.insertQcRqcLine(qcRqcLine);
    }

    /**
     * 修改退料检验单行
     * 
     * @param qcRqcLine 退料检验单行
     * @return 结果
     */
    @Override
    public int updateQcRqcLine(QcRqcLine qcRqcLine)
    {
        qcRqcLine.setUpdateTime(DateUtils.getNowDate());
        return qcRqcLineMapper.updateQcRqcLine(qcRqcLine);
    }

    /**
     * 批量删除退料检验单行
     * 
     * @param lineIds 需要删除的退料检验单行主键
     * @return 结果
     */
    @Override
    public int deleteQcRqcLineByLineIds(Long[] lineIds)
    {
        return qcRqcLineMapper.deleteQcRqcLineByLineIds(lineIds);
    }

    /**
     * 删除退料检验单行信息
     * 
     * @param lineId 退料检验单行主键
     * @return 结果
     */
    @Override
    public int deleteQcRqcLineByLineId(Long lineId)
    {
        return qcRqcLineMapper.deleteQcRqcLineByLineId(lineId);
    }

    @Override
    public int deleteQcRqcLineByRqcId(Long rqcId) {
        return qcRqcLineMapper.deleteQcRqcLineByRqcId(rqcId);
    }
}
