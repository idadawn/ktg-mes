package com.ktg.mes.qc.service;

import java.util.List;
import com.ktg.mes.qc.domain.QcRqcLine;

/**
 * 退料检验单行Service接口
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
public interface IQcRqcLineService 
{
    /**
     * 查询退料检验单行
     * 
     * @param lineId 退料检验单行主键
     * @return 退料检验单行
     */
    public QcRqcLine selectQcRqcLineByLineId(Long lineId);

    /**
     * 查询退料检验单行列表
     * 
     * @param qcRqcLine 退料检验单行
     * @return 退料检验单行集合
     */
    public List<QcRqcLine> selectQcRqcLineList(QcRqcLine qcRqcLine);

    /**
     * 新增退料检验单行
     * 
     * @param qcRqcLine 退料检验单行
     * @return 结果
     */
    public int insertQcRqcLine(QcRqcLine qcRqcLine);

    /**
     * 修改退料检验单行
     * 
     * @param qcRqcLine 退料检验单行
     * @return 结果
     */
    public int updateQcRqcLine(QcRqcLine qcRqcLine);

    /**
     * 批量删除退料检验单行
     * 
     * @param lineIds 需要删除的退料检验单行主键集合
     * @return 结果
     */
    public int deleteQcRqcLineByLineIds(Long[] lineIds);

    /**
     * 删除退料检验单行信息
     * 
     * @param lineId 退料检验单行主键
     * @return 结果
     */
    public int deleteQcRqcLineByLineId(Long lineId);

    /**
     * 根据退料单头ID删除退料检验单行信息
     * @param rqcId
     * @return
     */
    public int deleteQcRqcLineByRqcId(Long rqcId);

}
