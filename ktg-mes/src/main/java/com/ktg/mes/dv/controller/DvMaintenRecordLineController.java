package com.ktg.mes.dv.controller;

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
import com.ktg.mes.dv.domain.DvMaintenRecordLine;
import com.ktg.mes.dv.service.IDvMaintenRecordLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 设备保养记录行Controller
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/mes/dv/maintenrecordline")
public class DvMaintenRecordLineController extends BaseController
{
    @Autowired
    private IDvMaintenRecordLineService dvMaintenRecordLineService;

    /**
     * 查询设备保养记录行列表
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvMaintenRecordLine dvMaintenRecordLine)
    {
        startPage();
        List<DvMaintenRecordLine> list = dvMaintenRecordLineService.selectDvMaintenRecordLineList(dvMaintenRecordLine);
        return getDataTable(list);
    }

    /**
     * 导出设备保养记录行列表
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:export')")
    @Log(title = "设备保养记录行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvMaintenRecordLine dvMaintenRecordLine)
    {
        List<DvMaintenRecordLine> list = dvMaintenRecordLineService.selectDvMaintenRecordLineList(dvMaintenRecordLine);
        ExcelUtil<DvMaintenRecordLine> util = new ExcelUtil<DvMaintenRecordLine>(DvMaintenRecordLine.class);
        util.exportExcel(response, list, "设备保养记录行数据");
    }

    /**
     * 获取设备保养记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(dvMaintenRecordLineService.selectDvMaintenRecordLineByLineId(lineId));
    }

    /**
     * 新增设备保养记录行
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:add')")
    @Log(title = "设备保养记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvMaintenRecordLine dvMaintenRecordLine)
    {
        return toAjax(dvMaintenRecordLineService.insertDvMaintenRecordLine(dvMaintenRecordLine));
    }

    /**
     * 修改设备保养记录行
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:edit')")
    @Log(title = "设备保养记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvMaintenRecordLine dvMaintenRecordLine)
    {
        return toAjax(dvMaintenRecordLineService.updateDvMaintenRecordLine(dvMaintenRecordLine));
    }

    /**
     * 删除设备保养记录行
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:remove')")
    @Log(title = "设备保养记录行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(dvMaintenRecordLineService.deleteDvMaintenRecordLineByLineIds(lineIds));
    }
}
