package com.ktg.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 通用代办对象 sys_todo_list
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
public class SysTodoList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 代办ID */
    private Long taskId;

    /** 代办名称 */
    @Excel(name = "代办名称")
    private String taskName;

    /** 代办类型（0-流程待办，1-业务待办） */
    @Excel(name = "代办类型", readConverterExp = "0=-流程待办，1-业务待办")
    private Long taskType;

    /** 任务来源系统 */
    @Excel(name = "任务来源系统")
    private String sysCode;

    /** Web端回调地址 */
    @Excel(name = "Web端回调地址")
    private String url;

    /** APP端回调地址 */
    @Excel(name = "APP端回调地址")
    private String appUrl;

    /** H5端回调地址（小程序、公众号等） */
    @Excel(name = "H5端回调地址", readConverterExp = "小=程序、公众号等")
    private String h5Url;

    /** 优先级 */
    @Excel(name = "优先级")
    private String priority;

    /** 业务ID */
    @Excel(name = "业务ID")
    private Long businessId;

    /** 业务编码 */
    @Excel(name = "业务编码")
    private String businessCode;

    /** 业务大类 */
    @Excel(name = "业务大类")
    private String businessCategory;

    /** 业务分类（小类） */
    @Excel(name = "业务分类", readConverterExp = "小=类")
    private String businessType;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private String businessName;

    /** 代办所属人ID */
    @Excel(name = "代办所属人ID")
    private Long ownerId;

    /** 代办所属人用户名 */
    @Excel(name = "代办所属人用户名")
    private String ownerName;

    /** 代办所属人名称 */
    @Excel(name = "代办所属人名称")
    private String ownerNick;

    /** 发起人ID */
    @Excel(name = "发起人ID")
    private Long creatorId;

    /** 发起人用户名 */
    @Excel(name = "发起人用户名")
    private String creatorName;

    /** 发起人名称 */
    @Excel(name = "发起人名称")
    private String creatorNick;

    /** 流转状态（PASS-正常流转，REJECT-退回上一步，FIRST-退回发起人） */
    @Excel(name = "流转状态", readConverterExp = "P=ASS-正常流转，REJECT-退回上一步，FIRST-退回发起人")
    private String processStatus;

    /** 流程节点名称 */
    @Excel(name = "流程节点名称")
    private String nodeName;

    /** 状态（0-正常，1-已完成，2-已删除） */
    @Excel(name = "状态", readConverterExp = "0=-正常，1-已完成，2-已删除")
    private Integer status;

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

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setTaskType(Long taskType) 
    {
        this.taskType = taskType;
    }

    public Long getTaskType() 
    {
        return taskType;
    }
    public void setSysCode(String sysCode) 
    {
        this.sysCode = sysCode;
    }

    public String getSysCode() 
    {
        return sysCode;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setAppUrl(String appUrl) 
    {
        this.appUrl = appUrl;
    }

    public String getAppUrl() 
    {
        return appUrl;
    }
    public void setH5Url(String h5Url) 
    {
        this.h5Url = h5Url;
    }

    public String getH5Url() 
    {
        return h5Url;
    }
    public void setPriority(String priority) 
    {
        this.priority = priority;
    }

    public String getPriority() 
    {
        return priority;
    }
    public void setBusinessId(Long businessId) 
    {
        this.businessId = businessId;
    }

    public Long getBusinessId() 
    {
        return businessId;
    }
    public void setBusinessCode(String businessCode) 
    {
        this.businessCode = businessCode;
    }

    public String getBusinessCode() 
    {
        return businessCode;
    }
    public void setBusinessCategory(String businessCategory) 
    {
        this.businessCategory = businessCategory;
    }

    public String getBusinessCategory() 
    {
        return businessCategory;
    }
    public void setBusinessType(String businessType) 
    {
        this.businessType = businessType;
    }

    public String getBusinessType() 
    {
        return businessType;
    }
    public void setBusinessName(String businessName) 
    {
        this.businessName = businessName;
    }

    public String getBusinessName() 
    {
        return businessName;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }
    public void setOwnerNick(String ownerNick) 
    {
        this.ownerNick = ownerNick;
    }

    public String getOwnerNick() 
    {
        return ownerNick;
    }
    public void setCreatorId(Long creatorId) 
    {
        this.creatorId = creatorId;
    }

    public Long getCreatorId() 
    {
        return creatorId;
    }
    public void setCreatorName(String creatorName) 
    {
        this.creatorName = creatorName;
    }

    public String getCreatorName() 
    {
        return creatorName;
    }
    public void setCreatorNick(String creatorNick) 
    {
        this.creatorNick = creatorNick;
    }

    public String getCreatorNick() 
    {
        return creatorNick;
    }
    public void setProcessStatus(String processStatus) 
    {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() 
    {
        return processStatus;
    }
    public void setNodeName(String nodeName) 
    {
        this.nodeName = nodeName;
    }

    public String getNodeName() 
    {
        return nodeName;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
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
    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }
    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("taskType", getTaskType())
            .append("sysCode", getSysCode())
            .append("url", getUrl())
            .append("appUrl", getAppUrl())
            .append("h5Url", getH5Url())
            .append("priority", getPriority())
            .append("businessId", getBusinessId())
            .append("businessCode", getBusinessCode())
            .append("businessCategory", getBusinessCategory())
            .append("businessType", getBusinessType())
            .append("businessName", getBusinessName())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("ownerNick", getOwnerNick())
            .append("creatorId", getCreatorId())
            .append("creatorName", getCreatorName())
            .append("creatorNick", getCreatorNick())
            .append("processStatus", getProcessStatus())
            .append("nodeName", getNodeName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("expireTime", getExpireTime())
            .append("completeTime", getCompleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
