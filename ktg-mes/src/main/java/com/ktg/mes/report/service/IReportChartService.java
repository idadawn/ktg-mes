package com.ktg.mes.report.service;

import java.util.List;
import com.ktg.mes.report.domain.ReportChart;

/**
 * 图形报组件Service接口
 * 
 * @author yinjinlu
 * @date 2025-05-12
 */
public interface IReportChartService 
{
    /**
     * 查询图形报组件
     * 
     * @param chartId 图形报组件主键
     * @return 图形报组件
     */
    public ReportChart selectReportChartByChartId(Long chartId);

    /**
     * 查询图形报组件列表
     * 
     * @param reportChart 图形报组件
     * @return 图形报组件集合
     */
    public List<ReportChart> selectReportChartList(ReportChart reportChart);

    /**
     * 根据角色ID获取权限内的图表
     * @param roleIds
     * @return
     */
    public List<ReportChart> getMyCharts(Long[] roleIds);

    /**
     * 新增图形报组件
     * 
     * @param reportChart 图形报组件
     * @return 结果
     */
    public int insertReportChart(ReportChart reportChart);

    /**
     * 修改图形报组件
     * 
     * @param reportChart 图形报组件
     * @return 结果
     */
    public int updateReportChart(ReportChart reportChart);

    /**
     * 批量删除图形报组件
     * 
     * @param chartIds 需要删除的图形报组件主键集合
     * @return 结果
     */
    public int deleteReportChartByChartIds(Long[] chartIds);

    /**
     * 删除图形报组件信息
     * 
     * @param chartId 图形报组件主键
     * @return 结果
     */
    public int deleteReportChartByChartId(Long chartId);
}
