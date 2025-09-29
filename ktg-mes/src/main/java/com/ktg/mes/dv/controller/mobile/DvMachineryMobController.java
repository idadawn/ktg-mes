package com.ktg.mes.dv.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.dv.domain.DvMachinery;
import com.ktg.mes.dv.service.IDvMachineryService;
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
@RequestMapping("/mobile/dv/machinery")
public class DvMachineryMobController extends BaseController {

    @Autowired
    private IDvMachineryService dvMachineryService;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DvMachinery dvMachinery)
    {
        startPage();
        List<DvMachinery> list = dvMachineryService.selectDvMachineryList(dvMachinery);
        return getDataTable(list);
    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvMachinery dvMachinery)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvMachineryService.checkRecptCodeUnique(dvMachinery))){
            return  AjaxResult.error("编号已存在！");
        }
        return dvMachineryService.updateDvMachinery(dvMachinery);
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:query')")
    @GetMapping(value = "/{machineryId}")
    public AjaxResult getInfo(@PathVariable("machineryId") Long machineryId)
    {
        return AjaxResult.success(dvMachineryService.selectDvMachineryByMachineryId(machineryId));
    }

}
