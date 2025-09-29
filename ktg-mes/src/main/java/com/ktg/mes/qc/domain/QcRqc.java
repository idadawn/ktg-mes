package com.ktg.mes.qc.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 退料检验单对象 qc_rqc
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
public class QcRqc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 检验单ID */
    private Long rqcId;

    /** 检验单编号 */
    @Excel(name = "检验单编号")
    private String rqcCode;

    /** 检验单名称 */
    @Excel(name = "检验单名称")
    private String rqcName;

    /** 检验模板ID */
    @Excel(name = "检验模板ID")
    private Long templateId;

    /** 来源单据ID */
    @Excel(name = "来源单据ID")
    private Long sourceDocId;

    /** 来源单据类型 */
    @Excel(name = "来源单据类型")
    private String sourceDocType;

    /** 来源单据编号 */
    @Excel(name = "来源单据编号")
    private String sourceDocCode;

    /** 来源单据行ID */
    @Excel(name = "来源单据行ID")
    private Long sourceLineId;

    /** 产品物料ID */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /** 产品物料编码 */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /** 产品物料名称 */
    @Excel(name = "产品物料名称")
    private String itemName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long batchId;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchCode;

    /** 检测数量 */
    @Excel(name = "检测数量")
    private BigDecimal quantityCheck;

    /** 不合格数 */
    @Excel(name = "不合格数")
    private BigDecimal quantityUnqualified;

    /** 合格品数量 */
    @Excel(name = "合格品数量")
    private BigDecimal quantityQualified;

    /** 检测结果 */
    @Excel(name = "检测结果")
    private String checkResult;

    /** 检测日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectDate;

    /** 检测人员ID */
    @Excel(name = "检测人员ID")
    private Long userId;

    /** 检测人员名称 */
    @Excel(name = "检测人员名称")
    private String userName;

    /** 检测人员 */
    @Excel(name = "检测人员")
    private String nickName;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private String status;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setRqcId(Long rqcId) 
    {
        this.rqcId = rqcId;
    }

    public Long getRqcId() 
    {
        return rqcId;
    }
    public void setRqcCode(String rqcCode) 
    {
        this.rqcCode = rqcCode;
    }

    public String getRqcCode() 
    {
        return rqcCode;
    }
    public void setRqcName(String rqcName) 
    {
        this.rqcName = rqcName;
    }

    public String getRqcName() 
    {
        return rqcName;
    }
    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }
    public void setSourceDocId(Long sourceDocId) 
    {
        this.sourceDocId = sourceDocId;
    }

    public Long getSourceDocId() 
    {
        return sourceDocId;
    }
    public void setSourceDocType(String sourceDocType) 
    {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocType() 
    {
        return sourceDocType;
    }
    public void setSourceDocCode(String sourceDocCode) 
    {
        this.sourceDocCode = sourceDocCode;
    }

    public String getSourceDocCode() 
    {
        return sourceDocCode;
    }
    public void setSourceLineId(Long sourceLineId) 
    {
        this.sourceLineId = sourceLineId;
    }

    public Long getSourceLineId() 
    {
        return sourceLineId;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setUnitOfMeasure(String unitOfMeasure) 
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() 
    {
        return unitOfMeasure;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setBatchId(Long batchId) 
    {
        this.batchId = batchId;
    }

    public Long getBatchId() 
    {
        return batchId;
    }
    public void setBatchCode(String batchCode) 
    {
        this.batchCode = batchCode;
    }

    public String getBatchCode() 
    {
        return batchCode;
    }
    public void setQuantityCheck(BigDecimal quantityCheck) 
    {
        this.quantityCheck = quantityCheck;
    }

    public BigDecimal getQuantityCheck() 
    {
        return quantityCheck;
    }
    public void setQuantityUnqualified(BigDecimal quantityUnqualified) 
    {
        this.quantityUnqualified = quantityUnqualified;
    }

    public BigDecimal getQuantityUnqualified() 
    {
        return quantityUnqualified;
    }
    public void setQuantityQualified(BigDecimal quantityQualified) 
    {
        this.quantityQualified = quantityQualified;
    }

    public BigDecimal getQuantityQualified() 
    {
        return quantityQualified;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setInspectDate(Date inspectDate) 
    {
        this.inspectDate = inspectDate;
    }

    public Date getInspectDate() 
    {
        return inspectDate;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("rqcId", getRqcId())
            .append("rqcCode", getRqcCode())
            .append("rqcName", getRqcName())
            .append("templateId", getTemplateId())
            .append("sourceDocId", getSourceDocId())
            .append("sourceDocType", getSourceDocType())
            .append("sourceDocCode", getSourceDocCode())
            .append("sourceLineId", getSourceLineId())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("specification", getSpecification())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("unitName", getUnitName())
            .append("batchId", getBatchId())
            .append("batchCode", getBatchCode())
            .append("quantityCheck", getQuantityCheck())
            .append("quantityUnqualified", getQuantityUnqualified())
            .append("quantityQualified", getQuantityQualified())
            .append("checkResult", getCheckResult())
            .append("inspectDate", getInspectDate())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("status", getStatus())
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
