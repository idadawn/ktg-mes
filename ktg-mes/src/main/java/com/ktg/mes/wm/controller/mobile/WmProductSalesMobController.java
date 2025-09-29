package com.ktg.mes.wm.controller.mobile;

import cn.hutool.core.collection.CollectionUtil;
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
import com.ktg.mes.wm.domain.tx.ProductSalesTxBean;
import com.ktg.mes.wm.service.*;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api("产品销售出库")
@RestController
@RequestMapping("/mobile/wm/productsales")
public class WmProductSalesMobController extends BaseController {
    @Autowired
    private IWmProductSalesService wmProductSalesService;

    @Autowired
    private IWmProductSalesLineService wmProductSalesLineService;

    @Autowired
    private IWmProductSalesDetailService wmProductSalesDetailService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private IMdClientService mdClientService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询销售出库单列表
     */
    @ApiOperation("查询销售出库单清单接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSales wmProductSales)
    {
        startPage();
        List<WmProductSales> list = wmProductSalesService.selectWmProductSalesList(wmProductSales);
        return getDataTable(list);
    }


    /**
     * 获取销售出库单详细信息
     */
    @ApiOperation("获取销售出库单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:query')")
    @GetMapping(value = "/{salesId}")
    public AjaxResult getInfo(@PathVariable("salesId") Long salesId)
    {
        return AjaxResult.success(wmProductSalesService.selectWmProductSalesBySalesId(salesId));
    }

    @GetMapping("/checkQuantity/{salesId}")
    public AjaxResult checkQuantity(@PathVariable("salesId") Long salesId){

        WmProductSalesLine param = new WmProductSalesLine();
        param.setSalesId(salesId);
        List<WmProductSalesLine> lines = wmProductSalesLineService.selectWmProductSalesLineList(param);
        boolean flag = true;
        if(CollectionUtils.isNotEmpty(lines)){
            for(WmProductSalesLine line : lines){
                if(!UserConstants.EQUAL_TO.equals(wmProductSalesDetailService.checkQuantity(line.getLineId()))){
                    flag = false;
                }
            }
        }
        return AjaxResult.success(flag);
    }

    /**
     * 新增销售出库单
     */
    @ApiOperation("新增销售出库单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:add')")
    @Log(title = "销售出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSales wmProductSales)
    {
        if(StringUtils.isNotNull(wmProductSales.getSalesCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmProductSalesService.checkUnique(wmProductSales))){
                return AjaxResult.error("出库单编号已存在！");
            }
        }else {
            wmProductSales.setSalesCode(autoCodeUtil.genSerialCode(UserConstants.PRODUCTSALSE_CODE,""));
        }

        if(StringUtils.isNotNull(wmProductSales.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmProductSales.getClientId());
            wmProductSales.setClientCode(client.getClientCode());
            wmProductSales.setClientName(client.getClientName());
            wmProductSales.setClientNick(client.getClientNick());
        }

        wmProductSales.setCreateBy(getUsername());
        wmProductSalesService.insertWmProductSales(wmProductSales);
        return AjaxResult.success(wmProductSales);
    }

    /**
     * 修改销售出库单
     */
    @ApiOperation("编辑销售出库单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:edit')")
    @Log(title = "销售出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSales wmProductSales)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmProductSalesService.checkUnique(wmProductSales))){
            return AjaxResult.error("出库单编号已存在！");
        }

        if(StringUtils.isNotNull(wmProductSales.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmProductSales.getClientId());
            wmProductSales.setClientCode(client.getClientCode());
            wmProductSales.setClientName(client.getClientName());
            wmProductSales.setClientNick(client.getClientNick());
        }

        WmProductSalesLine param = new WmProductSalesLine();
        param.setSalesId(wmProductSales.getSalesId());
        List<WmProductSalesLine> lines = wmProductSalesLineService.selectWmProductSalesLineList(param);

        if(UserConstants.PRODUCT_SALES_STATUS_UNSTOCK.equals(wmProductSales.getStatus())){
            //检查出库单行
            if(CollectionUtil.isEmpty(lines)){
                return AjaxResult.error("出库物资不能为空");
            }
            for(WmProductSalesLine line : lines){
                if(BigDecimal.ZERO.compareTo(line.getQuantitySales())>=0){
                    return AjaxResult.error("出库数量必须大于0!");
                }
            }
        }

        //向待填写运单状态提交时，检查行是否有需要进行出货检验的，如果有。则将状态更改为待检验;如果是在UNSHIPPING状态下保存，则行的质量状态肯定不为空的。
        if(UserConstants.PRODUCT_SALES_STATUS_UNSHIPPING.equals(wmProductSales.getStatus())){
            for(WmProductSalesLine line : lines){
                if(UserConstants.YES.equals(line.getOqcCheck()) && StringUtils.isEmpty(line.getQualityStatus())){
                    wmProductSales.setStatus(UserConstants.PRODUCT_SALES_STATUS_UNCHECK);
                    line.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
                    wmProductSalesLineService.updateWmProductSalesLine(line);
                }
            }
        }

        wmProductSales.setUpdateBy(getUsername());
        return toAjax(wmProductSalesService.updateWmProductSales(wmProductSales));
    }

    /**
     * 删除销售出库单
     */
    @ApiOperation("删除销售出库单信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:remove')")
    @Log(title = "销售出库单", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{salesIds}")
    public AjaxResult remove(@PathVariable Long[] salesIds)
    {
        for (Long salesId: salesIds
        ) {
            WmProductSales sales = wmProductSalesService.selectWmProductSalesBySalesId(salesId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(sales.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            wmProductSalesLineService.deleteBySalesId(salesId);
        }
        return toAjax(wmProductSalesService.deleteWmProductSalesBySalesIds(salesIds));
    }

    /**
     * 执行出库
     * @return
     */
    @ApiOperation("执行销售出库接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productsales:edit')")
    @Log(title = "销售出库单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{salesId}")
    public AjaxResult execute(@PathVariable Long salesId){
        WmProductSales sales = wmProductSalesService.selectWmProductSalesBySalesId(salesId);

        WmProductSalesLine param = new WmProductSalesLine();
        param.setSalesId(salesId);
        List<WmProductSalesLine> lines = wmProductSalesLineService.selectWmProductSalesLineList(param);
        if(CollectionUtil.isEmpty(lines)){
            return AjaxResult.error("出库物资不能为空");
        }

        List<ProductSalesTxBean> beans = wmProductSalesService.getTxBeans(salesId);
        storageCoreService.processProductSales(beans);

        sales.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmProductSalesService.updateWmProductSales(sales);
        return AjaxResult.success();
    }
}
