package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.service.IMdProductBomService;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmIssueHeader;
import com.ktg.mes.wm.domain.WmIssueLine;
import com.ktg.mes.wm.domain.WmOutsourceIssue;
import com.ktg.mes.wm.service.IWmOutsourceIssueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import com.ktg.mes.wm.domain.WmOutsourceIssueLine;
import com.ktg.mes.wm.service.IWmOutsourceIssueLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 外协领料单行Controller
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/mes/wm/outsourceissueline")
public class WmOutsourceIssueLineController extends BaseController
{
    @Autowired
    private IWmOutsourceIssueService wmIssueHeaderService;

    @Autowired
    private IWmOutsourceIssueLineService wmOutsourceIssueLineService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IMdProductBomService mdProductBomService;

    /**
     * 查询外协领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        startPage();
        List<WmOutsourceIssueLine> list = wmOutsourceIssueLineService.selectWmOutsourceIssueLineList(wmOutsourceIssueLine);
        return getDataTable(list);
    }

    /**
     * 查询生产领料单行列表（带明细行)
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmOutsourceIssueLine wmIssueLine)
    {
        startPage();
        List<WmOutsourceIssueLine> list = wmOutsourceIssueLineService.selectWmOutsourceIssueLineWithDetailList(wmIssueLine);
        return getDataTable(list);
    }

    /**
     * 导出外协领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:export')")
    @Log(title = "外协领料单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        List<WmOutsourceIssueLine> list = wmOutsourceIssueLineService.selectWmOutsourceIssueLineList(wmOutsourceIssueLine);
        ExcelUtil<WmOutsourceIssueLine> util = new ExcelUtil<WmOutsourceIssueLine>(WmOutsourceIssueLine.class);
        util.exportExcel(response, list, "外协领料单行数据");
    }

    /**
     * 获取外协领料单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmOutsourceIssueLineService.selectWmOutsourceIssueLineByLineId(lineId));
    }

    /**
     * 新增外协领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:add')")
    @Log(title = "外协领料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        if(checkBom(wmOutsourceIssueLine)){
            return AjaxResult.error("该物料不在BOM中，请重新选择物料");
        }

        if(BigDecimal.ZERO.compareTo(wmOutsourceIssueLine.getQuantityIssued()) >= 0){
            return AjaxResult.error("领料数量必须大于0");
        }

        return toAjax(wmOutsourceIssueLineService.insertWmOutsourceIssueLine(wmOutsourceIssueLine));
    }

    /**
     * 修改外协领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:edit')")
    @Log(title = "外协领料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        if(checkBom(wmOutsourceIssueLine)){
            return AjaxResult.error("该物料不在BOM中，请重新选择物料");
        }

        if(BigDecimal.ZERO.compareTo(wmOutsourceIssueLine.getQuantityIssued()) >= 0){
            return AjaxResult.error("领料数量必须大于0");
        }

        return toAjax(wmOutsourceIssueLineService.updateWmOutsourceIssueLine(wmOutsourceIssueLine));
    }


    private boolean checkBom(WmOutsourceIssueLine wmIssueLine){
        //BOM领料验证（防错料）
        WmOutsourceIssue header = wmIssueHeaderService.selectWmOutsourceIssueByIssueId(wmIssueLine.getIssueId());
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(header.getWorkorderId());
        MdProductBom param = new MdProductBom();
        param.setItemId(workorder.getProductId());
        List<MdProductBom> bomList = mdProductBomService.selectMdProductBomList(param);
        if(!CollectionUtils.isEmpty(bomList)){
            boolean flag = false;
            for(MdProductBom bom : bomList){
                if(bom.getBomItemId().longValue() == wmIssueLine.getItemId().longValue()){
                    flag = true;
                }
            }
            return flag;
        }
        return true;
    }

    /**
     * 删除外协领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:remove')")
    @Log(title = "外协领料单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmOutsourceIssueLineService.deleteWmOutsourceIssueLineByLineIds(lineIds));
    }
}
