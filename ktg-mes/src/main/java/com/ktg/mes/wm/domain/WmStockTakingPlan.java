package com.ktg.mes.wm.domain;

import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 库存盘点方案对象 wm_stock_taking_plan
 * 
 * @author yinjinlu
 * @date 2025-03-21
 */
public class WmStockTakingPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点单ID */
    private Long planId;

    /** 盘点单编号 */
    @Excel(name = "盘点单编号")
    private String planCode;

    /** 盘点单名称 */
    @Excel(name = "盘点单名称")
    private String planName;

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

    /** 是否盲盘 */
    @Excel(name = "是否盲盘")
    private String blindFlag;

    /** 是否库存冻结 */
    @Excel(name = "是否库存冻结")
    private String frozenFlag;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String enableFlag;

    /** 数据过滤SQL */
    @Excel(name = "数据过滤SQL")
    private String dataSql;

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
    public void setTakingType(String takingType) 
    {
        this.takingType = takingType;
    }

    public String getTakingType() 
    {
        return takingType;
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

    public void setBlindFlag(String blindFlag)
    {
        this.blindFlag = blindFlag;
    }

    public String getBlindFlag() 
    {
        return blindFlag;
    }
    public void setFrozenFlag(String frozenFlag) 
    {
        this.frozenFlag = frozenFlag;
    }

    public String getFrozenFlag() 
    {
        return frozenFlag;
    }
    public void setEnableFlag(String enableFlag) 
    {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() 
    {
        return enableFlag;
    }
    public void setDataSql(String dataSql) 
    {
        this.dataSql = dataSql;
    }

    public String getDataSql() 
    {
        return dataSql;
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
            .append("planId", getPlanId())
            .append("planCode", getPlanCode())
            .append("planName", getPlanName())
            .append("takingType", getTakingType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("blindFlag", getBlindFlag())
            .append("frozenFlag", getFrozenFlag())
            .append("enableFlag", getEnableFlag())
            .append("dataSql", getDataSql())
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
