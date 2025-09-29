package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdClient;
import com.ktg.mes.md.service.IMdClientService;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.RtSalesTxBean;
import com.ktg.mes.wm.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("销售退货")
@RestController
@RequestMapping("/mobile/wm/rtsales")
public class WmRtSalesMobController extends BaseController {

    @Autowired
    private IWmRtSalesService wmRtSalesService;

    @Autowired
    private IWmRtSalesLineService wmRtSalesLineService;

    @Autowired
    private IWmRtSalesDetailService wmRtSalesDetailService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private IMdClientService mdClientService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询产品销售退货单列表
     */
    @ApiOperation("查询销售退货单列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtSales wmRtSales)
    {
        startPage();
        List<WmRtSales> list = wmRtSalesService.selectWmRtSalesList(wmRtSales);
        return getDataTable(list);
    }


    /**
     * 获取产品销售退货单详细信息
     */
    @ApiOperation("获取销售退货单详细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId)
    {
        return AjaxResult.success(wmRtSalesService.selectWmRtSalesByRtId(rtId));
    }

    /**
     * 新增产品销售退货单
     */
    @ApiOperation("新增销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:add')")
    @Log(title = "产品销售退货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtSales wmRtSales)
    {
        if(StringUtils.isNotNull(wmRtSales.getRtCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmRtSalesService.checkUnique(wmRtSales))){
                return AjaxResult.error("退货单号已存在!");
            }
        }else {
            wmRtSales.setRtCode(autoCodeUtil.genSerialCode(UserConstants.RTSALSE_CODE,""));
        }

        if(StringUtils.isNotNull(wmRtSales.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmRtSales.getClientId());
            wmRtSales.setClientCode(client.getClientCode());
            wmRtSales.setClientName(client.getClientName());
            wmRtSales.setClientNick(client.getClientNick());
        }

        wmRtSales.setCreateBy(getUsername());
        wmRtSalesService.insertWmRtSales(wmRtSales);
        return AjaxResult.success(wmRtSales);
    }

    /**
     * 修改产品销售退货单
     */
    @ApiOperation("编辑销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:edit')")
    @Log(title = "产品销售退货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtSales wmRtSales)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtSalesService.checkUnique(wmRtSales))){
            return AjaxResult.error("退货单号已存在!");
        }

        if(StringUtils.isNotNull(wmRtSales.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmRtSales.getClientId());
            wmRtSales.setClientCode(client.getClientCode());
            wmRtSales.setClientName(client.getClientName());
            wmRtSales.setClientNick(client.getClientNick());
        }

        WmRtSalesLine line = new WmRtSalesLine();
        line.setRtId(wmRtSales.getRtId());
        List<WmRtSalesLine> lines = wmRtSalesLineService.selectWmRtSalesLineList(line);

        if(UserConstants.RT_SALES_STATUS_UNCHECK.equals(wmRtSales.getStatus())){
            //行校验
            if(CollectionUtils.isEmpty(lines)){
                return AjaxResult.error("请添加退货单行信息！");
            }

            //全部的行设置为未检测状态
            for(WmRtSalesLine l : lines){
                l.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
                wmRtSalesLineService.updateWmRtSalesLine(l);
            }
        }

        if(UserConstants.RT_SALES_STATUS_UNEXECUTE.equals(wmRtSales.getStatus())){
            boolean flag = true;
            StringBuilder sb = new StringBuilder();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(lines)){
                for(WmRtSalesLine l : lines){
                    if(UserConstants.LESS_THAN.equals(wmRtSalesDetailService.checkQuantity(l.getLineId()))){
                        flag = false;
                        sb.append(l.getItemName()).append(l.getBatchCode()).append("未完成上架！");
                    }
                }
            }

            if(!flag){
                return AjaxResult.error(sb.toString());
            }
        }
        return toAjax(wmRtSalesService.updateWmRtSales(wmRtSales));
    }

    /**
     * 删除产品销售退货单
     */
    @ApiOperation("删除销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:remove')")
    @Log(title = "产品销售退货单", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds)
    {
        for (Long rtId: rtIds
        ) {
            WmRtSales rtSales = wmRtSalesService.selectWmRtSalesByRtId(rtId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(rtSales.getStatus())){
                return  AjaxResult.error("只能删除草稿状态单据");
            }

            wmRtSalesLineService.deleteByRtId(rtId);
        }

        return toAjax(wmRtSalesService.deleteWmRtSalesByRtIds(rtIds));
    }

    /**
     * 执行退货
     * @param rtId
     * @return
     */
    @ApiOperation("执行销售退货接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsales:edit')")
    @Log(title = "产品销售退货单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{rtId}")
    public AjaxResult execute(@PathVariable Long rtId){
        WmRtSales rtSales = wmRtSalesService.selectWmRtSalesByRtId(rtId);
        WmRtSalesLine param = new WmRtSalesLine();
        param.setRtId(rtId);
        List<WmRtSalesLine> lines = wmRtSalesLineService.selectWmRtSalesLineList(param);
        if(CollectionUtils.isEmpty(lines)){
            return AjaxResult.error("请添加退货单行信息！");
        }

        List<RtSalesTxBean> beans = wmRtSalesService.getTxBeans(rtId);

        storageCoreService.processRtSales(beans);

        rtSales.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmRtSalesService.updateWmRtSales(rtSales);
        return AjaxResult.success();
    }
}
