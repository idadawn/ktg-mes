package com.ktg.mes.wm.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.mes.wm.domain.WmMaterialStock;
import com.ktg.mes.wm.domain.WmRtVendorLine;
import com.ktg.mes.wm.service.IWmMaterialStockService;
import com.ktg.mes.wm.service.IWmRtVendorLineService;
import org.apache.commons.collections.CollectionUtils;
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
import com.ktg.mes.wm.domain.WmRtVendorDetail;
import com.ktg.mes.wm.service.IWmRtVendorDetailService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 采购退货单明细Controller
 * 
 * @author yinjinlu
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/mes/wm/rtvendordetail")
public class WmRtVendorDetailController extends BaseController
{
    @Autowired
    private IWmRtVendorDetailService wmRtVendorDetailService;

    @Autowired
    private IWmRtVendorLineService wmRtVendorLineService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    /**
     * 查询采购退货单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtVendorDetail wmRtVendorDetail)
    {
        startPage();
        List<WmRtVendorDetail> list = wmRtVendorDetailService.selectWmRtVendorDetailList(wmRtVendorDetail);
        return getDataTable(list);
    }

    /**
     * 导出采购退货单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:export')")
    @Log(title = "采购退货单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtVendorDetail wmRtVendorDetail)
    {
        List<WmRtVendorDetail> list = wmRtVendorDetailService.selectWmRtVendorDetailList(wmRtVendorDetail);
        ExcelUtil<WmRtVendorDetail> util = new ExcelUtil<WmRtVendorDetail>(WmRtVendorDetail.class);
        util.exportExcel(response, list, "采购退货单明细数据");
    }

    /**
     * 获取采购退货单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return AjaxResult.success(wmRtVendorDetailService.selectWmRtVendorDetailByDetailId(detailId));
    }


    /**
     * 新增采购退货单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:add')")
    @Log(title = "采购退货单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtVendorDetail wmRtVendorDetail)
    {

        if(BigDecimal.ZERO.compareTo(wmRtVendorDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //物资验证
        WmMaterialStock materialStock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmRtVendorDetail.getMaterialStockId());
        if(materialStock == null){
            return AjaxResult.error("物资不存在，请重新选择！！");
        }else{
            WmRtVendorLine line = wmRtVendorLineService.selectWmRtVendorLineByLineId(wmRtVendorDetail.getLineId());
            if(line != null && !line.getItemCode().equals(materialStock.getItemCode())){
                return AjaxResult.error("拣货物资编码与退货单物料编码不一致，请重新选择！！");
            }
        }

        return toAjax(wmRtVendorDetailService.insertWmRtVendorDetail(wmRtVendorDetail));
    }

    /**
     * 修改采购退货单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:edit')")
    @Log(title = "采购退货单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtVendorDetail wmRtVendorDetail)
    {
        if(BigDecimal.ZERO.compareTo(wmRtVendorDetail.getQuantity()) >= 0){
            return AjaxResult.error("明细行数量必须大于0，请重新调整！！");
        }

        //物资验证
        WmMaterialStock materialStock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmRtVendorDetail.getMaterialStockId());
        if(materialStock == null){
            return AjaxResult.error("物资不存在，请重新选择！！");
        }else{
            WmRtVendorLine line = wmRtVendorLineService.selectWmRtVendorLineByLineId(wmRtVendorDetail.getLineId());
            if(line != null && !line.getItemCode().equals(materialStock.getItemCode())){
                return AjaxResult.error("拣货物资编码与退货单物料编码不一致，请重新选择！！");
            }
        }

        return toAjax(wmRtVendorDetailService.updateWmRtVendorDetail(wmRtVendorDetail));
    }

    /**
     * 删除采购退货单明细
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendordetail:remove')")
    @Log(title = "采购退货单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(wmRtVendorDetailService.deleteWmRtVendorDetailByDetailIds(detailIds));
    }
}
