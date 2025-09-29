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
import com.ktg.mes.wm.domain.WmProductProduceDetail;
import com.ktg.mes.wm.service.IWmProductProduceDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 产品产出记录明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/mes/wm/productproducedetail")
public class WmProductProduceDetailController extends BaseController
{
    @Autowired
    private IWmProductProduceDetailService wmProductProduceDetailService;

    /**
     * 查询产品产出记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductProduceDetail wmProductProduceDetail)
    {
        startPage();
        List<WmProductProduceDetail> list = wmProductProduceDetailService.selectWmProductProduceDetailList(wmProductProduceDetail);
        return getDataTable(list);
    }

    /**
     * 导出产品产出记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:export')")
    @Log(title = "产品产出记录明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductProduceDetail wmProductProduceDetail)
    {
        List<WmProductProduceDetail> list = wmProductProduceDetailService.selectWmProductProduceDetailList(wmProductProduceDetail);
        ExcelUtil<WmProductProduceDetail> util = new ExcelUtil<WmProductProduceDetail>(WmProductProduceDetail.class);
        util.exportExcel(response, list, "产品产出记录明细数据");
    }

    /**
     * 获取产品产出记录明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmProductProduceDetailService.selectWmProductProduceDetailByDetailId(detailId));
    }

    /**
     * 新增产品产出记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:add')")
    @Log(title = "产品产出记录明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductProduceDetail wmProductProduceDetail)
    {
        return toAjax(wmProductProduceDetailService.insertWmProductProduceDetail(wmProductProduceDetail));
    }

    /**
     * 修改产品产出记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:edit')")
    @Log(title = "产品产出记录明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductProduceDetail wmProductProduceDetail)
    {
        return toAjax(wmProductProduceDetailService.updateWmProductProduceDetail(wmProductProduceDetail));
    }

    /**
     * 删除产品产出记录明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:remove')")
    @Log(title = "产品产出记录明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmProductProduceDetailService.deleteWmProductProduceDetailByDetailIds(detailIds));
    }
}
