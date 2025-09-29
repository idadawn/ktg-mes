package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;
import com.ktg.mes.wm.service.IWmArrivalNoticeLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/3/24
 */
@Api("到货通知单行")
@RestController
@RequestMapping("/mobile/wm/arrivalnoticeline")
public class WmArrivalNoticeLineMobController extends BaseController {

    @Autowired
    private IWmArrivalNoticeLineService wmArrivalNoticeLineService;

    /**
     * 查询到货通知单行列表
     */
    @ApiOperation("查询到货通知单行列表")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:list')")
    @GetMapping("/list")
    public AjaxResult list(WmArrivalNoticeLine wmArrivalNoticeLine)
    {
        List<WmArrivalNoticeLine> list = wmArrivalNoticeLineService.selectWmArrivalNoticeLineList(wmArrivalNoticeLine);
        return AjaxResult.success(list);
    }

    /**
     * 获取到货通知单行详细信息
     */
    @ApiOperation("获取到货通知单行详细信息")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmArrivalNoticeLineService.selectWmArrivalNoticeLineByLineId(lineId));
    }

    /**
     * 新增到货通知单行
     */
    @ApiOperation("新增到货通知单行")
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

        wmArrivalNoticeLineService.insertWmArrivalNoticeLine(wmArrivalNoticeLine);

        return AjaxResult.success(wmArrivalNoticeLine);
    }

    /**
     * 修改到货通知单行
     */
    @ApiOperation("修改到货通知单行")
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

        wmArrivalNoticeLineService.updateWmArrivalNoticeLine(wmArrivalNoticeLine);
        return AjaxResult.success(wmArrivalNoticeLine);
    }

    /**
     * 删除到货通知单行
     */
    @ApiOperation("删除到货通知单行")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:remove')")
    @Log(title = "到货通知单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmArrivalNoticeLineService.deleteWmArrivalNoticeLineByLineIds(lineIds));
    }
}
