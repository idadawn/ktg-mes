package com.ktg.mes.wm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 杂项出库单对象 wm_misc_issue
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public class WmMiscIssue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 杂项出库单ID */
    private Long issueId;

    /** 杂项出库单编号 */
    @Excel(name = "杂项出库单编号")
    private String issueCode;

    /** 杂项出库单名称 */
    @Excel(name = "杂项出库单名称")
    private String issueName;

    /** 杂项事务类型 */
    @Excel(name = "杂项事务类型")
    private String miscType;

    /** 来源单据ID */
    @Excel(name = "来源单据ID")
    private Long sourceDocId;

    /** 来源单据编号 */
    @Excel(name = "来源单据编号")
    private String sourceDocCode;

    /** 来源单据类型 */
    @Excel(name = "来源单据类型")
    private String sourceDocType;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

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

    public void setIssueId(Long issueId) 
    {
        this.issueId = issueId;
    }

    public Long getIssueId() 
    {
        return issueId;
    }
    public void setIssueCode(String issueCode) 
    {
        this.issueCode = issueCode;
    }

    public String getIssueCode() 
    {
        return issueCode;
    }
    public void setIssueName(String issueName) 
    {
        this.issueName = issueName;
    }

    public String getIssueName() 
    {
        return issueName;
    }
    public void setMiscType(String miscType) 
    {
        this.miscType = miscType;
    }

    public String getMiscType() 
    {
        return miscType;
    }
    public void setSourceDocId(Long sourceDocId) 
    {
        this.sourceDocId = sourceDocId;
    }

    public Long getSourceDocId() 
    {
        return sourceDocId;
    }
    public void setSourceDocCode(String sourceDocCode) 
    {
        this.sourceDocCode = sourceDocCode;
    }

    public String getSourceDocCode() 
    {
        return sourceDocCode;
    }
    public void setSourceDocType(String sourceDocType) 
    {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocType() 
    {
        return sourceDocType;
    }
    public void setIssueDate(Date issueDate) 
    {
        this.issueDate = issueDate;
    }

    public Date getIssueDate() 
    {
        return issueDate;
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
            .append("issueId", getIssueId())
            .append("issueCode", getIssueCode())
            .append("issueName", getIssueName())
            .append("miscType", getMiscType())
            .append("sourceDocId", getSourceDocId())
            .append("sourceDocCode", getSourceDocCode())
            .append("sourceDocType", getSourceDocType())
            .append("issueDate", getIssueDate())
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
