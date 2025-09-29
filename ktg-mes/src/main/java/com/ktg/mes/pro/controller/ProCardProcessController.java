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
import com.ktg.mes.pro.domain.ProCardProcess;
import com.ktg.mes.pro.service.IProCardProcessService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 工序流转卡-工序信息Controller
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/mes/pro/procardprocess")
public class ProCardProcessController extends BaseController
{
    @Autowired
    private IProCardProcessService proCardProcessService;

    /**
     * 查询工序流转卡-工序信息列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProCardProcess proCardProcess)
    {
        startPage();
        List<ProCardProcess> list = proCardProcessService.selectProCardProcessList(proCardProcess);
        return getDataTable(list);
    }

    /**
     * 导出工序流转卡-工序信息列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:export')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProCardProcess proCardProcess)
    {
        List<ProCardProcess> list = proCardProcessService.selectProCardProcessList(proCardProcess);
        ExcelUtil<ProCardProcess> util = new ExcelUtil<ProCardProcess>(ProCardProcess.class);
        util.exportExcel(response, list, "工序流转卡-工序信息数据");
    }

    /**
     * 获取工序流转卡-工序信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proCardProcessService.selectProCardProcessByRecordId(recordId));
    }

    /**
     * 新增工序流转卡-工序信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:add')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProCardProcess proCardProcess)
    {
        return toAjax(proCardProcessService.insertProCardProcess(proCardProcess));
    }

    /**
     * 修改工序流转卡-工序信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:edit')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProCardProcess proCardProcess)
    {
        return toAjax(proCardProcessService.updateProCardProcess(proCardProcess));
    }

    /**
     * 删除工序流转卡-工序信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procardprocess:remove')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proCardProcessService.deleteProCardProcessByRecordIds(recordIds));
    }
}
