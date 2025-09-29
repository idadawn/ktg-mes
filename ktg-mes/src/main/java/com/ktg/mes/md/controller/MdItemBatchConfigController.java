package com.ktg.mes.md.controller;

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
import com.ktg.mes.md.domain.MdItemBatchConfig;
import com.ktg.mes.md.service.IMdItemBatchConfigService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 物料批次属性配置Controller
 * 
 * @author yinjinlu
 * @date 2025-02-05
 */
@RestController
@RequestMapping("/mes/md/batchconfig")
public class MdItemBatchConfigController extends BaseController
{
    @Autowired
    private IMdItemBatchConfigService mdItemBatchConfigService;

    /**
     * 查询物料批次属性配置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdItemBatchConfig mdItemBatchConfig)
    {
        startPage();
        List<MdItemBatchConfig> list = mdItemBatchConfigService.selectMdItemBatchConfigList(mdItemBatchConfig);
        return getDataTable(list);
    }

    /**
     * 导出物料批次属性配置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:export')")
    @Log(title = "物料批次属性配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdItemBatchConfig mdItemBatchConfig)
    {
        List<MdItemBatchConfig> list = mdItemBatchConfigService.selectMdItemBatchConfigList(mdItemBatchConfig);
        ExcelUtil<MdItemBatchConfig> util = new ExcelUtil<MdItemBatchConfig>(MdItemBatchConfig.class);
        util.exportExcel(response, list, "物料批次属性配置数据");
    }

    /**
     * 获取物料批次属性配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        MdItemBatchConfig mdItemBatchConfig = new MdItemBatchConfig();
        mdItemBatchConfig.setItemId(itemId);
        List<MdItemBatchConfig> list = mdItemBatchConfigService.selectMdItemBatchConfigList(mdItemBatchConfig);
        if(!CollectionUtils.isEmpty(list)){
            return AjaxResult.success(list.get(0));
        }
        return AjaxResult.success();
    }

    /**
     * 新增物料批次属性配置
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:add')")
    @Log(title = "物料批次属性配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdItemBatchConfig mdItemBatchConfig)
    {
        return toAjax(mdItemBatchConfigService.insertMdItemBatchConfig(mdItemBatchConfig));
    }

    /**
     * 修改物料批次属性配置
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:edit')")
    @Log(title = "物料批次属性配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdItemBatchConfig mdItemBatchConfig)
    {
        return toAjax(mdItemBatchConfigService.updateMdItemBatchConfig(mdItemBatchConfig));
    }

    /**
     * 删除物料批次属性配置
     */
    @PreAuthorize("@ss.hasPermi('mes:md:batchconfig:remove')")
    @Log(title = "物料批次属性配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(mdItemBatchConfigService.deleteMdItemBatchConfigByConfigIds(configIds));
    }
}
