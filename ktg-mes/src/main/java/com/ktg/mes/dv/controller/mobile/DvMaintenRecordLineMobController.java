package com.ktg.mes.dv.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.dv.domain.DvMaintenRecordLine;
import com.ktg.mes.dv.service.IDvMaintenRecordLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备保养记录行Controller
 *
 * @author yinjinlu
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/mobile/dv/maintenrecordline")
public class DvMaintenRecordLineMobController extends BaseController {

    @Autowired
    private IDvMaintenRecordLineService dvMaintenRecordLineService;

    /**
     * 查询设备保养记录行列表
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:list')")
    @GetMapping("/list")
    public AjaxResult list(DvMaintenRecordLine dvMaintenRecordLine) {
        List<DvMaintenRecordLine> list = dvMaintenRecordLineService.selectDvMaintenRecordLineList(dvMaintenRecordLine);
        return AjaxResult.success(list);
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
     * 修改设备保养记录行
     */
    @PreAuthorize("@ss.hasPermi('dv:maintenrecordline:edit')")
    @Log(title = "设备保养记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvMaintenRecordLine dvMaintenRecordLine)
    {
        return toAjax(dvMaintenRecordLineService.updateDvMaintenRecordLine(dvMaintenRecordLine));
    }


}
