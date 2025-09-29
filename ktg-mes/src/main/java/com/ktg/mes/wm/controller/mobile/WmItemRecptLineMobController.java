package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api("采购入库明细")
@RestController
@RequestMapping("/mobile/wm/itemrecptline")
public class WmItemRecptLineMobController extends BaseController {

    @Autowired
    private IWmItemRecptLineService wmItemRecptLineService;

    @Autowired
    private IWmItemRecptService wmItemRecptService;

    @Autowired
    private IWmBatchService wmBatchService;

    /**
     * 查询物料入库单行列表
     */
    @ApiOperation("查询采购入库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:list')")
    @GetMapping("/list")
    public AjaxResult list(WmItemRecptLine wmItemRecptLine)
    {
        List<WmItemRecptLine> list = wmItemRecptLineService.selectWmItemRecptLineWithOnShelfList(wmItemRecptLine);
        return AjaxResult.success(list);
    }



    /**
     * 获取物料入库单行详细信息
     */
    @ApiOperation("查看采购入库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmItemRecptLineService.selectWmItemRecptLineByLineId(lineId));
    }

    /**
     * 新增物料入库单行
     */
    @ApiOperation("新增采购入库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:add')")
    @Log(title = "物料入库单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmItemRecptLine wmItemRecptLine)
    {
        //数量校验
        if(BigDecimal.ZERO.compareTo(wmItemRecptLine.getQuantityRecived()) >= 0){
            return AjaxResult.error("到货数量必须大于0!");
        }

        WmItemRecpt wmItemRecpt = wmItemRecptService.selectWmItemRecptByRecptId(wmItemRecptLine.getRecptId());

        //自动生成批次号
        WmBatch batch = new WmBatch();
        batch.setItemId(wmItemRecptLine.getItemId());
        batch.setItemCode(wmItemRecptLine.getItemCode());
        batch.setItemName(wmItemRecptLine.getItemName());
        batch.setSpecification(wmItemRecptLine.getSpecification());
        batch.setUnitOfMeasure(wmItemRecptLine.getUnitOfMeasure());
        batch.setUnitName(wmItemRecptLine.getUnitName());
        batch.setProduceDate(wmItemRecptLine.getProduceDate());
        batch.setExpireDate(wmItemRecptLine.getExpireDate());
        batch.setLotNumber(wmItemRecptLine.getLotNumber());
        batch.setPoCode(wmItemRecpt.getPoCode());
        batch.setVendorId(wmItemRecpt.getVendorId());
        batch.setVendorCode(wmItemRecpt.getVendorCode());

        WmBatch wmBatch = wmBatchService.getOrGenerateBatchCode(batch);
        if(StringUtils.isNotNull(wmBatch)){
            wmItemRecptLine.setBatchId(wmBatch.getBatchId());
            wmItemRecptLine.setBatchCode(wmBatch.getBatchCode());
        }else{
            wmItemRecptLine.setBatchId(null);
            wmItemRecptLine.setBatchCode(null);
        }
        wmItemRecptLine.setCreateBy(getUsername());
        wmItemRecptLineService.insertWmItemRecptLine(wmItemRecptLine);
        return AjaxResult.success(wmItemRecptLine);
    }

    /**
     * 修改物料入库单行
     */
    @ApiOperation("修改采购入库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemRecptLine wmItemRecptLine)
    {
        //数量校验
        if(BigDecimal.ZERO.compareTo(wmItemRecptLine.getQuantityRecived()) >= 0){
            return AjaxResult.error("到货数量必须大于0!");
        }

        WmItemRecpt wmItemRecpt = wmItemRecptService.selectWmItemRecptByRecptId(wmItemRecptLine.getRecptId());

        //自动生成批次号
        WmBatch batch = new WmBatch();
        batch.setItemId(wmItemRecptLine.getItemId());
        batch.setItemCode(wmItemRecptLine.getItemCode());
        batch.setItemName(wmItemRecptLine.getItemName());
        batch.setSpecification(wmItemRecptLine.getSpecification());
        batch.setUnitOfMeasure(wmItemRecptLine.getUnitOfMeasure());
        batch.setUnitName(wmItemRecptLine.getUnitName());
        batch.setProduceDate(wmItemRecptLine.getProduceDate());
        batch.setExpireDate(wmItemRecptLine.getExpireDate());
        batch.setLotNumber(wmItemRecptLine.getLotNumber());
        batch.setPoCode(wmItemRecpt.getPoCode());
        batch.setVendorId(wmItemRecpt.getVendorId());
        batch.setVendorCode(wmItemRecpt.getVendorCode());

        WmBatch wmBatch = wmBatchService.getOrGenerateBatchCode(batch);
        if(StringUtils.isNotNull(wmBatch)){
            wmItemRecptLine.setBatchId(wmBatch.getBatchId());
            wmItemRecptLine.setBatchCode(wmBatch.getBatchCode());
        }else{
            wmItemRecptLine.setBatchId(null);
            wmItemRecptLine.setBatchCode(null);
        }
        wmItemRecptLine.setUpdateBy(getUsername());
        wmItemRecptLineService.updateWmItemRecptLine(wmItemRecptLine);
        return AjaxResult.success(wmItemRecptLine);
    }


    /**
     * 删除物料入库单行
     */
    @ApiOperation("删除采购入库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:remove')")
    @Log(title = "物料入库单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmItemRecptLineService.deleteWmItemRecptLineByLineIds(lineIds));
    }

}
