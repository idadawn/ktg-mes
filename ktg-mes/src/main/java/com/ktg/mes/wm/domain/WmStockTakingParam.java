package com.ktg.mes.wm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 库存盘点方案参数对象 wm_stock_taking_param
 * 
 * @author yinjinlu
 * @date 2025-03-31
 */
public class WmStockTakingParam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数ID */
    private Long paramId;

    /** 方案ID */
    @Excel(name = "方案ID")
    private Long planId;

    /** 条件类型 */
    @Excel(name = "条件类型")
    private String paramType;

    /** 条件值ID */
    @Excel(name = "条件值ID")
    private Long paramValueId;

    /** 条件值编码 */
    @Excel(name = "条件值编码")
    private String paramValueCode;

    /** 条件值名称 */
    @Excel(name = "条件值名称")
    private String paramValueName;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setParamId(Long paramId) 
    {
        this.paramId = paramId;
    }

    public Long getParamId() 
    {
        return paramId;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setParamType(String paramType) 
    {
        this.paramType = paramType;
    }

    public String getParamType() 
    {
        return paramType;
    }
    public void setParamValueId(Long paramValueId) 
    {
        this.paramValueId = paramValueId;
    }

    public Long getParamValueId() 
    {
        return paramValueId;
    }
    public void setParamValueCode(String paramValueCode) 
    {
        this.paramValueCode = paramValueCode;
    }

    public String getParamValueCode() 
    {
        return paramValueCode;
    }
    public void setParamValueName(String paramValueName) 
    {
        this.paramValueName = paramValueName;
    }

    public String getParamValueName() 
    {
        return paramValueName;
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
            .append("paramId", getParamId())
            .append("planId", getPlanId())
            .append("paramType", getParamType())
            .append("paramValueId", getParamValueId())
            .append("paramValueCode", getParamValueCode())
            .append("paramValueName", getParamValueName())
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
