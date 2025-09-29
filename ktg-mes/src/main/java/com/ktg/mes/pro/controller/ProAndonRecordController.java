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
import com.ktg.mes.pro.domain.ProAndonRecord;
import com.ktg.mes.pro.service.IProAndonRecordService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 安灯呼叫记录Controller
 * 
 * @author yinjinlu
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/mes/pro/andonrecord")
public class ProAndonRecordController extends BaseController
{
    @Autowired
    private IProAndonRecordService proAndonRecordService;

    /**
     * 查询安灯呼叫记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProAndonRecord proAndonRecord)
    {
        startPage();
        List<ProAndonRecord> list = proAndonRecordService.selectProAndonRecordList(proAndonRecord);
        return getDataTable(list);
    }

    /**
     * 导出安灯呼叫记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:export')")
    @Log(title = "安灯呼叫记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProAndonRecord proAndonRecord)
    {
        List<ProAndonRecord> list = proAndonRecordService.selectProAndonRecordList(proAndonRecord);
        ExcelUtil<ProAndonRecord> util = new ExcelUtil<ProAndonRecord>(ProAndonRecord.class);
        util.exportExcel(response, list, "安灯呼叫记录数据");
    }

    /**
     * 获取安灯呼叫记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proAndonRecordService.selectProAndonRecordByRecordId(recordId));
    }

    /**
     * 新增安灯呼叫记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:add')")
    @Log(title = "安灯呼叫记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProAndonRecord proAndonRecord)
    {
        return toAjax(proAndonRecordService.insertProAndonRecord(proAndonRecord));
    }

    /**
     * 修改安灯呼叫记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:edit')")
    @Log(title = "安灯呼叫记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProAndonRecord proAndonRecord)
    {
        return toAjax(proAndonRecordService.updateProAndonRecord(proAndonRecord));
    }

    /**
     * 删除安灯呼叫记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:remove')")
    @Log(title = "安灯呼叫记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proAndonRecordService.deleteProAndonRecordByRecordIds(recordIds));
    }
}
