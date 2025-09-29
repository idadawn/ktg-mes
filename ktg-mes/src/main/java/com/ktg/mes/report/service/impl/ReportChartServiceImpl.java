package com.ktg.mes.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ktg.common.core.domain.entity.SysUser;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.report.domain.ReportChartRole;
import com.ktg.mes.report.mapper.ReportChartRoleMapper;
import com.ktg.system.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.report.mapper.ReportChartMapper;
import com.ktg.mes.report.domain.ReportChart;
import com.ktg.mes.report.service.IReportChartService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图形报组件Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-05-12
 */
@Service
public class ReportChartServiceImpl implements IReportChartService 
{
    @Autowired
    private ReportChartMapper reportChartMapper;

    @Autowired
    private ReportChartRoleMapper reportChartRoleMapper;

    /**
     * 查询图形报组件
     * 
     * @param chartId 图形报组件主键
     * @return 图形报组件
     */
    @Override
    public ReportChart selectReportChartByChartId(Long chartId)
    {
        return reportChartMapper.selectReportChartByChartId(chartId);
    }

    /**
     * 查询图形报组件列表
     * 
     * @param reportChart 图形报组件
     * @return 图形报组件
     */
    @Override
    public List<ReportChart> selectReportChartList(ReportChart reportChart)
    {
        return reportChartMapper.selectReportChartList(reportChart);
    }

    @Override
    public List<ReportChart> getMyCharts(Long[] roleIds) {
        return reportChartMapper.getMyCharts(roleIds);
    }

    /**
     * 新增图形报组件
     * 
     * @param reportChart 图形报组件
     * @return 结果
     */
    @Override
    public int insertReportChart(ReportChart reportChart)
    {

        reportChart.setCreateTime(DateUtils.getNowDate());
        int ret = reportChartMapper.insertReportChart(reportChart);
        insertChartRole(reportChart);
        return ret;
    }


    /**
     * 修改图形报组件
     * 
     * @param reportChart 图形报组件
     * @return 结果
     */
    @Override
    public int updateReportChart(ReportChart reportChart)
    {
        //先删除原有角色授权信息
        reportChartRoleMapper.deleteReportChartRoleByChartId(reportChart.getChartId());
        //新增角色授权信息
        insertChartRole(reportChart);
        reportChart.setUpdateTime(DateUtils.getNowDate());
        return reportChartMapper.updateReportChart(reportChart);
    }

    /**
     * 新增图形的角色授权信息
     * @param reportChart
     */
    public void insertChartRole(ReportChart reportChart)
    {
        Long[] roles = reportChart.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {

            List<ReportChartRole> list = new ArrayList<ReportChartRole>();
            for (Long roleId : roles)
            {
                ReportChartRole ur = new ReportChartRole();
                ur.setChartId(reportChart.getChartId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                reportChartRoleMapper.batchChartRole(list);
            }
        }
    }

    /**
     * 批量删除图形报组件
     * 
     * @param chartIds 需要删除的图形报组件主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteReportChartByChartIds(Long[] chartIds)
    {
        reportChartRoleMapper.deleteReportChartRoleByChartIds(chartIds);
        return reportChartMapper.deleteReportChartByChartIds(chartIds);
    }

    /**
     * 删除图形报组件信息
     * 
     * @param chartId 图形报组件主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteReportChartByChartId(Long chartId)
    {
        reportChartRoleMapper.deleteReportChartRoleByChartId(chartId);
        return reportChartMapper.deleteReportChartByChartId(chartId);
    }
}
