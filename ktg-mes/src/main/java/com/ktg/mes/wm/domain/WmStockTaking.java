package com.ktg.mes.wm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 库存盘点记录对象 wm_stock_taking
 * 
 * @author yinjinlu
 * @date 2023-08-17
 */
public class WmStockTaking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点单ID */
    private Long takingId;

    /** 盘点单编号 */
    @Excel(name = "盘点单编号")
    private String takingCode;

    /** 盘点单名称 */
    @Excel(name = "盘点单名称")
    private String takingName;

    /** 盘点日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date takingDate;

    /** 盘点人用户名 */
    @Excel(name = "盘点人用户名")
    private String userName;

    /** 盘点人 */
    @Excel(name = "盘点人")
    private String nickName;

    /** 盘点类型 */
    @Excel(name = "盘点类型")
    private String takingType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String blindFlag;

    private String frozenFlag;

    private Long planId;

    private String planCode;

    private String planName;


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

    public void setTakingId(Long takingId) 
    {
        this.takingId = takingId;
    }

    public Long getTakingId() 
    {
        return takingId;
    }
    public void setTakingCode(String takingCode) 
    {
        this.takingCode = takingCode;
    }

    public String getTakingCode() 
    {
        return takingCode;
    }
    public void setTakingName(String takingName) 
    {
        this.takingName = takingName;
    }

    public String getTakingName() 
    {
        return takingName;
    }
    public void setTakingDate(Date takingDate) 
    {
        this.takingDate = takingDate;
    }

    public Date getTakingDate() 
    {
        return takingDate;
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
    public void setTakingType(String takingType) 
    {
        this.takingType = takingType;
    }

    public String getBlindFlag() {
        return blindFlag;
    }

    public void setBlindFlag(String blindFlag) {
        this.blindFlag = blindFlag;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTakingType()
    {
        return takingType;
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
            .append("takingId", getTakingId())
            .append("takingCode", getTakingCode())
            .append("takingName", getTakingName())
            .append("takingDate", getTakingDate())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("takingType", getTakingType())
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
