package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.service.*;
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
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产退料单行Controller
 * 
 * @author yinjinlu
 * @date 2022-09-15
 */
@RestController
@RequestMapping("/mes/wm/rtissueline")
public class WmRtIssueLineController extends BaseController
{
    @Autowired
    private IWmRtIssueService wmRtIssueService;

    @Autowired
    private IWmRtIssueLineService wmRtIssueLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询生产退料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtIssueLine wmRtIssueLine)
    {
        startPage();
        List<WmRtIssueLine> list = wmRtIssueLineService.selectWmRtIssueLineList(wmRtIssueLine);
        return getDataTable(list);
    }

    /**
     * 查询生产退料单行列表（含明细）
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmRtIssueLine wmRtIssueLine)
    {
        startPage();
        List<WmRtIssueLine> list = wmRtIssueLineService.selectWmRtIssueLineWithDetailList(wmRtIssueLine);
        return getDataTable(list);
    }

    /**
     * 导出生产退料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:export')")
    @Log(title = "生产退料单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtIssueLine wmRtIssueLine)
    {
        List<WmRtIssueLine> list = wmRtIssueLineService.selectWmRtIssueLineList(wmRtIssueLine);
        ExcelUtil<WmRtIssueLine> util = new ExcelUtil<WmRtIssueLine>(WmRtIssueLine.class);
        util.exportExcel(response, list, "生产退料单行数据");
    }

    /**
     * 获取生产退料单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmRtIssueLineService.selectWmRtIssueLineByLineId(lineId));
    }

    /**
     * 新增生产退料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:add')")
    @Log(title = "生产退料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtIssueLine wmRtIssueLine)
    {
        //根据头上的退料类型设置行上物料的质量状态
        WmRtIssue issue = wmRtIssueService.selectWmRtIssueByRtId(wmRtIssueLine.getRtId());

        //如果是余料退料，则行上物资的质量状态为合格；如果是废料/坏料退料，则行上物资的质量状态为不合格
        if(UserConstants.YES.equals(wmRtIssueLine.getQcFlag())){
            wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
        }else{
            if(UserConstants.RTISSUE_TYPE_RMR.equals(issue.getRtType())){
                wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
            }else{
                wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
            }
        }

        wmRtIssueLine.setCreateBy(getUsername());
        return toAjax(wmRtIssueLineService.insertWmRtIssueLine(wmRtIssueLine));
    }

    /**
     * 修改生产退料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtIssueLine wmRtIssueLine)
    {
        //根据头上的退料类型设置行上物料的质量状态
        WmRtIssue issue = wmRtIssueService.selectWmRtIssueByRtId(wmRtIssueLine.getRtId());

        //如果是余料退料，则行上物资的质量状态为合格；如果是废料/坏料退料，则行上物资的质量状态为不合格
        if(UserConstants.YES.equals(wmRtIssueLine.getQcFlag())){
            wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
        }else{
            if(UserConstants.RTISSUE_TYPE_RMR.equals(issue.getRtType())){
                wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
            }else{
                wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
            }
        }

        return toAjax(wmRtIssueLineService.updateWmRtIssueLine(wmRtIssueLine));
    }

    /**
     * 删除生产退料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:remove')")
    @Log(title = "生产退料单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmRtIssueLineService.deleteWmRtIssueLineByLineIds(lineIds));
    }
}
