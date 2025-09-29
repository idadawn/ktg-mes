package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
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
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;
import com.ktg.mes.wm.service.IWmArrivalNoticeLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 到货通知单行Controller
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/mes/wm/arrivalnoticeline")
public class WmArrivalNoticeLineController extends BaseController
{
    @Autowired
    private IWmArrivalNoticeLineService wmArrivalNoticeLineService;

    /**
     * 查询到货通知单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        startPage();
        List<WmArrivalNoticeLine> list = wmArrivalNoticeLineService.selectWmArrivalNoticeLineList(wmArrivalNoticeLine);
        return getDataTable(list);
    }

    /**
     * 导出到货通知单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:export')")
    @Log(title = "到货通知单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        List<WmArrivalNoticeLine> list = wmArrivalNoticeLineService.selectWmArrivalNoticeLineList(wmArrivalNoticeLine);
        ExcelUtil<WmArrivalNoticeLine> util = new ExcelUtil<WmArrivalNoticeLine>(WmArrivalNoticeLine.class);
        util.exportExcel(response, list, "到货通知单行数据");
    }

    /**
     * 获取到货通知单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmArrivalNoticeLineService.selectWmArrivalNoticeLineByLineId(lineId));
    }

    /**
     * 新增到货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:add')")
    @Log(title = "到货通知单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        if(BigDecimal.ZERO.compareTo(wmArrivalNoticeLine.getQuantityArrival()) >= 0){
            return AjaxResult.error("接收数量必须大于0！");
        }

        //如果不需要检验，则合格品数量直接=接收数量;否则合格品数量就由对应的检验单负责更新
        if(UserConstants.NO.equals(wmArrivalNoticeLine.getIqcCheck())){
            wmArrivalNoticeLine.setQuantityQuanlified(wmArrivalNoticeLine.getQuantityArrival());
        }
        return toAjax(wmArrivalNoticeLineService.insertWmArrivalNoticeLine(wmArrivalNoticeLine));
    }

    /**
     * 修改到货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:edit')")
    @Log(title = "到货通知单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        if(BigDecimal.ZERO.compareTo(wmArrivalNoticeLine.getQuantityArrival()) >= 0){
            return AjaxResult.error("接收数量必须大于0！");
        }

        //如果不需要检验，则合格品数量直接=接收数量;否则合格品数量就由对应的检验单负责更新
        if(UserConstants.NO.equals(wmArrivalNoticeLine.getIqcCheck())){
            wmArrivalNoticeLine.setQuantityQuanlified(wmArrivalNoticeLine.getQuantityArrival());
        }
        return toAjax(wmArrivalNoticeLineService.updateWmArrivalNoticeLine(wmArrivalNoticeLine));
    }

    /**
     * 删除到货通知单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:remove')")
    @Log(title = "到货通知单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmArrivalNoticeLineService.deleteWmArrivalNoticeLineByLineIds(lineIds));
    }
}
