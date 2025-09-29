package com.ktg.mes.pro.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.pro.domain.ProAndonConfig;
import com.ktg.mes.pro.domain.ProAndonRecord;
import com.ktg.mes.pro.service.IProAndonConfigService;
import com.ktg.mes.pro.service.IProAndonRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/04/27
 */
@Api
@RestController
@RequestMapping("/mobile/pro/andonrecord")
public class ProAndonRecordMobController extends BaseController {

    @Autowired
    private IProAndonRecordService proAndonRecordService;

    @Autowired
    private IProAndonConfigService proAndonConfigService;

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
     * 查询安灯呼叫配置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:list')")
    @GetMapping("/listReasons")
    public AjaxResult listReasons()
    {
        List<ProAndonConfig> list = proAndonConfigService.selectProAndonConfigList(new ProAndonConfig());
        return AjaxResult.success(list);
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
        proAndonRecord.setCreateBy(getUsername());
        proAndonRecordService.insertProAndonRecord(proAndonRecord);
        return AjaxResult.success(proAndonRecord);
    }

    /**
     * 修改安灯呼叫记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonrecord:edit')")
    @Log(title = "安灯呼叫记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProAndonRecord proAndonRecord)
    {
        proAndonRecord.setUpdateBy(getUsername());
        return toAjax(proAndonRecordService.updateProAndonRecord(proAndonRecord));
    }

}
