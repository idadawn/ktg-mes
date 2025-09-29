package com.ktg.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ktg.system.domain.SysTodoList;
import com.ktg.system.service.ISysTodoListService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 通用代办Controller
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/todolist")
public class SysTodoListController extends BaseController
{
    @Autowired
    private ISysTodoListService sysTodoListService;

    /**
     * 查询通用代办列表
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTodoList sysTodoList)
    {
        startPage();
        List<SysTodoList> list = sysTodoListService.selectSysTodoListList(sysTodoList);
        return getDataTable(list);
    }

    /**
     * 导出通用代办列表
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:export')")
    @Log(title = "通用代办", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTodoList sysTodoList)
    {
        List<SysTodoList> list = sysTodoListService.selectSysTodoListList(sysTodoList);
        ExcelUtil<SysTodoList> util = new ExcelUtil<SysTodoList>(SysTodoList.class);
        util.exportExcel(response, list, "通用代办数据");
    }

    /**
     * 获取通用代办详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(sysTodoListService.selectSysTodoListByTaskId(taskId));
    }

    /**
     * 新增通用代办
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:add')")
    @Log(title = "通用代办", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTodoList sysTodoList)
    {
        getUsername();
        return toAjax(sysTodoListService.insertSysTodoList(sysTodoList));
    }

    /**
     * 修改通用代办
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:edit')")
    @Log(title = "通用代办", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTodoList sysTodoList)
    {
        return toAjax(sysTodoListService.updateSysTodoList(sysTodoList));
    }

    /**
     * 删除通用代办
     */
    @PreAuthorize("@ss.hasPermi('system:todolist:remove')")
    @Log(title = "通用代办", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(sysTodoListService.deleteSysTodoListByTaskIds(taskIds));
    }
}
