package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.qc.domain.QcTemplate;
import com.ktg.mes.qc.service.IQcTemplateService;
import com.ktg.mes.wm.domain.WmArrivalNoticeLine;
import com.ktg.mes.wm.service.IWmArrivalNoticeLineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
import com.ktg.mes.wm.domain.WmArrivalNotice;
import com.ktg.mes.wm.service.IWmArrivalNoticeService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 到货通知单Controller
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/mes/wm/arrivalnotice")
public class WmArrivalNoticeController extends BaseController
{
    @Autowired
    private IWmArrivalNoticeService wmArrivalNoticeService;

    @Autowired
    private IWmArrivalNoticeLineService wmArrivalNoticeLineService;

    @Autowired
    private IQcTemplateService qcTemplateService;

    /**
     * 查询到货通知单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmArrivalNotice wmArrivalNotice)
    {
        startPage();
        List<WmArrivalNotice> list = wmArrivalNoticeService.selectWmArrivalNoticeList(wmArrivalNotice);
        return getDataTable(list);
    }

    /**
     * 导出到货通知单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:export')")
    @Log(title = "到货通知单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmArrivalNotice wmArrivalNotice)
    {
        List<WmArrivalNotice> list = wmArrivalNoticeService.selectWmArrivalNoticeList(wmArrivalNotice);
        ExcelUtil<WmArrivalNotice> util = new ExcelUtil<WmArrivalNotice>(WmArrivalNotice.class);
        util.exportExcel(response, list, "到货通知单数据");
    }

    /**
     * 获取到货通知单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(wmArrivalNoticeService.selectWmArrivalNoticeByNoticeId(noticeId));
    }

    /**
     * 新增到货通知单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:add')")
    @Log(title = "到货通知单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmArrivalNotice wmArrivalNotice)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmArrivalNoticeService.checkRnCodeUnique(wmArrivalNotice))){
            return AjaxResult.error("单据编号已存在");
        }

        return toAjax(wmArrivalNoticeService.insertWmArrivalNotice(wmArrivalNotice));
    }

    /**
     * 修改到货通知单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:arrivalnotice:edit')")
    @Log(title = "到货通知单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmArrivalNotice wmArrivalNotice)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmArrivalNoticeService.checkRnCodeUnique(wmArrivalNotice))){
            return AjaxResult.error("单据编号已存在");
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
        return toAjax(wmArrivalNoticeService.updateWmArrivalNotice(wmArrivalNotice));
    }

    /**
     * 删除到货通知单
     */
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
