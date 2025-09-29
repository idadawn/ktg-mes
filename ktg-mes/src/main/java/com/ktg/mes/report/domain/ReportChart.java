package com.ktg.mes.report.domain;

import com.ktg.common.core.domain.entity.SysRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 图形报组件对象 report_chart
 * 
 * @author yinjinlu
 * @date 2025-05-12
 */
public class ReportChart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图表ID */
    private Long chartId;

    /** 图表编号 */
    @Excel(name = "图表编号")
    private String chartCode;

    /** 图表名称 */
    @Excel(name = "图表名称")
    private String chartName;

    /** 图表类型 */
    @Excel(name = "图表类型")
    private String chartType;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String businessType;

    /** 接口地址 */
    @Excel(name = "接口地址")
    private String api;

    /** 图表options参数 */
    @Excel(name = "图表options参数")
    private String options;

    /** 缩略图地址 */
    @Excel(name = "缩略图地址")
    private String chartPic;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String enableFlag;

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

    /** 角色组 */
    private Long[] roleIds;

    private List<SysRole> roles;

    public void setChartId(Long chartId) 
    {
        this.chartId = chartId;
    }

    public Long getChartId() 
    {
        return chartId;
    }
    public void setChartCode(String chartCode) 
    {
        this.chartCode = chartCode;
    }

    public String getChartCode() 
    {
        return chartCode;
    }
    public void setChartName(String chartName) 
    {
        this.chartName = chartName;
    }

    public String getChartName() 
    {
        return chartName;
    }
    public void setChartType(String chartType) 
    {
        this.chartType = chartType;
    }

    public String getChartType() 
    {
        return chartType;
    }
    public void setBusinessType(String businessType) 
    {
        this.businessType = businessType;
    }

    public String getBusinessType() 
    {
        return businessType;
    }
    public void setApi(String api) 
    {
        this.api = api;
    }

    public String getApi() 
    {
        return api;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setChartPic(String chartPic)
    {
        this.chartPic = chartPic;
    }

    public String getChartPic() 
    {
        return chartPic;
    }
    public void setEnableFlag(String enableFlag) 
    {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() 
    {
        return enableFlag;
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

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("chartId", getChartId())
            .append("chartCode", getChartCode())
            .append("chartName", getChartName())
            .append("chartType", getChartType())
            .append("businessType", getBusinessType())
            .append("api", getApi())
            .append("chartPic", getChartPic())
            .append("enableFlag", getEnableFlag())
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
