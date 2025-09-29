package com.ktg.mes.dv.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 设备点检记录对象 dv_check_record
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public class DvCheckRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    private Long recordId;

    /** 计划ID */
    @Excel(name = "计划ID")
    private Long planId;

    /** 计划编码 */
    @Excel(name = "计划编码")
    private String planCode;

    /** 计划名称 */
    @Excel(name = "计划名称")
    private String planName;

    /** 计划类型 */
    @Excel(name = "计划类型")
    private String planType;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long machineryId;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String machineryCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String machineryName;

    /** 品牌 */
    @Excel(name = "品牌")
    private String machineryBrand;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String machinerySpec;

    /** 点检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @Excel(name = "点检时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    private Long userId;

    private String userName;

    private String nickName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setPlanCode(String planCode) 
    {
        this.planCode = planCode;
    }

    public String getPlanCode() 
    {
        return planCode;
    }
    public void setPlanName(String planName) 
    {
        this.planName = planName;
    }

    public String getPlanName() 
    {
        return planName;
    }
    public void setPlanType(String planType) 
    {
        this.planType = planType;
    }

    public String getPlanType() 
    {
        return planType;
    }
    public void setMachineryId(Long machineryId) 
    {
        this.machineryId = machineryId;
    }

    public Long getMachineryId() 
    {
        return machineryId;
    }
    public void setMachineryCode(String machineryCode) 
    {
        this.machineryCode = machineryCode;
    }

    public String getMachineryCode() 
    {
        return machineryCode;
    }
    public void setMachineryName(String machineryName) 
    {
        this.machineryName = machineryName;
    }

    public String getMachineryName() 
    {
        return machineryName;
    }
    public void setMachineryBrand(String machineryBrand) 
    {
        this.machineryBrand = machineryBrand;
    }

    public String getMachineryBrand() 
    {
        return machineryBrand;
    }
    public void setMachinerySpec(String machinerySpec) 
    {
        this.machinerySpec = machinerySpec;
    }

    public String getMachinerySpec() 
    {
        return machinerySpec;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
            .append("recordId", getRecordId())
            .append("planId", getPlanId())
            .append("planCode", getPlanCode())
            .append("planName", getPlanName())
            .append("planType", getPlanType())
            .append("machineryId", getMachineryId())
            .append("machineryCode", getMachineryCode())
            .append("machineryName", getMachineryName())
            .append("machineryBrand", getMachineryBrand())
            .append("machinerySpec", getMachinerySpec())
            .append("checkTime", getCheckTime())
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
