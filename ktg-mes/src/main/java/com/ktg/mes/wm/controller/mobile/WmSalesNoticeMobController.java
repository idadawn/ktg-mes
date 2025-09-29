package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmSalesNotice;
import com.ktg.mes.wm.service.IWmSalesNoticeService;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("发货通知单")
@RestController
@RequestMapping("/mobile/wm/salesnotice")
public class WmSalesNoticeMobController extends BaseController {

    @Autowired
    private IWmSalesNoticeService wmSalesNoticeService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询发货通知单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmSalesNotice wmSalesNotice)
    {
        startPage();
        List<WmSalesNotice> list = wmSalesNoticeService.selectWmSalesNoticeList(wmSalesNotice);
        return getDataTable(list);
    }

    /**
     * 获取发货通知单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(wmSalesNoticeService.selectWmSalesNoticeByNoticeId(noticeId));
    }

    /**
     * 新增发货通知单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:add')")
    @Log(title = "发货通知单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmSalesNotice wmSalesNotice)
    {
        if(StringUtils.isNotNull(wmSalesNotice.getNoticeCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmSalesNoticeService.checkCodeUnique(wmSalesNotice))){
                return AjaxResult.error("单据编号已存在");
            }
        }else{
            wmSalesNotice.setNoticeCode(autoCodeUtil.genSerialCode(UserConstants.SALES_NOTICE_CODE,""));
        }

        if(StringUtils.isNull(wmSalesNotice.getClientId())){
            return AjaxResult.error("请选择客户");
        }

        if(StringUtils.isNull(wmSalesNotice.getNoticeName())){
            wmSalesNotice.setNoticeName(wmSalesNotice.getClientName()+"发货通知单");
        }

        return toAjax(wmSalesNoticeService.insertWmSalesNotice(wmSalesNotice));
    }

    /**
     * 修改发货通知单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:edit')")
    @Log(title = "发货通知单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmSalesNotice wmSalesNotice)
    {
        if(StringUtils.isNotNull(wmSalesNotice.getNoticeCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmSalesNoticeService.checkCodeUnique(wmSalesNotice))){
                return AjaxResult.error("单据编号已存在");
            }
        }else{
            wmSalesNotice.setNoticeCode(autoCodeUtil.genSerialCode(UserConstants.SALES_NOTICE_CODE,""));
        }

        if(StringUtils.isNull(wmSalesNotice.getClientId())){
            return AjaxResult.error("请选择客户");
        }

        if(StringUtils.isNull(wmSalesNotice.getNoticeName())){
            wmSalesNotice.setNoticeName(wmSalesNotice.getClientName()+"发货通知单");
        }

        return toAjax(wmSalesNoticeService.updateWmSalesNotice(wmSalesNotice));
    }

    /**
     * 删除发货通知单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:salesnotice:remove')")
    @Log(title = "发货通知单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        return toAjax(wmSalesNoticeService.deleteWmSalesNoticeByNoticeIds(noticeIds));
    }

}
