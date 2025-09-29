package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 外协入库单行Controller
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/mes/wm/outsourcerecptline")
public class WmOutsourceRecptLineController extends BaseController
{

    @Autowired
    private IWmOutsourceRecptService wmOutsourceRecptService;

    @Autowired
    private IWmOutsourceRecptLineService wmOutsourceRecptLineService;

    @Autowired
    private IProWorkorderService proWorkorderService;


    @Autowired
    private IWmBatchService wmBatchService;

    /**
     * 查询外协入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceRecptLine wmOutsourceRecptLine)
    {
        startPage();
        List<WmOutsourceRecptLine> list = wmOutsourceRecptLineService.selectWmOutsourceRecptLineList(wmOutsourceRecptLine);
        return getDataTable(list);
    }

    /**
     * 查询生产退料单行列表（含明细）
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmOutsourceRecptLine wmOutsourceRecptLine)
    {
        startPage();
        List<WmOutsourceRecptLine> list = wmOutsourceRecptLineService.selectWmOutsourceRecptLineWithDetailList(wmOutsourceRecptLine);
        return getDataTable(list);
    }


    /**
     * 导出外协入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:export')")
    @Log(title = "外协入库单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmOutsourceRecptLine wmOutsourceRecptLine)
    {
        List<WmOutsourceRecptLine> list = wmOutsourceRecptLineService.selectWmOutsourceRecptLineList(wmOutsourceRecptLine);
        ExcelUtil<WmOutsourceRecptLine> util = new ExcelUtil<WmOutsourceRecptLine>(WmOutsourceRecptLine.class);
        util.exportExcel(response, list, "外协入库单行数据");
    }

    /**
     * 获取外协入库单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmOutsourceRecptLineService.selectWmOutsourceRecptLineByLineId(lineId));
    }

    /**
     * 新增外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:add')")
    @Log(title = "外协入库单行", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceRecptLine wmOutsourceRecptLine)
    {
        //数量校验
        if(BigDecimal.ZERO.compareTo(wmOutsourceRecptLine.getQuantityRecived()) >= 0){
            return AjaxResult.error("到货数量必须大于0!");
        }

        WmOutsourceRecpt wmItemRecpt =  wmOutsourceRecptService.selectWmOutsourceRecptByRecptId(wmOutsourceRecptLine.getRecptId());
        ProWorkorder proWorkorder = proWorkorderService.selectProWorkorderByWorkorderId(wmItemRecpt.getWorkorderId());
        if(proWorkorder == null){
            return AjaxResult.error("外协工单不存在!");
        }

        //如果不需要检验，质量状态设置为合格
        if(UserConstants.NO.equals(wmOutsourceRecptLine.getIqcCheck())){
            wmOutsourceRecptLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
        }else{
            wmOutsourceRecptLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
        }

        //自动生成批次号
        WmBatch batch = new WmBatch();
        batch.setItemId(wmOutsourceRecptLine.getItemId());
        batch.setItemCode(wmOutsourceRecptLine.getItemCode());
        batch.setItemName(wmOutsourceRecptLine.getItemName());
        batch.setSpecification(wmOutsourceRecptLine.getSpecification());
        batch.setUnitOfMeasure(wmOutsourceRecptLine.getUnitOfMeasure());
        batch.setUnitName(wmOutsourceRecptLine.getUnitName());
        batch.setProduceDate(wmOutsourceRecptLine.getProduceDate());
        batch.setExpireDate(wmOutsourceRecptLine.getExpireDate());
        batch.setLotNumber(wmOutsourceRecptLine.getLotNumber());
        batch.setCoCode(proWorkorder.getSourceCode());
        batch.setVendorId(wmItemRecpt.getVendorId());
        batch.setVendorCode(wmItemRecpt.getVendorCode());
        batch.setClientId(proWorkorder.getClientId());
        batch.setClientCode(proWorkorder.getClientCode());

        WmBatch wmBatch = wmBatchService.getOrGenerateBatchCode(batch);
        if(StringUtils.isNotNull(wmBatch)){
            wmOutsourceRecptLine.setBatchId(wmBatch.getBatchId());
            wmOutsourceRecptLine.setBatchCode(wmBatch.getBatchCode());
        }else{
            wmOutsourceRecptLine.setBatchId(null);
            wmOutsourceRecptLine.setBatchCode(null);
        }
        wmOutsourceRecptLine.setCreateBy(getUsername());
        wmOutsourceRecptLineService.insertWmOutsourceRecptLine(wmOutsourceRecptLine);

        return AjaxResult.success(wmOutsourceRecptLine);
    }

    /**
     * 修改外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:edit')")
    @Log(title = "外协入库单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceRecptLine wmOutsourceRecptLine)
    {
        //数量校验
        if(BigDecimal.ZERO.compareTo(wmOutsourceRecptLine.getQuantityRecived()) >= 0){
            return AjaxResult.error("到货数量必须大于0!");
        }

        WmOutsourceRecpt wmItemRecpt =  wmOutsourceRecptService.selectWmOutsourceRecptByRecptId(wmOutsourceRecptLine.getRecptId());
        ProWorkorder proWorkorder = proWorkorderService.selectProWorkorderByWorkorderId(wmItemRecpt.getWorkorderId());
        if(proWorkorder == null){
            return AjaxResult.error("外协工单不存在!");
        }

        //如果不需要检验，质量状态设置为合格
        if(UserConstants.NO.equals(wmOutsourceRecptLine.getIqcCheck())){
            wmOutsourceRecptLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
        }else{
            wmOutsourceRecptLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
        }

        //自动生成批次号
        WmBatch batch = new WmBatch();
        batch.setItemId(wmOutsourceRecptLine.getItemId());
        batch.setItemCode(wmOutsourceRecptLine.getItemCode());
        batch.setItemName(wmOutsourceRecptLine.getItemName());
        batch.setSpecification(wmOutsourceRecptLine.getSpecification());
        batch.setUnitOfMeasure(wmOutsourceRecptLine.getUnitOfMeasure());
        batch.setUnitName(wmOutsourceRecptLine.getUnitName());
        batch.setProduceDate(wmOutsourceRecptLine.getProduceDate());
        batch.setExpireDate(wmOutsourceRecptLine.getExpireDate());
        batch.setLotNumber(wmOutsourceRecptLine.getLotNumber());
        batch.setCoCode(proWorkorder.getSourceCode());
        batch.setVendorId(wmItemRecpt.getVendorId());
        batch.setVendorCode(wmItemRecpt.getVendorCode());
        batch.setClientId(proWorkorder.getClientId());
        batch.setClientCode(proWorkorder.getClientCode());

        WmBatch wmBatch = wmBatchService.getOrGenerateBatchCode(batch);
        if(StringUtils.isNotNull(wmBatch)){
            wmOutsourceRecptLine.setBatchId(wmBatch.getBatchId());
            wmOutsourceRecptLine.setBatchCode(wmBatch.getBatchCode());
        }else{
            wmOutsourceRecptLine.setBatchId(null);
            wmOutsourceRecptLine.setBatchCode(null);
        }
        wmOutsourceRecptLine.setUpdateBy(getUsername());
        return toAjax(wmOutsourceRecptLineService.updateWmOutsourceRecptLine(wmOutsourceRecptLine));
    }

    /**
     * 删除外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourcerecpt:remove')")
    @Log(title = "外协入库单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmOutsourceRecptLineService.deleteWmOutsourceRecptLineByLineIds(lineIds));
    }
}
