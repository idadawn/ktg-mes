package com.ktg.mes.pro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 安灯呼叫配置对象 pro_andon_config
 * 
 * @author yinjinlu
 * @date 2025-04-28
 */
public class ProAndonConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long configId;

    /** 呼叫原因 */
    @Excel(name = "呼叫原因")
    private String andonReason;

    /** 级别 */
    @Excel(name = "级别")
    private String andonLevel;

    /** 处置人角色ID */
    @Excel(name = "处置人角色ID")
    private Long handlerRoleId;

    /** 处置人角色名称 */
    @Excel(name = "处置人角色名称")
    private String handlerRoleName;

    /** 处置人 */
    @Excel(name = "处置人")
    private Long handlerUserId;

    /** 处置人用户名 */
    @Excel(name = "处置人用户名")
    private String handlerUserName;

    /** 处置人名称 */
    @Excel(name = "处置人名称")
    private String handlerNickName;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }
    public void setAndonReason(String andonReason) 
    {
        this.andonReason = andonReason;
    }

    public String getAndonReason() 
    {
        return andonReason;
    }
    public void setAndonLevel(String andonLevel) 
    {
        this.andonLevel = andonLevel;
    }

    public String getAndonLevel() 
    {
        return andonLevel;
    }
    public void setHandlerRoleId(Long handlerRoleId) 
    {
        this.handlerRoleId = handlerRoleId;
    }

    public Long getHandlerRoleId() 
    {
        return handlerRoleId;
    }
    public void setHandlerRoleName(String handlerRoleName) 
    {
        this.handlerRoleName = handlerRoleName;
    }

    public String getHandlerRoleName() 
    {
        return handlerRoleName;
    }
    public void setHandlerUserId(Long handlerUserId) 
    {
        this.handlerUserId = handlerUserId;
    }

    public Long getHandlerUserId() 
    {
        return handlerUserId;
    }
    public void setHandlerUserName(String handlerUserName) 
    {
        this.handlerUserName = handlerUserName;
    }

    public String getHandlerUserName() 
    {
        return handlerUserName;
    }
    public void setHandlerNickName(String handlerNickName) 
    {
        this.handlerNickName = handlerNickName;
    }

    public String getHandlerNickName() 
    {
        return handlerNickName;
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
            .append("configId", getConfigId())
            .append("andonReason", getAndonReason())
            .append("andonLevel", getAndonLevel())
            .append("handlerRoleId", getHandlerRoleId())
            .append("handlerRoleName", getHandlerRoleName())
            .append("handlerUserId", getHandlerUserId())
            .append("handlerUserName", getHandlerUserName())
            .append("handlerNickName", getHandlerNickName())
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
