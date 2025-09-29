package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.ItemRecptTxBean;
import com.ktg.mes.wm.domain.tx.MiscRecptTxBean;
import com.ktg.mes.wm.service.IStorageCoreService;
import com.ktg.mes.wm.service.IWmMiscRecptLineService;
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
import com.ktg.mes.wm.service.IWmMiscRecptService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 杂项入库单Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscrecpt")
public class WmMiscRecptController extends BaseController
{
    @Autowired
    private IWmMiscRecptService wmMiscRecptService;

    @Autowired
    private IWmMiscRecptLineService wmMiscRecptLineService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询杂项入库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscRecpt wmMiscRecpt)
    {
        startPage();
        List<WmMiscRecpt> list = wmMiscRecptService.selectWmMiscRecptList(wmMiscRecpt);
        return getDataTable(list);
    }

    /**
     * 导出杂项入库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:export')")
    @Log(title = "杂项入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscRecpt wmMiscRecpt)
    {
        List<WmMiscRecpt> list = wmMiscRecptService.selectWmMiscRecptList(wmMiscRecpt);
        ExcelUtil<WmMiscRecpt> util = new ExcelUtil<WmMiscRecpt>(WmMiscRecpt.class);
        util.exportExcel(response, list, "杂项入库单数据");
    }

    /**
     * 获取杂项入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:query')")
    @GetMapping(value = "/{recptId}")
    public AjaxResult getInfo(@PathVariable("recptId") Long recptId)
    {
        return AjaxResult.success(wmMiscRecptService.selectWmMiscRecptByRecptId(recptId));
    }

    /**
     * 新增杂项入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:add')")
    @Log(title = "杂项入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscRecpt wmMiscRecpt)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmMiscRecptService.checkRecptCodeUnique(wmMiscRecpt))){
            return  AjaxResult.error("编号已存在！");
        }
        wmMiscRecpt.setCreateBy(getUsername());
        wmMiscRecptService.insertWmMiscRecpt(wmMiscRecpt);
        return AjaxResult.success(wmMiscRecpt);
    }

    /**
     * 修改杂项入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:edit')")
    @Log(title = "杂项入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscRecpt wmMiscRecpt)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmMiscRecptService.checkRecptCodeUnique(wmMiscRecpt))){
            return  AjaxResult.error("编号已存在！");
        }

        if(UserConstants.ORDER_STATUS_APPROVED.equals(wmMiscRecpt.getStatus())){
            //检查有没有入库单行
            WmMiscRecptLine param = new WmMiscRecptLine();
            param.setRecptId(wmMiscRecpt.getRecptId());
            List<WmMiscRecptLine> lines = wmMiscRecptLineService.selectWmMiscRecptLineList(param);
            if(CollUtil.isEmpty(lines)){
                return AjaxResult.error("请添加入库单行");
            }
        }

        return toAjax(wmMiscRecptService.updateWmMiscRecpt(wmMiscRecpt));
    }

    /**
     * 执行入库
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{recptId}")
    public AjaxResult execute(@PathVariable Long recptId){

        WmMiscRecpt recpt = wmMiscRecptService.selectWmMiscRecptByRecptId(recptId);

        //构造Transaction事务，并执行库存更新逻辑
        List<MiscRecptTxBean> beans = wmMiscRecptService.getTxBeans(recptId);

        //调用库存核心
        storageCoreService.processMiscRecpt(beans);

        //更新单据状态
        recpt.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmMiscRecptService.updateWmMiscRecpt(recpt);

        return AjaxResult.success();
    }


    /**
     * 删除杂项入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscrecpt:remove')")
    @Log(title = "杂项入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recptIds}")
    public AjaxResult remove(@PathVariable Long[] recptIds)
    {
        return toAjax(wmMiscRecptService.deleteWmMiscRecptByRecptIds(recptIds));
    }
}
