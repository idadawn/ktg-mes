package com.ktg.mes.wm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 生产退料单头对象 wm_rt_issue
 * 
 * @author yinjinlu
 * @date 2022-09-15
 */
public class WmRtIssue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 退料单ID */
    private Long rtId;

    /** 退料单编号 */
    @Excel(name = "退料单编号")
    private String rtCode;

    /** 退料单名称 */
    @Excel(name = "退料单名称")
    private String rtName;

    /** 生产工单ID */
    @Excel(name = "生产工单ID")
    private Long workorderId;

    /** 生产工单编码 */
    @Excel(name = "生产工单编码")
    private String workorderCode;


    private Long workstationId;

    private String workstationCode;

    private String workstationName;

    @Excel(name = "退料类型")
    private String rtType;

    /** 退料日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rtDate;

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

    public void setRtId(Long rtId) 
    {
        this.rtId = rtId;
    }

    public Long getRtId() 
    {
        return rtId;
    }
    public void setRtCode(String rtCode) 
    {
        this.rtCode = rtCode;
    }

    public String getRtCode() 
    {
        return rtCode;
    }
    public void setRtName(String rtName) 
    {
        this.rtName = rtName;
    }

    public String getRtName() 
    {
        return rtName;
    }
    public void setWorkorderId(Long workorderId) 
    {
        this.workorderId = workorderId;
    }

    public Long getWorkorderId() 
    {
        return workorderId;
    }
    public void setWorkorderCode(String workorderCode) 
    {
        this.workorderCode = workorderCode;
    }

    public String getWorkorderCode() 
    {
        return workorderCode;
    }

    public Long getWorkstationId() {
        return workstationId;
    }

    public void setWorkstationId(Long workstationId) {
        this.workstationId = workstationId;
    }

    public String getWorkstationCode() {
        return workstationCode;
    }

    public void setWorkstationCode(String workstationCode) {
        this.workstationCode = workstationCode;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public String getRtType() {
        return rtType;
    }

    public void setRtType(String rtType) {
        this.rtType = rtType;
    }

    public void setRtDate(Date rtDate)
    {
        this.rtDate = rtDate;
    }

    public Date getRtDate() 
    {
        return rtDate;
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
            .append("rtId", getRtId())
            .append("rtCode", getRtCode())
            .append("rtName", getRtName())
            .append("workorderId", getWorkorderId())
            .append("workorderCode", getWorkorderCode())
            .append("rtDate", getRtDate())
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
