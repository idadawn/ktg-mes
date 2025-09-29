package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.service.IMdProductBomService;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmIssueHeader;
import com.ktg.mes.wm.service.IWmIssueHeaderService;
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
import com.ktg.mes.wm.domain.WmIssueLine;
import com.ktg.mes.wm.service.IWmIssueLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产领料单行Controller
 * 
 * @author yinjinlu
 * @date 2022-07-14
 */
@RestController
@RequestMapping("/mes/wm/issueline")
public class WmIssueLineController extends BaseController
{
    @Autowired
    private IWmIssueLineService wmIssueLineService;

    @Autowired
    private IWmIssueHeaderService wmIssueHeaderService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IMdProductBomService mdProductBomService;


    /**
     * 查询生产领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueLine wmIssueLine)
    {
        startPage();
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineList(wmIssueLine);
        return getDataTable(list);
    }

    /**
     * 查询生产领料单行列表（带明细行)
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmIssueLine wmIssueLine)
    {
        startPage();
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineWithDetailList(wmIssueLine);
        return getDataTable(list);
    }


    /**
     * 导出生产领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:export')")
    @Log(title = "生产领料单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmIssueLine wmIssueLine)
    {
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineList(wmIssueLine);
        ExcelUtil<WmIssueLine> util = new ExcelUtil<WmIssueLine>(WmIssueLine.class);
        util.exportExcel(response, list, "生产领料单行数据");
    }

    /**
     * 获取生产领料单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmIssueLineService.selectWmIssueLineByLineId(lineId));
    }

    /**
     * 新增生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueLine wmIssueLine)
    {
        if(!checkBom(wmIssueLine)){
            return AjaxResult.error("当前物料不在生产工单的BOM物料清单中，请检查！");
        }

        if(wmIssueLine.getQuantityIssued() == null || BigDecimal.ZERO.compareTo(wmIssueLine.getQuantityIssued()) >= 0 ){
            return AjaxResult.error("领料数量必须大于0！");
        }

        wmIssueLine.setCreateBy(getUsername());
        return toAjax(wmIssueLineService.insertWmIssueLine(wmIssueLine));
    }

    /**
     * 修改生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueLine wmIssueLine)
    {
        if(!checkBom(wmIssueLine)){
            return AjaxResult.error("当前物料不在生产工单的BOM物料清单中，请检查！");
        }

        if(wmIssueLine.getQuantityIssued() == null || BigDecimal.ZERO.compareTo(wmIssueLine.getQuantityIssued()) >= 0 ){
            return AjaxResult.error("领料数量必须大于0！");
        }

        return toAjax(wmIssueLineService.updateWmIssueLine(wmIssueLine));
    }


    private boolean checkBom(WmIssueLine wmIssueLine){
        //BOM领料验证（防错料）
        WmIssueHeader header = wmIssueHeaderService.selectWmIssueHeaderByIssueId(wmIssueLine.getIssueId());
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
     * 删除生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmIssueLineService.deleteWmIssueLineByLineIds(lineIds));
    }
}
