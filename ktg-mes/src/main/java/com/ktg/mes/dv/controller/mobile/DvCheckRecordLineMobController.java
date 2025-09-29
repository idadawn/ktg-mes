package com.ktg.mes.dv.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.dv.domain.DvCheckRecordLine;
import com.ktg.mes.dv.service.IDvCheckRecordLineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/4/30
 */
@Api("设备台账")
@RestController
@RequestMapping("/mobile/dv/checkrecordline")
public class DvCheckRecordLineMobController extends BaseController {

    @Autowired
    private IDvCheckRecordLineService dvCheckRecordLineService;

    /**
     * 查询设备点检记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:list')")
    @GetMapping("/list")
    public AjaxResult list(DvCheckRecordLine dvCheckRecordLine)
    {
        List<DvCheckRecordLine> list = dvCheckRecordLineService.selectDvCheckRecordLineList(dvCheckRecordLine);
        return AjaxResult.success(list);
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
     * 修改设备点检记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:edit')")
    @Log(title = "设备点检记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckRecordLine dvCheckRecordLine)
    {
        return toAjax(dvCheckRecordLineService.updateDvCheckRecordLine(dvCheckRecordLine));
    }

}
