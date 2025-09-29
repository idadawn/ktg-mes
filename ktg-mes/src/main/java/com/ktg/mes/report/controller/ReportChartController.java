package com.ktg.mes.report.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.core.domain.entity.SysRole;
import com.ktg.common.core.domain.entity.SysUser;
import com.ktg.mes.report.domain.ReportChartRole;
import com.ktg.system.service.ISysRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.report.domain.ReportChart;
import com.ktg.mes.report.service.IReportChartService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 图形报组件Controller
 * 
 * @author yinjinlu
 * @date 2025-05-12
 */
@RestController
@RequestMapping("/mes/report/chart")
public class ReportChartController extends BaseController
{
    @Autowired
    private IReportChartService reportChartService;

    @Autowired
    private ISysRoleService roleService;

    /**
     * 查询图形报组件列表
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReportChart reportChart)
    {
        startPage();
        List<ReportChart> list = reportChartService.selectReportChartList(reportChart);
        return getDataTable(list);
    }

    /**
     * 根据当前用户的角色权限查询图形报组件列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:list')")
    @GetMapping("/getMyCharts")
    public AjaxResult getMyCharts()
    {
        SysUser user = getLoginUser().getUser();
        List<Long> roleIds = roleService.selectRoleListByUserId(user.getUserId());
        if(roleIds == null || roleIds.size() == 0){
            return AjaxResult.success();
        }
        List<ReportChart> list = reportChartService.getMyCharts(roleIds.toArray(new Long[roleIds.size()]));
        return AjaxResult.success(list);
    }


    /**
     * 导出图形报组件列表
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:export')")
    @Log(title = "图形报组件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportChart reportChart)
    {
        List<ReportChart> list = reportChartService.selectReportChartList(reportChart);
        ExcelUtil<ReportChart> util = new ExcelUtil<ReportChart>(ReportChart.class);
        util.exportExcel(response, list, "图形报组件数据");
    }

    /**
     * 获取图形报组件详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:query')")
    @GetMapping(value = "/{chartId}")
    public AjaxResult getInfo(@PathVariable("chartId") Long chartId)
    {
        AjaxResult ajax = AjaxResult.success();
        ReportChart chart =  reportChartService.selectReportChartByChartId(chartId);
        ajax.put(AjaxResult.DATA_TAG, chart);
        if(chart.getRoles()!= null){
            ajax.put("roleIds", chart.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增图形报组件
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:add')")
    @Log(title = "图形报组件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportChart reportChart)
    {
        reportChartService.insertReportChart(reportChart);
        return AjaxResult.success(reportChart);
    }

    /**
     * 修改图形报组件
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:edit')")
    @Log(title = "图形报组件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportChart reportChart)
    {
        return toAjax(reportChartService.updateReportChart(reportChart));
    }

    /**
     * 删除图形报组件
     */
    @PreAuthorize("@ss.hasPermi('mes:report:chart:remove')")
    @Log(title = "图形报组件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{chartIds}")
    public AjaxResult remove(@PathVariable Long[] chartIds)
    {
        return toAjax(reportChartService.deleteReportChartByChartIds(chartIds));
    }
}
