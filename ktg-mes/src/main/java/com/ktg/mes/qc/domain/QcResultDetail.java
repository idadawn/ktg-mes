package com.ktg.mes.qc.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 检测结果明细记录对象 qc_result_detail
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
public class QcResultDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long qcId;

    private String qcType;

    /** 流水号 */
    private Long detailId;

    /** 结果记录ID */
    @Excel(name = "结果记录ID")
    private Long resultId;

    /** 检测项ID */
    @Excel(name = "检测项ID")
    private Long indexId;


    private String indexCode;


    private String indexName;


    /** 检测项类型 */
    @Excel(name = "检测项类型")
    private String indexType;

    /** 检测工具 */
    @Excel(name = "检测工具")
    private String qcTool;

    /** 检测要求 */
    @Excel(name = "检测要求")
    private String checkMethod;

    /** 标准值 */
    @Excel(name = "标准值")
    private BigDecimal standerVal;

    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /** 误差上限 */
    @Excel(name = "误差上限")
    private BigDecimal thresholdMax;

    /** 误差下限 */
    @Excel(name = "误差下限")
    private BigDecimal thresholdMin;

    /** 质检值类型 */
    @Excel(name = "质检值类型")
    private String qcResultType;

    /** 值属性 */
    @Excel(name = "值属性")
    private String qcResultSpc;

    /** 浮点值 */
    @Excel(name = "浮点值")
    private BigDecimal qcValFloat;

    /** 整数 */
    @Excel(name = "整数")
    private Long qcValInteger;

    /** 文字 */
    @Excel(name = "文字")
    private String qcValText;

    /** 字典项 */
    @Excel(name = "字典项")
    private String qcValDict;

    /** 文件 */
    @Excel(name = "文件")
    private String qcValFile;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String attr1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String attr2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private Long attr3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private Long attr4;

    public Long getQcId() {
        return qcId;
    }

    public void setQcId(Long qcId) {
        this.qcId = qcId;
    }

    public String getQcType() {
        return qcType;
    }

    public void setQcType(String qcType) {
        this.qcType = qcType;
    }

    public void setDetailId(Long detailId)
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setResultId(Long resultId) 
    {
        this.resultId = resultId;
    }

    public Long getResultId() 
    {
        return resultId;
    }
    public void setIndexId(Long indexId) 
    {
        this.indexId = indexId;
    }

    public Long getIndexId() 
    {
        return indexId;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setIndexType(String indexType)
    {
        this.indexType = indexType;
    }

    public String getIndexType() 
    {
        return indexType;
    }
    public void setQcTool(String qcTool) 
    {
        this.qcTool = qcTool;
    }

    public String getQcTool() 
    {
        return qcTool;
    }
    public void setCheckMethod(String checkMethod) 
    {
        this.checkMethod = checkMethod;
    }

    public String getCheckMethod() 
    {
        return checkMethod;
    }
    public void setStanderVal(BigDecimal standerVal) 
    {
        this.standerVal = standerVal;
    }

    public BigDecimal getStanderVal() 
    {
        return standerVal;
    }
    public void setUnitOfMeasure(String unitOfMeasure) 
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() 
    {
        return unitOfMeasure;
    }
    public void setThresholdMax(BigDecimal thresholdMax) 
    {
        this.thresholdMax = thresholdMax;
    }

    public BigDecimal getThresholdMax() 
    {
        return thresholdMax;
    }
    public void setThresholdMin(BigDecimal thresholdMin) 
    {
        this.thresholdMin = thresholdMin;
    }

    public BigDecimal getThresholdMin() 
    {
        return thresholdMin;
    }
    public void setQcResultType(String qcResultType) 
    {
        this.qcResultType = qcResultType;
    }

    public String getQcResultType() 
    {
        return qcResultType;
    }
    public void setQcResultSpc(String qcResultSpc) 
    {
        this.qcResultSpc = qcResultSpc;
    }

    public String getQcResultSpc() 
    {
        return qcResultSpc;
    }
    public void setQcValFloat(BigDecimal qcValFloat) 
    {
        this.qcValFloat = qcValFloat;
    }

    public BigDecimal getQcValFloat() 
    {
        return qcValFloat;
    }
    public void setQcValInteger(Long qcValInteger) 
    {
        this.qcValInteger = qcValInteger;
    }

    public Long getQcValInteger() 
    {
        return qcValInteger;
    }
    public void setQcValText(String qcValText) 
    {
        this.qcValText = qcValText;
    }

    public String getQcValText() 
    {
        return qcValText;
    }
    public void setQcValDict(String qcValDict) 
    {
        this.qcValDict = qcValDict;
    }

    public String getQcValDict() 
    {
        return qcValDict;
    }
    public void setQcValFile(String qcValFile) 
    {
        this.qcValFile = qcValFile;
    }

    public String getQcValFile() 
    {
        return qcValFile;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("resultId", getResultId())
            .append("indexId", getIndexId())
            .append("indexType", getIndexType())
            .append("qcTool", getQcTool())
            .append("checkMethod", getCheckMethod())
            .append("standerVal", getStanderVal())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("thresholdMax", getThresholdMax())
            .append("thresholdMin", getThresholdMin())
            .append("qcResultType", getQcResultType())
            .append("qcResultSpc", getQcResultSpc())
            .append("qcValFloat", getQcValFloat())
            .append("qcValInteger", getQcValInteger())
            .append("qcValText", getQcValText())
            .append("qcValDict", getQcValDict())
            .append("qcValFile", getQcValFile())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
