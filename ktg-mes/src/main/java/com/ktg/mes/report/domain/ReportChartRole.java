package com.ktg.mes.report.domain;

public class ReportChartRole {

    private Long chartId;
    private Long roleId;

    public Long getChartId() {
        return chartId;
    }
    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "ReportChartRole{" +
                "chartId=" + chartId +
                ", roleId=" + roleId +
                '}';
    }
}
