package com.ktg.mes.md.controller.mobile;

import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.service.IMdItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("物料信息")
@RestController
@RequestMapping("/mobile/md/item")
public class MdItemMobController extends BaseController {

    @Autowired
    private IMdItemService mdItemService;

    /**
     * 列表查询
     * @param mdItem
     * @return
     */
    @ApiOperation("查询物料清单（分页）")
    @GetMapping("/list")
    public TableDataInfo list(MdItem mdItem){
        startPage();
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }


    /**
     * 列表查询
     * @param mdItem
     * @return
     */
    @ApiOperation("查询物料清单（分页）")
    @GetMapping("/listWithoutPage")
    public AjaxResult listWithoutPage(MdItem mdItem){
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return AjaxResult.success(list);
    }


    /**
     * 主键查询
     * @param itemId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable Long itemId){
        return AjaxResult.success(mdItemService.selectMdItemById(itemId));
    }

}
