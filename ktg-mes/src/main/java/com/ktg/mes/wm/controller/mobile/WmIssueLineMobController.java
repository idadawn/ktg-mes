package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.service.IMdProductBomService;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmIssueHeader;
import com.ktg.mes.wm.domain.WmIssueLine;
import com.ktg.mes.wm.domain.WmMaterialStock;
import com.ktg.mes.wm.service.IWmIssueHeaderService;
import com.ktg.mes.wm.service.IWmIssueLineService;
import com.ktg.mes.wm.service.IWmMaterialStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api("生产领料明细")
@RestController
@RequestMapping("/mobile/wm/issueline")
public class WmIssueLineMobController extends BaseController {

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
    @ApiOperation("查询生产领导单行信息接口（带拣货数量）")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueLine wmIssueLine)
    {
        startPage();
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineWithOffShelfList(wmIssueLine);
        return getDataTable(list);
    }


    /**
     * 获取生产领料单行详细信息
     */
    @ApiOperation("获取生产领料单行明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmIssueLineService.selectWmIssueLineByLineId(lineId));
    }

    /**
     * 新增生产领料单行
     */
    @ApiOperation("新增生产领料单行信息接口")
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
        wmIssueLineService.insertWmIssueLine(wmIssueLine);
        return AjaxResult.success(wmIssueLine);
    }

    /**
     * 修改生产领料单行
     */
    @ApiOperation("更新生产领料单行信息接口")
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

        wmIssueLine.setUpdateBy(getUsername());

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
    @ApiOperation("删除生产领料单行信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmIssueLineService.deleteWmIssueLineByLineIds(lineIds));
    }

}
