package com.ktg.mes.wm.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.ktg.mes.wm.domain.WmBatch;
import com.ktg.mes.wm.service.IWmBatchService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 批次记录Controller
 * 
 * @author yinjinlu
 * @date 2025-02-24
 */
@RestController
@RequestMapping("/mes/wm/batch")
public class WmBatchController extends BaseController
{
    @Autowired
    private IWmBatchService wmBatchService;

    /**
     * 查询批次记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmBatch wmBatch)
    {
        startPage();
        List<WmBatch> list = wmBatchService.selectWmBatchList(wmBatch);
        return getDataTable(list);
    }


    /**
     * 批次追踪（向前）
     * 追踪当前批次的物资都用在哪些生产工单的哪些批次中
     */
    @GetMapping("/listForward")
    public TableDataInfo listForward(WmBatch wmBatch)
    {
        startPage();
        List<WmBatch> list = getForwardBatchList(wmBatch.getBatchCode());
        return getDataTable(list);
    }


    private List<WmBatch> getForwardBatchList(String batchCode){
        List<WmBatch> results = new ArrayList<WmBatch>();
        List<WmBatch> list = wmBatchService.selectForwardBatchList(batchCode);
        if(CollectionUtils.isNotEmpty(list)){
            results.addAll(list);
            for(WmBatch batch : list){
                results.addAll(getForwardBatchList(batch.getBatchCode()));
            }
        }
        return results;
    }

    /**
     * 批次追踪（向后）
     * 追踪当前批次的物资都来源于哪些生产工单，哪些批次的物资
     */
    @GetMapping("/listBackward")
    public TableDataInfo listBackward(WmBatch wmBatch)
    {
        startPage();
        List<WmBatch> list = getBackwardBatchList(wmBatch.getBatchCode());
        return getDataTable(list);
    }

    private List<WmBatch> getBackwardBatchList(String batchCode){
        List<WmBatch> results = new ArrayList<WmBatch>();
        List<WmBatch> list = wmBatchService.selectBackwardBatchList(batchCode);
        if(CollectionUtils.isNotEmpty(list)){
            results.addAll(list);
            for(WmBatch batch : list){
                results.addAll(getBackwardBatchList(batch.getBatchCode()));
            }
        }
        return results;
    }

    /**
     * 导出批次记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:batch:export')")
    @Log(title = "批次记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmBatch wmBatch)
    {
        List<WmBatch> list = wmBatchService.selectWmBatchList(wmBatch);
        ExcelUtil<WmBatch> util = new ExcelUtil<WmBatch>(WmBatch.class);
        util.exportExcel(response, list, "批次记录数据");
    }

    /**
     * 获取批次记录详细信息
     */
    @GetMapping(value = "/{batchId}")
    public AjaxResult getInfo(@PathVariable("batchId") Long batchId)
    {
        return AjaxResult.success(wmBatchService.selectWmBatchByBatchId(batchId));
    }

    /**
     * 新增批次记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:batch:add')")
    @Log(title = "批次记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmBatch wmBatch)
    {
        return toAjax(wmBatchService.insertWmBatch(wmBatch));
    }

    /**
     * 修改批次记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:batch:edit')")
    @Log(title = "批次记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmBatch wmBatch)
    {
        return toAjax(wmBatchService.updateWmBatch(wmBatch));
    }

    /**
     * 删除批次记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:batch:remove')")
    @Log(title = "批次记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable Long[] batchIds)
    {
        return toAjax(wmBatchService.deleteWmBatchByBatchIds(batchIds));
    }
}
