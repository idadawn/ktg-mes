package com.ktg.mes.pro.controller;

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
import com.ktg.mes.pro.domain.ProSnProcess;
import com.ktg.mes.pro.service.IProSnProcessService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * SN流转-工序信息Controller
 * 
 * @author yinjinlu
 * @date 2024-11-22
 */
@RestController
@RequestMapping("/mes/pro/prosnprocess")
public class ProSnProcessController extends BaseController
{
    @Autowired
    private IProSnProcessService proSnProcessService;

    /**
     * 查询SN流转-工序信息列表
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProSnProcess proSnProcess)
    {
        startPage();
        List<ProSnProcess> list = proSnProcessService.selectProSnProcessList(proSnProcess);
        return getDataTable(list);
    }

    /**
     * 导出SN流转-工序信息列表
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:export')")
    @Log(title = "SN流转-工序信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProSnProcess proSnProcess)
    {
        List<ProSnProcess> list = proSnProcessService.selectProSnProcessList(proSnProcess);
        ExcelUtil<ProSnProcess> util = new ExcelUtil<ProSnProcess>(ProSnProcess.class);
        util.exportExcel(response, list, "SN流转-工序信息数据");
    }

    /**
     * 获取SN流转-工序信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proSnProcessService.selectProSnProcessByRecordId(recordId));
    }

    /**
     * 新增SN流转-工序信息
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:add')")
    @Log(title = "SN流转-工序信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProSnProcess proSnProcess)
    {

        return toAjax(proSnProcessService.insertProSnProcess(proSnProcess));
    }

    /**
     * 修改SN流转-工序信息
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:edit')")
    @Log(title = "SN流转-工序信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProSnProcess proSnProcess)
    {
        return toAjax(proSnProcessService.updateProSnProcess(proSnProcess));
    }

    /**
     * 删除SN流转-工序信息
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:remove')")
    @Log(title = "SN流转-工序信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proSnProcessService.deleteProSnProcessByRecordIds(recordIds));
    }
}
