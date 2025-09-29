package com.ktg.mes.md.controller;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.mes.md.domain.MdWorkshop;
import com.ktg.mes.md.service.IMdWorkshopService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车间Controller
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@RestController
@RequestMapping("/mes/md/workshop")
public class MdWorkshopController extends BaseController
{
    @Autowired
    private IMdWorkshopService mdWorkshopService;

    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;
    /**
     * 查询车间列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdWorkshop mdWorkshop)
    {
        startPage();
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        return getDataTable(list);
    }

    /**
     * 获取所有可用车间
     * @return
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(){
        MdWorkshop mdWorkshop = new MdWorkshop();
        mdWorkshop.setEnableFlag("Y");
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        return AjaxResult.success(list);
    }

    /**
     * 导出车间列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:export')")
    @Log(title = "车间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkshop mdWorkshop)
    {
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        ExcelUtil<MdWorkshop> util = new ExcelUtil<MdWorkshop>(MdWorkshop.class);
        util.exportExcel(response, list, "车间数据");
    }

    /**
     * 获取车间详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:query')")
    @GetMapping(value = "/{workshopId}")
    public AjaxResult getInfo(@PathVariable("workshopId") Long workshopId)
    {
        return AjaxResult.success(mdWorkshopService.selectMdWorkshopByWorkshopId(workshopId));
    }

    /**
     * 新增车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:add')")
    @Log(title = "车间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkshop mdWorkshop)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkshopService.checkWorkshopCodeUnique(mdWorkshop))){
            return AjaxResult.error("车间编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(mdWorkshopService.checkWorkshopNameUnique(mdWorkshop))){
            return AjaxResult.error("车间名称已存在！");
        }
        int i = mdWorkshopService.insertMdWorkshop(mdWorkshop);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_WORKSHOP,mdWorkshop.getWorkshopId(),mdWorkshop.getWorkshopCode(),mdWorkshop.getWorkshopName());
        return toAjax(i);
    }

    /**
     * 修改车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:edit')")
    @Log(title = "车间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkshop mdWorkshop)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkshopService.checkWorkshopCodeUnique(mdWorkshop))){
            return AjaxResult.error("车间编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(mdWorkshopService.checkWorkshopNameUnique(mdWorkshop))){
            return AjaxResult.error("车间名称已存在！");
        }
        return toAjax(mdWorkshopService.updateMdWorkshop(mdWorkshop));
    }

    /**
     * 删除车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:remove')")
    @Log(title = "车间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workshopIds}")
    public AjaxResult remove(@PathVariable Long[] workshopIds)
    {
        return toAjax(mdWorkshopService.deleteMdWorkshopByWorkshopIds(workshopIds));
    }
}
