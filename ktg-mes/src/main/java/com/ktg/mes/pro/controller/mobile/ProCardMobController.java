package com.ktg.mes.pro.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.pro.domain.ProCard;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProCardService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/11/18
 */
@Api
@RestController
@RequestMapping("/mobile/pro/procard")
public class ProCardMobController extends BaseController {

    @Autowired
    private IProCardService proCardService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询工序流转卡列表
     */
    @ApiOperation("查询工序流转卡清单接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/list")
    public AjaxResult list(ProCard proCard)
    {
        List<ProCard> list = proCardService.selectProCardList(proCard);
        return AjaxResult.success(list);
    }

    /**
     * 查询流经某个工作站的所有流转卡
     * 查询条件：工作站、生产工单
     * @return
     */
    @ApiOperation("查询某个工作站下指定工单的所有流转卡接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/getStationList")
    public AjaxResult getStationList(ProCard card){
        if(!StringUtils.isNotNull(card.getWorkorderId())){
            return AjaxResult.error("请传递生产工单ID参数");
        }

        if(!StringUtils.isNotNull(card.getWorkstationId())){
            return AjaxResult.error("请传递工作站ID参数");
        }

        List<ProCard> list = proCardService.getStationList(card);
        return AjaxResult.success(list);
    }

    /**
     * 获取工序流转卡详细信息
     */
    @ApiOperation("根据流转卡编号查询流转卡详细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:query')")
    @GetMapping(value = "/{cardCode}")
    public AjaxResult getInfo(@PathVariable("cardCode") String cardCode)
    {
        ProCard param = new ProCard();
        param.setCardCode(cardCode);
        List<ProCard> cards = proCardService.selectProCardList(param);
        if(!CollectionUtils.isEmpty(cards)){
            return AjaxResult.success(cards.get(0));
        }
        return AjaxResult.error("未查询到对应的流转卡！");
    }

    /**
     * 新增工序流转卡
     */
    @ApiOperation("新增流转卡接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:add')")
    @Log(title = "工序流转卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProCard proCard)
    {
        if(!StringUtils.isNotNull(proCard.getWorkorderId())){
            return AjaxResult.error("请传递生产工单参数!");
        }

        if(!StringUtils.isNotNull(proCard.getQuantityTransfered())){
            return AjaxResult.error("请传递流转数量参数!");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(proCard.getWorkorderId());
        if(!StringUtils.isNotNull(workorder)){
            return AjaxResult.error("生产工单无效!");
        }

        proCard.setCardCode(autoCodeUtil.genSerialCode(UserConstants.CARD_CODE,null));
        proCard.setWorkorderCode(workorder.getWorkorderCode());
        proCard.setWorkorderName(workorder.getWorkorderName());
        proCard.setItemId(workorder.getProductId());
        proCard.setItemCode(workorder.getProductCode());
        proCard.setItemName(workorder.getProductName());
        proCard.setSpecification(workorder.getProductSpc());
        proCard.setUnitOfMeasure(workorder.getUnitOfMeasure());
        proCard.setBatchCode(workorder.getBatchCode());
        proCard.setStatus(UserConstants.ORDER_STATUS_CONFIRMED);
        proCardService.insertProCard(proCard);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_PROCARD,proCard.getCardId(),proCard.getCardCode(),"");
        return AjaxResult.success(proCard);
    }
}
