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
import com.ktg.mes.wm.domain.WmMaterialrequestNotice;
import com.ktg.mes.wm.service.IWmMaterialrequestNoticeService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 备料通知单Controller
 * 
 * @author yinjinlu
 * @date 2024-12-13
 */
@RestController
@RequestMapping("/mes/wm/mrnotice")
public class WmMaterialrequestNoticeController extends BaseController
{
    @Autowired
    private IWmMaterialrequestNoticeService wmMaterialrequestNoticeService;

    /**
     * 查询备料通知单列表
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        startPage();
        List<WmMaterialrequestNotice> list = wmMaterialrequestNoticeService.selectWmMaterialrequestNoticeList(wmMaterialrequestNotice);
        return getDataTable(list);
    }

    /**
     * 导出备料通知单列表
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:export')")
    @Log(title = "备料通知单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        List<WmMaterialrequestNotice> list = wmMaterialrequestNoticeService.selectWmMaterialrequestNoticeList(wmMaterialrequestNotice);
        ExcelUtil<WmMaterialrequestNotice> util = new ExcelUtil<WmMaterialrequestNotice>(WmMaterialrequestNotice.class);
        util.exportExcel(response, list, "备料通知单数据");
    }

    /**
     * 获取备料通知单详细信息
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(wmMaterialrequestNoticeService.selectWmMaterialrequestNoticeByNoticeId(noticeId));
    }

    /**
     * 新增备料通知单
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:add')")
    @Log(title = "备料通知单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        return toAjax(wmMaterialrequestNoticeService.insertWmMaterialrequestNotice(wmMaterialrequestNotice));
    }

    /**
     * 修改备料通知单
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:edit')")
    @Log(title = "备料通知单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMaterialrequestNotice wmMaterialrequestNotice)
    {
        return toAjax(wmMaterialrequestNoticeService.updateWmMaterialrequestNotice(wmMaterialrequestNotice));
    }

    /**
     * 删除备料通知单
     */
    @PreAuthorize("@ss.hasPermi('wm:mrnotice:remove')")
    @Log(title = "备料通知单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        return toAjax(wmMaterialrequestNoticeService.deleteWmMaterialrequestNoticeByNoticeIds(noticeIds));
    }
}
