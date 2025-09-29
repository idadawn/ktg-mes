package com.ktg.mes.qc.mapper;

import java.util.List;
import com.ktg.mes.qc.domain.QcResultDetail;

/**
 * 检测结果明细记录Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
public interface QcResultDetailMapper 
{
    /**
     * 查询检测结果明细记录
     * 
     * @param detailId 检测结果明细记录主键
     * @return 检测结果明细记录
     */
    public QcResultDetail selectQcResultDetailByDetailId(Long detailId);

    /**
     * 查询检测结果明细记录列表
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 检测结果明细记录集合
     */
    public List<QcResultDetail> selectQcResultDetailList(QcResultDetail qcResultDetail);

    /**
     * 查询某个IQC单据下某个ResultId的检测值列表
     * 如果ResultId为空，则返回此单据的根据index检查项构造的列表
     * @param qcResultDetail
     * @return
     */
    public List<QcResultDetail> selectQcResultDetailByResultIdAndIQCId(QcResultDetail qcResultDetail);


    /**
     * 查询某个IPQC单据下某个ResultId的检测值列表
     * 如果ResultId为空，则返回此单据的根据index检查项构造的列表
     * @param qcResultDetail
     * @return
     */
    public List<QcResultDetail> selectQcResultDetailByResultIdAndIPQCId(QcResultDetail qcResultDetail);

    /**
     * 查询某个OQC单据下某个ResultId的检测值列表
     * 如果ResultId为空，则返回此单据的根据index检查项构造的列表
     * @param qcResultDetail
     * @return
     */
    public List<QcResultDetail> selectQcResultDetailByResultIdAndOQCId(QcResultDetail qcResultDetail);

    /**
     * 查询某个RQC单据下某个ResultId的检测值列表
     * 如果ResultId为空，则返回此单据的根据index检查项构造的列表
     * @param qcResultDetail
     * @return
     */
    public List<QcResultDetail> selectQcResultDetailByResultIdAndRQCId(QcResultDetail qcResultDetail);

    /**
     * 新增检测结果明细记录
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 结果
     */
    public int insertQcResultDetail(QcResultDetail qcResultDetail);

    /**
     * 修改检测结果明细记录
     * 
     * @param qcResultDetail 检测结果明细记录
     * @return 结果
     */
    public int updateQcResultDetail(QcResultDetail qcResultDetail);

    /**
     * 删除检测结果明细记录
     * 
     * @param detailId 检测结果明细记录主键
     * @return 结果
     */
    public int deleteQcResultDetailByDetailId(Long detailId);

    /**
     * 批量删除检测结果明细记录
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQcResultDetailByDetailIds(Long[] detailIds);

    /**
     * 根据检测结果ID删除所有明细信息
     * @param resultId
     * @return
     */
    public int deleteQcResultDetailByResultId(long resultId);
}
