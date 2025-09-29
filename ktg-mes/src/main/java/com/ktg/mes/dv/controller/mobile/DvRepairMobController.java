package com.ktg.mes.dv.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.dv.domain.DvRepair;
import com.ktg.mes.dv.service.IDvRepairService;
import com.ktg.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备维修单Controller
 *
 * @author yinjinlu
 * @date 2025-05-06
 */
@RestController
@RequestMapping("/mobile/dv/repair")
public class DvRepairMobController extends BaseController {

    @Autowired
    private IDvRepairService dvRepairService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询设备维修单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvRepair dvRepair)
    {
        startPage();
        List<DvRepair> list = dvRepairService.selectDvRepairList(dvRepair);
        return getDataTable(list);
    }


    /**
     * 获取设备维修单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId)
    {
        return AjaxResult.success(dvRepairService.selectDvRepairByRepairId(repairId));
    }

    /**
     * 新增设备维修单
     */
    @PreAuthorize("@ss.hasPermi('dv:repair:add')")
    @Log(title = "设备维修单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvRepair dvRepair)
    {
        if(StringUtils.isNotNull(dvRepair.getRepairCode())){
            if(UserConstants.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(dvRepair))){
                return AjaxResult.error("维修单编号已存！");
            }
        } else{
            dvRepair.setRepairCode(autoCodeUtil.genSerialCode(UserConstants.DV_REPAIR_CODE,""));
        }

        //根据sourceDocType和sourceDocId 生成对应的维修单项目

        dvRepairService.insertDvRepair(dvRepair);
        return AjaxResult.success(dvRepair);
    }

    /**
     * 修改设备维修单
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:edit')")
    @Log(title = "设备维修单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvRepair dvRepair)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(dvRepair))){
            return AjaxResult.error("维修单编号已存！");
        }
        return toAjax(dvRepairService.updateDvRepair(dvRepair));
    }

    /**
     * 删除设备维修单
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:remove')")
    @Log(title = "设备维修单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds)
    {
        return toAjax(dvRepairService.deleteDvRepairByRepairIds(repairIds));
    }


}
