package com.ktg.mes.qc.mapper;

import java.util.List;
import com.ktg.mes.qc.domain.QcRqc;

/**
 * 退料检验单Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
public interface QcRqcMapper 
{
    /**
     * 查询退料检验单
     * 
     * @param rqcId 退料检验单主键
     * @return 退料检验单
     */
    public QcRqc selectQcRqcByRqcId(Long rqcId);

    /**
     * 查询退料检验单列表
     * 
     * @param qcRqc 退料检验单
     * @return 退料检验单集合
     */
    public List<QcRqc> selectQcRqcList(QcRqc qcRqc);

    /**
     * 检查编码是否一致
     * @param qcRqc
     * @return
     */
    public QcRqc checkCodeUnique(QcRqc qcRqc);

    /**
     * 新增退料检验单
     * 
     * @param qcRqc 退料检验单
     * @return 结果
     */
    public int insertQcRqc(QcRqc qcRqc);

    /**
     * 修改退料检验单
     * 
     * @param qcRqc 退料检验单
     * @return 结果
     */
    public int updateQcRqc(QcRqc qcRqc);

    /**
     * 删除退料检验单
     * 
     * @param rqcId 退料检验单主键
     * @return 结果
     */
    public int deleteQcRqcByRqcId(Long rqcId);

    /**
     * 批量删除退料检验单
     * 
     * @param rqcIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQcRqcByRqcIds(Long[] rqcIds);
}
