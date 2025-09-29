package com.ktg.mes.pro.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.mes.qc.domain.QcDefectRecord;
import com.ktg.mes.qc.domain.ValidList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
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
import com.ktg.mes.pro.domain.ProAndonConfig;
import com.ktg.mes.pro.service.IProAndonConfigService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 安灯呼叫配置Controller
 * 
 * @author yinjinlu
 * @date 2025-04-28
 */
@RestController
@RequestMapping("/mes/pro/andonconfig")
public class ProAndonConfigController extends BaseController
{
    @Autowired
    private IProAndonConfigService proAndonConfigService;

    /**
     * 查询安灯呼叫配置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:list')")
    @GetMapping("/list")
    public AjaxResult list(ProAndonConfig proAndonConfig)
    {
        List<ProAndonConfig> list = proAndonConfigService.selectProAndonConfigList(proAndonConfig);
        return AjaxResult.success(list);
    }

    /**
     * 导出安灯呼叫配置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:export')")
    @Log(title = "安灯呼叫配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProAndonConfig proAndonConfig)
    {
        List<ProAndonConfig> list = proAndonConfigService.selectProAndonConfigList(proAndonConfig);
        ExcelUtil<ProAndonConfig> util = new ExcelUtil<ProAndonConfig>(ProAndonConfig.class);
        util.exportExcel(response, list, "安灯呼叫配置数据");
    }

    /**
     * 获取安灯呼叫配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return AjaxResult.success(proAndonConfigService.selectProAndonConfigByConfigId(configId));
    }

    /**
     * 新增安灯呼叫配置
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:add')")
    @Log(title = "安灯呼叫配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProAndonConfig proAndonConfig)
    {
        return toAjax(proAndonConfigService.insertProAndonConfig(proAndonConfig));
    }

    /**
     * 修改安灯呼叫配置
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:edit')")
    @Log(title = "安灯呼叫配置", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ValidList<ProAndonConfig> configs)
    {
        if(!CollectionUtils.isEmpty(configs)){
            for(ProAndonConfig proAndonConfig : configs){
                if(proAndonConfig.getConfigId() == null){
                    proAndonConfigService.insertProAndonConfig(proAndonConfig);
                }else{
                    proAndonConfigService.updateProAndonConfig(proAndonConfig);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 删除安灯呼叫配置
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:andonconfig:remove')")
    @Log(title = "安灯呼叫配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(proAndonConfigService.deleteProAndonConfigByConfigIds(configIds));
    }
}
