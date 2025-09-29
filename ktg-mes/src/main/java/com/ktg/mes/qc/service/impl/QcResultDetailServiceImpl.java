package com.ktg.mes.qc.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.qc.mapper.QcResultDetailMapper;
import com.ktg.mes.qc.domain.QcResultDetail;
import com.ktg.mes.qc.service.IQcResultDetailService;

/**
 * 检测结果明细记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
@Service
public class QcResultDetailServiceImpl implements IQcResultDetailService 
{
    @Autowired
    private QcResultDetailMapper qcResultDetailMapper;

    /**
     * 查询检测结果明细记录
     * 
     * @param detailId 检测结果明细记录主键
     * @return 检测结果明细记录
     */
    @Override
    public QcResultDetail selectQcResultDetailByDetailId(Long detailId)
    {
        return qcResultDetailMapper.selectQcResultDetailByDetailId(detailId);
    }

    /**
     * 查询检测结果明细记录列表
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 检测结果明细记录
     */
    @Override
    public List<QcResultDetail> selectQcResultDetailList(QcResultDetail qcResultDetail)
    {
        return qcResultDetailMapper.selectQcResultDetailList(qcResultDetail);
    }

    @Override
    public List<QcResultDetail> selectQcResultDetailByResultIdAndIQCId(QcResultDetail qcResultDetail) {
        return qcResultDetailMapper.selectQcResultDetailByResultIdAndIQCId(qcResultDetail);
    }

    @Override
    public List<QcResultDetail> selectQcResultDetailByResultIdAndIPQCId(QcResultDetail qcResultDetail) {
        return qcResultDetailMapper.selectQcResultDetailByResultIdAndIPQCId(qcResultDetail);
    }

    @Override
    public List<QcResultDetail> selectQcResultDetailByResultIdAndOQCId(QcResultDetail qcResultDetail) {
        return qcResultDetailMapper.selectQcResultDetailByResultIdAndOQCId(qcResultDetail);
    }

    @Override
    public List<QcResultDetail> selectQcResultDetailByResultIdAndRQCId(QcResultDetail qcResultDetail) {
        return qcResultDetailMapper.selectQcResultDetailByResultIdAndRQCId(qcResultDetail);
    }

    /**
     * 新增检测结果明细记录
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 结果
     */
    @Override
    public int insertQcResultDetail(QcResultDetail qcResultDetail)
    {
        qcResultDetail.setCreateTime(DateUtils.getNowDate());
        return qcResultDetailMapper.insertQcResultDetail(qcResultDetail);
    }

    /**
     * 修改检测结果明细记录
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 结果
     */
    @Override
    public int updateQcResultDetail(QcResultDetail qcResultDetail)
    {
        qcResultDetail.setUpdateTime(DateUtils.getNowDate());
        return qcResultDetailMapper.updateQcResultDetail(qcResultDetail);
    }

    /**
     * 批量删除检测结果明细记录
     * 
     * @param detailIds 需要删除的检测结果明细记录主键
     * @return 结果
     */
    @Override
    public int deleteQcResultDetailByDetailIds(Long[] detailIds)
    {
        return qcResultDetailMapper.deleteQcResultDetailByDetailIds(detailIds);
    }

    /**
     * 删除检测结果明细记录信息
     * 
     * @param detailId 检测结果明细记录主键
     * @return 结果
     */
    @Override
    public int deleteQcResultDetailByDetailId(Long detailId)
    {
        return qcResultDetailMapper.deleteQcResultDetailByDetailId(detailId);
    }

    @Override
    public int deleteQcResultDetailByResultId(long resultId) {
        return qcResultDetailMapper.deleteQcResultDetailByResultId(resultId);
    }
}
