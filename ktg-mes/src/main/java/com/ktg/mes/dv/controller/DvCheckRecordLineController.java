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
import com.ktg.mes.dv.domain.DvCheckRecordLine;
import com.ktg.mes.dv.service.IDvCheckRecordLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 设备点检记录行Controller
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/mes/dv/checkrecordline")
public class DvCheckRecordLineController extends BaseController
{
    @Autowired
    private IDvCheckRecordLineService dvCheckRecordLineService;

    /**
     * 查询设备点检记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvCheckRecordLine dvCheckRecordLine)
    {
        startPage();
        List<DvCheckRecordLine> list = dvCheckRecordLineService.selectDvCheckRecordLineList(dvCheckRecordLine);
        return getDataTable(list);
    }

    /**
     * 导出设备点检记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:export')")
    @Log(title = "设备点检记录行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvCheckRecordLine dvCheckRecordLine)
    {
        List<DvCheckRecordLine> list = dvCheckRecordLineService.selectDvCheckRecordLineList(dvCheckRecordLine);
        ExcelUtil<DvCheckRecordLine> util = new ExcelUtil<DvCheckRecordLine>(DvCheckRecordLine.class);
        util.exportExcel(response, list, "设备点检记录行数据");
    }

    /**
     * 获取设备点检记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(dvCheckRecordLineService.selectDvCheckRecordLineByLineId(lineId));
    }

    /**
     * 新增设备点检记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:add')")
    @Log(title = "设备点检记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvCheckRecordLine dvCheckRecordLine)
    {
        return toAjax(dvCheckRecordLineService.insertDvCheckRecordLine(dvCheckRecordLine));
    }

    /**
     * 修改设备点检记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:edit')")
    @Log(title = "设备点检记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckRecordLine dvCheckRecordLine)
    {
        return toAjax(dvCheckRecordLineService.updateDvCheckRecordLine(dvCheckRecordLine));
    }

    /**
     * 删除设备点检记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:remove')")
    @Log(title = "设备点检记录行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(dvCheckRecordLineService.deleteDvCheckRecordLineByLineIds(lineIds));
    }



}
