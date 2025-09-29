package com.ktg.mes.report.mapper;

import java.util.List;
import com.ktg.mes.report.domain.ReportChartRole;
import com.ktg.system.domain.SysUserRole;

/**
 * 图形报角色权限Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-05-12
 */
public interface ReportChartRoleMapper 
{
    /**
     * 查询图形报角色权限
     * 
     * @param chartId 图形报角色权限主键
     * @return 图形报角色权限
     */
    public ReportChartRole selectReportChartRoleByChartId(Long chartId);

    /**
     * 查询图形报角色权限列表
     * 
     * @param reportChartRole 图形报角色权限
     * @return 图形报角色权限集合
     */
    public List<ReportChartRole> selectReportChartRoleList(ReportChartRole reportChartRole);

    /**
     * 新增图形报角色权限
     * 
     * @param reportChartRole 图形报角色权限
     * @return 结果
     */
    public int insertReportChartRole(ReportChartRole reportChartRole);


    /**
     * 批量新增图形报角色权限
     * @param reportChartRoleList
     * @return
     */
    public int batchChartRole(List<ReportChartRole> reportChartRoleList);

    /**
     * 修改图形报角色权限
     * 
     * @param reportChartRole 图形报角色权限
     * @return 结果
     */
    public int updateReportChartRole(ReportChartRole reportChartRole);

    /**
     * 删除图形报角色权限
     * 
     * @param chartId 图形报角色权限主键
     * @return 结果
     */
    public int deleteReportChartRoleByChartId(Long chartId);

    /**
     * 批量删除图形报角色权限
     * 
     * @param chartIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportChartRoleByChartIds(Long[] chartIds);
}
