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
import com.ktg.mes.dv.domain.DvMaintenRecord;
import com.ktg.mes.dv.service.IDvMaintenRecordService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 设备保养记录Controller
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/mes/dv/maintenrecord")
public class DvMaintenRecordController extends BaseController
{
    @Autowired
    private IDvMaintenRecordService dvMaintenRecordService;

    /**
     * 查询设备保养记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvMaintenRecord dvMaintenRecord)
    {
        startPage();
        List<DvMaintenRecord> list = dvMaintenRecordService.selectDvMaintenRecordList(dvMaintenRecord);
        return getDataTable(list);
    }

    /**
     * 导出设备保养记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:export')")
    @Log(title = "设备保养记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvMaintenRecord dvMaintenRecord)
    {
        List<DvMaintenRecord> list = dvMaintenRecordService.selectDvMaintenRecordList(dvMaintenRecord);
        ExcelUtil<DvMaintenRecord> util = new ExcelUtil<DvMaintenRecord>(DvMaintenRecord.class);
        util.exportExcel(response, list, "设备保养记录数据");
    }

    /**
     * 获取设备保养记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(dvMaintenRecordService.selectDvMaintenRecordByRecordId(recordId));
    }

    /**
     * 新增设备保养记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:add')")
    @Log(title = "设备保养记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvMaintenRecord dvMaintenRecord)
    {
        return toAjax(dvMaintenRecordService.insertDvMaintenRecord(dvMaintenRecord));
    }

    /**
     * 修改设备保养记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:edit')")
    @Log(title = "设备保养记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvMaintenRecord dvMaintenRecord)
    {
        return toAjax(dvMaintenRecordService.updateDvMaintenRecord(dvMaintenRecord));
    }

    /**
     * 删除设备保养记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:maintenrecord:remove')")
    @Log(title = "设备保养记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(dvMaintenRecordService.deleteDvMaintenRecordByRecordIds(recordIds));
    }
}
