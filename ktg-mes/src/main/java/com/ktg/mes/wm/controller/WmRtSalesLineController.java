package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import com.ktg.mes.wm.service.IWmWarehouseService;
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
import com.ktg.mes.wm.domain.WmRtSalesLine;
import com.ktg.mes.wm.service.IWmRtSalesLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 产品销售退货行Controller
 * 
 * @author yinjinlu
 * @date 2022-10-06
 */
@RestController
@RequestMapping("/mes/wm/rtsalesline")
public class WmRtSalesLineController extends BaseController
{
    @Autowired
    private IWmRtSalesLineService wmRtSalesLineService;

    @Autowired
    private IMdItemService mdItemService;

    /**
     * 查询产品销售退货行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtSalesLine wmRtSalesLine)
    {
        startPage();
        List<WmRtSalesLine> list = wmRtSalesLineService.selectWmRtSalesLineList(wmRtSalesLine);
        return getDataTable(list);
    }

    /**
     * 查询产品销售退货行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:list')")
    @GetMapping("/listWithDetail")
    public TableDataInfo listWithDetail(WmRtSalesLine wmRtSalesLine)
    {
        startPage();
        List<WmRtSalesLine> list = wmRtSalesLineService.selectWmRtSalesLineWithDetailList(wmRtSalesLine);
        return getDataTable(list);
    }

    /**
     * 导出产品销售退货行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:export')")
    @Log(title = "产品销售退货行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtSalesLine wmRtSalesLine)
    {
        List<WmRtSalesLine> list = wmRtSalesLineService.selectWmRtSalesLineList(wmRtSalesLine);
        ExcelUtil<WmRtSalesLine> util = new ExcelUtil<WmRtSalesLine>(WmRtSalesLine.class);
        util.exportExcel(response, list, "产品销售退货行数据");
    }

    /**
     * 获取产品销售退货行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmRtSalesLineService.selectWmRtSalesLineByLineId(lineId));
    }

    /**
     * 新增产品销售退货行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:add')")
    @Log(title = "产品销售退货行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtSalesLine wmRtSalesLine)
    {

        //物资是否批次管理校验
        MdItem mdItem = mdItemService.selectMdItemById(wmRtSalesLine.getItemId());
        if(StringUtils.isNotNull(mdItem)){
            if(UserConstants.YES.equals(mdItem.getBatchFlag()) && StringUtils.isNull(wmRtSalesLine.getBatchId()) && StringUtils.isNull(wmRtSalesLine.getBatchCode()) ){
                return AjaxResult.error("当前物资启用了批次管理，请选择批次!");
            }
        }else{
            return AjaxResult.error("物资主数据不存在!");
        }

        wmRtSalesLine.setCreateBy(getUsername());
        return toAjax(wmRtSalesLineService.insertWmRtSalesLine(wmRtSalesLine));
    }

    /**
     * 修改产品销售退货行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:edit')")
    @Log(title = "产品销售退货行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtSalesLine wmRtSalesLine)
    {
        //物资是否批次管理校验
        MdItem mdItem = mdItemService.selectMdItemById(wmRtSalesLine.getItemId());
        if(StringUtils.isNotNull(mdItem)){
            if(UserConstants.YES.equals(mdItem.getBatchFlag()) && StringUtils.isNull(wmRtSalesLine.getBatchId()) && StringUtils.isNull(wmRtSalesLine.getBatchCode()) ){
                return AjaxResult.error("当前物资启用了批次管理，请选择批次!");
            }
        }else{
            return AjaxResult.error("物资主数据不存在!");
        }
        return toAjax(wmRtSalesLineService.updateWmRtSalesLine(wmRtSalesLine));
    }

    /**
     * 删除产品销售退货行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:remove')")
    @Log(title = "产品销售退货行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmRtSalesLineService.deleteWmRtSalesLineByLineIds(lineIds));
    }
}
