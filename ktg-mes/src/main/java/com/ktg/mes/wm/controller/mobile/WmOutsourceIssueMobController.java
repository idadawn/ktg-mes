package com.ktg.mes.wm.controller.mobile;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmOutsourceIssue;
import com.ktg.mes.wm.domain.WmOutsourceIssueLine;
import com.ktg.mes.wm.domain.tx.OutsourceIssueTxBean;
import com.ktg.mes.wm.service.IStorageCoreService;
import com.ktg.mes.wm.service.IWmOutsourceIssueDetailService;
import com.ktg.mes.wm.service.IWmOutsourceIssueLineService;
import com.ktg.mes.wm.service.IWmOutsourceIssueService;
import com.ktg.system.strategy.AutoCodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobile/wm/outsourceissue")
public class WmOutsourceIssueMobController extends BaseController {

    @Autowired
    private IWmOutsourceIssueService wmOutsourceIssueService;

    @Autowired
    private IWmOutsourceIssueLineService wmOutsourceIssueLineService;

    @Autowired
    private IWmOutsourceIssueDetailService wmIssueDetailService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;


    /**
     * 查询外协领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceIssue wmOutsourceIssue)
    {
        startPage();
        List<WmOutsourceIssue> list = wmOutsourceIssueService.selectWmOutsourceIssueList(wmOutsourceIssue);
        return getDataTable(list);
    }

    /**
     * 获取外协领料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId)
    {
        return AjaxResult.success(wmOutsourceIssueService.selectWmOutsourceIssueByIssueId(issueId));
    }


    /**
     * 新增外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:add')")
    @Log(title = "外协领料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceIssue wmOutsourceIssue)
    {
        if(StringUtils.isNotNull(wmOutsourceIssue.getIssueCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmOutsourceIssueService.checkIssueCodeUnique(wmOutsourceIssue))){
                return  AjaxResult.error("编号已存在！");
            }
        }else {
            wmOutsourceIssue.setIssueCode(autoCodeUtil.genSerialCode(UserConstants.WM_OUTSOURCE_ISSUE_CODE,""));
        }
        wmOutsourceIssue.setCreateBy(getUsername());
        wmOutsourceIssueService.insertWmOutsourceIssue(wmOutsourceIssue);
        return AjaxResult.success(wmOutsourceIssue);
    }

    /**
     * 修改外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:edit')")
    @Log(title = "外协领料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceIssue wmOutsourceIssue)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmOutsourceIssueService.checkIssueCodeUnique(wmOutsourceIssue))){
            return  AjaxResult.error("编号已存在！");
        }
        return toAjax(wmOutsourceIssueService.updateWmOutsourceIssue(wmOutsourceIssue));
    }

    @GetMapping("/checkQuantity/{issueId}")
    public AjaxResult checkQuantity(@PathVariable("issueId") Long issueId){

        WmOutsourceIssueLine param = new WmOutsourceIssueLine();
        param.setIssueId(issueId);
        List<WmOutsourceIssueLine> lines = wmOutsourceIssueLineService.selectWmOutsourceIssueLineList(param);
        boolean flag = true;
        if(CollectionUtils.isNotEmpty(lines)){
            for(WmOutsourceIssueLine line : lines){
                if(!UserConstants.EQUAL_TO.equals(wmIssueDetailService.checkQuantity(line.getLineId()))){
                    flag = false;
                }
            }
        }
        return AjaxResult.success(flag);
    }


    /**
     * 删除外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:remove')")
    @Log(title = "外协领料单头", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds)
    {
        for (Long issueId:issueIds
        ) {
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(wmOutsourceIssueService.selectWmOutsourceIssueByIssueId(issueId).getStatus())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AjaxResult.error("只能删除草稿状态的单据！");
            }

            wmOutsourceIssueLineService.deleteWmOutsourceIssueLineByIssueId(issueId);
        }
        return toAjax(wmOutsourceIssueService.deleteWmOutsourceIssueByIssueIds(issueIds));
    }

    /**
     * 执行出库
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:edit')")
    @Log(title = "外协领料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{issueId}")
    public AjaxResult execute(@PathVariable Long issueId){
        WmOutsourceIssue header = wmOutsourceIssueService.selectWmOutsourceIssueByIssueId(issueId);
        WmOutsourceIssueLine param = new WmOutsourceIssueLine();
        param.setIssueId(issueId);
        List<WmOutsourceIssueLine> lines = wmOutsourceIssueLineService.selectWmOutsourceIssueLineList(param);
        if(CollUtil.isEmpty(lines)){
            return AjaxResult.error("请指定领出的物资");
        }

        List<OutsourceIssueTxBean> beans = wmOutsourceIssueService.getTxBeans(issueId);

        storageCoreService.processOutsourceIssue(beans);
        //更新单据状态
        header.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmOutsourceIssueService.updateWmOutsourceIssue(header);
        return AjaxResult.success();
    }


}
