package com.ktg.mes.pro.controller.mobile;

import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/3/26
 */
@Api("生产工单")
@RestController
@RequestMapping("/mobile/pro/workorder")
public class ProWorkorderMobController extends BaseController {

    @Autowired
    private IProWorkorderService proWorkorderService;

    /**
     * 查询生产工单列表
     */
    @ApiOperation("生产工单列表查询接口（分页）")
    @GetMapping("/list")
    public TableDataInfo list(ProWorkorder proWorkorder)
    {
        startPage();
        List<ProWorkorder> list = proWorkorderService.selectProWorkorderList(proWorkorder);
        return getDataTable(list);
    }


    /**
     * 查询生产工单列表
     */
    @ApiOperation("生产工单列表查询接口（不分页）")
    @GetMapping("/listWithoutPage")
    public AjaxResult listWithoutPage(ProWorkorder proWorkorder)
    {
        List<ProWorkorder> list = proWorkorderService.selectProWorkorderList(proWorkorder);
        return AjaxResult.success(list);
    }

}
