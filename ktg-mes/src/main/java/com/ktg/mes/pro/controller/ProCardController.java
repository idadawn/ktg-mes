package com.ktg.mes.pro.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
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
import com.ktg.mes.pro.domain.ProCard;
import com.ktg.mes.pro.service.IProCardService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 工序流转卡Controller
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/mes/pro/procard")
public class ProCardController extends BaseController
{
    @Autowired
    private IProCardService proCardService;

    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询工序流转卡列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProCard proCard)
    {
        startPage();
        List<ProCard> list = proCardService.selectProCardList(proCard);
        return getDataTable(list);
    }

    /**
     * 导出工序流转卡列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:export')")
    @Log(title = "工序流转卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProCard proCard)
    {
        List<ProCard> list = proCardService.selectProCardList(proCard);
        ExcelUtil<ProCard> util = new ExcelUtil<ProCard>(ProCard.class);
        util.exportExcel(response, list, "工序流转卡数据");
    }

    /**
     * 获取工序流转卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:query')")
    @GetMapping(value = "/{cardId}")
    public AjaxResult getInfo(@PathVariable("cardId") Long cardId)
    {
        return AjaxResult.success(proCardService.selectProCardByCardId(cardId));
    }

    /**
     * 新增工序流转卡
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:add')")
    @Log(title = "工序流转卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProCard proCard)
    {
        if(UserConstants.NOT_UNIQUE.equals(proCardService.checkCardCodeUnique(proCard))){
            return  AjaxResult.error("编号已存在！");
        }
        int ret = proCardService.insertProCard(proCard);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_PROCARD,proCard.getCardId(),proCard.getCardCode(),"");
        return toAjax(ret);
    }

    /**
     * 修改工序流转卡
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:edit')")
    @Log(title = "工序流转卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProCard proCard)
    {
        if(UserConstants.NOT_UNIQUE.equals(proCardService.checkCardCodeUnique(proCard))){
            return  AjaxResult.error("编号已存在！");
        }
        return toAjax(proCardService.updateProCard(proCard));
    }

    /**
     * 删除工序流转卡
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:remove')")
    @Log(title = "工序流转卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cardIds}")
    public AjaxResult remove(@PathVariable Long[] cardIds)
    {
        return toAjax(proCardService.deleteProCardByCardIds(cardIds));
    }
}
