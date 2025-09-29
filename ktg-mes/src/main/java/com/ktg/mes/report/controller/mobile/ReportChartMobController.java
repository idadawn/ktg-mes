package com.ktg.mes.report.controller.mobile;

import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.domain.entity.SysUser;
import com.ktg.mes.report.domain.ReportChart;
import com.ktg.mes.report.service.IReportChartService;
import com.ktg.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mobile/report/chart")
public class ReportChartMobController extends BaseController {

    @Autowired
    private IReportChartService reportChartService;

    @Autowired
    private ISysRoleService roleService;

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
}
