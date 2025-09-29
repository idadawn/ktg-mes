package com.ktg.mes.wm.controller;

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
import com.ktg.mes.wm.domain.WmStockTakingParam;
import com.ktg.mes.wm.service.IWmStockTakingParamService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 库存盘点方案参数Controller
 * 
 * @author yinjinlu
 * @date 2025-03-31
 */
@RestController
@RequestMapping("/mes/wm/stocktakingparam")
public class WmStockTakingParamController extends BaseController
{
    @Autowired
    private IWmStockTakingParamService wmStockTakingParamService;

    /**
     * 查询库存盘点方案参数列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTakingParam wmStockTakingParam)
    {
        startPage();
        List<WmStockTakingParam> list = wmStockTakingParamService.selectWmStockTakingParamList(wmStockTakingParam);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点方案参数列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:export')")
    @Log(title = "库存盘点方案参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmStockTakingParam wmStockTakingParam)
    {
        List<WmStockTakingParam> list = wmStockTakingParamService.selectWmStockTakingParamList(wmStockTakingParam);
        ExcelUtil<WmStockTakingParam> util = new ExcelUtil<WmStockTakingParam>(WmStockTakingParam.class);
        util.exportExcel(response, list, "库存盘点方案参数数据");
    }

    /**
     * 获取库存盘点方案参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:query')")
    @GetMapping(value = "/{paramId}")
    public AjaxResult getInfo(@PathVariable("paramId") Long paramId)
    {
        return AjaxResult.success(wmStockTakingParamService.selectWmStockTakingParamByParamId(paramId));
    }

    /**
     * 新增库存盘点方案参数
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:add')")
    @Log(title = "库存盘点方案参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTakingParam wmStockTakingParam)
    {
        return toAjax(wmStockTakingParamService.insertWmStockTakingParam(wmStockTakingParam));
    }

    /**
     * 修改库存盘点方案参数
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:edit')")
    @Log(title = "库存盘点方案参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTakingParam wmStockTakingParam)
    {
        return toAjax(wmStockTakingParamService.updateWmStockTakingParam(wmStockTakingParam));
    }

    /**
     * 删除库存盘点方案参数
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingplan:remove')")
    @Log(title = "库存盘点方案参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{paramIds}")
    public AjaxResult remove(@PathVariable Long[] paramIds)
    {
        return toAjax(wmStockTakingParamService.deleteWmStockTakingParamByParamIds(paramIds));
    }
}
