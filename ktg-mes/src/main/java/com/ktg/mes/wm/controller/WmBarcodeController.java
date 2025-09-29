package com.ktg.mes.wm.controller;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.dv.service.IDvMachineryService;
import com.ktg.mes.md.service.IMdClientService;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.md.service.IMdVendorService;
import com.ktg.mes.md.service.IMdWorkstationService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmBarcode;
import com.ktg.mes.wm.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 条码清单Controller
 * 
 * @author yinjinlu
 * @date 2022-08-01
 */
@RestController
@RequestMapping("/mes/wm/barcode")
public class WmBarcodeController extends BaseController
{
    @Autowired
    private IWmBarcodeService wmBarcodeService;

    /**
     * 查询条码清单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmBarcode wmBarcode)
    {
        startPage();
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        return getDataTable(list);
    }

    /**
     * 导出条码清单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:barcode:export')")
    @Log(title = "条码清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmBarcode wmBarcode)
    {
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        ExcelUtil<WmBarcode> util = new ExcelUtil<WmBarcode>(WmBarcode.class);
        util.exportExcel(response, list, "条码清单数据");
    }

    /**
     * 获取条码清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:barcode:query')")
    @GetMapping(value = "/{barcodeId}")
    public AjaxResult getInfo(@PathVariable("barcodeId") Long barcodeId)
    {
        return AjaxResult.success(wmBarcodeService.selectWmBarcodeByBarcodeId(barcodeId));
    }

    /**
     * 新增条码清单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:barcode:add')")
    @Log(title = "条码清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmBarcode wmBarcode)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmBarcodeService.checkBarcodeUnique(wmBarcode))){
            return AjaxResult.error("当前业务内容的条码已存在!");
        }

        String path =wmBarcodeService.generateBarcode(wmBarcode);
        wmBarcode.setBarcodeUrl(path);
        return toAjax(wmBarcodeService.insertWmBarcode(wmBarcode));
    }

    /**
     * 修改条码清单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:barcode:edit')")
    @Log(title = "条码清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmBarcode wmBarcode)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmBarcodeService.checkBarcodeUnique(wmBarcode))){
            return AjaxResult.error("当前业务内容的条码已存在!");
        }
        String path =wmBarcodeService.generateBarcode(wmBarcode);
        wmBarcode.setBarcodeUrl(path);
        return toAjax(wmBarcodeService.updateWmBarcode(wmBarcode));
    }

    /**
     * 删除条码清单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:barcode:remove')")
    @Log(title = "条码清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{barcodeIds}")
    public AjaxResult remove(@PathVariable Long[] barcodeIds)
    {
        return toAjax(wmBarcodeService.deleteWmBarcodeByBarcodeIds(barcodeIds));
    }

    /**
     * 获取某个对象的二维码地址
     * @return
     */
    @GetMapping("/getBarcodeUrl")
    public AjaxResult getBarcodeUrl(WmBarcode wmBarcode){
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        if(!CollectionUtils.isEmpty(list)){
            return AjaxResult.success(list.get(0));
        }
        return AjaxResult.success();
    }

}
