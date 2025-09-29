package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.qc.service.IQcTemplateService;
import com.ktg.mes.wm.domain.WmArrivalNotice;
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;
import com.ktg.mes.wm.service.IWmArrivalNoticeLineService;
import com.ktg.mes.wm.service.IWmArrivalNoticeService;
import com.ktg.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/3/24
 */
@Api("到货通知单")
@RestController
@RequestMapping("/mobile/wm/arrivalnotice")
public class WmArrivalNoticeMobController extends BaseController {

    @Autowired
    private IWmArrivalNoticeService wmArrivalNoticeService;

    @Autowired
    private IWmArrivalNoticeLineService wmArrivalNoticeLineService;

    @Autowired
    private IQcTemplateService qcTemplateService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询到货通知单列表
     */
    @ApiOperation("查询到货通知单列表")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmArrivalNotice wmArrivalNotice)
    {
        startPage();
        List<WmArrivalNotice> list = wmArrivalNoticeService.selectWmArrivalNoticeList(wmArrivalNotice);
        return getDataTable(list);
    }


    /**
     * 获取到货通知单详细信息
     */
    @ApiOperation("获取到货通知单详细信息")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(wmArrivalNoticeService.selectWmArrivalNoticeByNoticeId(noticeId));
    }



    /**
     * 新增到货通知单
     */
    @ApiOperation("新增到货通知单")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:add')")
    @Log(title = "到货通知单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmArrivalNotice wmArrivalNotice)
    {
        if(StringUtils.isNotNull(wmArrivalNotice.getNoticeCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmArrivalNoticeService.checkRnCodeUnique(wmArrivalNotice))){
                return AjaxResult.error("单据编号已存在");
            }
        }else{
            wmArrivalNotice.setNoticeCode(autoCodeUtil.genSerialCode(UserConstants.ARRIVAL_NOTICE_CODE,""));
        }


        if(StringUtils.isNull(wmArrivalNotice.getVendorId())){
            return AjaxResult.error("请选择供应商");
        }

        if(StringUtils.isNull(wmArrivalNotice.getNoticeName())){
            wmArrivalNotice.setNoticeName("供应商"+wmArrivalNotice.getVendorName()+"到货通知单");
        }

        wmArrivalNoticeService.insertWmArrivalNotice(wmArrivalNotice);

        return AjaxResult.success(wmArrivalNotice);
    }

    /**
     * 修改到货通知单
     */
    @ApiOperation("修改到货通知单")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:edit')")
    @Log(title = "到货通知单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmArrivalNotice wmArrivalNotice)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmArrivalNoticeService.checkRnCodeUnique(wmArrivalNotice))){
            return AjaxResult.error("单据编号已存在");
        }

        if(StringUtils.isNull(wmArrivalNotice.getVendorId())){
            return AjaxResult.error("请选择供应商");
        }

        if(StringUtils.isNull(wmArrivalNotice.getNoticeName())){
            wmArrivalNotice.setNoticeName("供应商"+wmArrivalNotice.getVendorName()+"到货通知单");
        }

        //提交时判断通知单行上的物料
        if(UserConstants.ORDER_STATUS_APPROVING.equals(wmArrivalNotice.getStatus())){
            //到货内容检查
            WmArrivalNoticeLine param = new WmArrivalNoticeLine();
            param.setNoticeId(wmArrivalNotice.getNoticeId());
            List<WmArrivalNoticeLine> lines = wmArrivalNoticeLineService.selectWmArrivalNoticeLineList(param);
            if(CollectionUtils.isEmpty(lines)){
                return AjaxResult.error("请添加到货物资！");
            }

            //这里进行判断，待收货物资是否需要检验，如果没有需要检验的则直接将状态更改为待入库
            boolean needCheck = false;
            for(WmArrivalNoticeLine line:lines){
                if(UserConstants.YES.equals(line.getIqcCheck())){
                    needCheck = true;
                    break;
                }
            }
            if(!needCheck){
                wmArrivalNotice.setStatus(UserConstants.ORDER_STATUS_APPROVED);
            }
        }

        wmArrivalNoticeService.updateWmArrivalNotice(wmArrivalNotice);

        return AjaxResult.success(wmArrivalNotice);
    }


    /**
     * 删除到货通知单
     */
    @ApiOperation("删除到货通知单")
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:remove')")
    @Log(title = "到货通知单", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        for(Long noticeId:noticeIds){

            WmArrivalNotice notice = wmArrivalNoticeService.selectWmArrivalNoticeByNoticeId(noticeId);
            if(StringUtils.isNotNull(notice) && !UserConstants.ORDER_STATUS_PREPARE.equals(notice.getStatus()) ){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }
            wmArrivalNoticeLineService.deleteByNoticeId(noticeId);
        }

        return toAjax(wmArrivalNoticeService.deleteWmArrivalNoticeByNoticeIds(noticeIds));
    }



}
