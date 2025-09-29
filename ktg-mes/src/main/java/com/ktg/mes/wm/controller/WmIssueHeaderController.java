package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.IssueTxBean;
import com.ktg.mes.wm.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产领料单头Controller
 * 
 * @author yinjinlu
 * @date 2022-07-14
 */
@RestController
@RequestMapping("/mes/wm/issueheader")
public class WmIssueHeaderController extends BaseController
{
    @Autowired
    private IWmIssueHeaderService wmIssueHeaderService;

    @Autowired
    private IWmIssueLineService wmIssueLineService;

    @Autowired
    private IWmIssueDetailService wmIssueDetailService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询生产领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueHeader wmIssueHeader)
    {
        startPage();
        List<WmIssueHeader> list = wmIssueHeaderService.selectWmIssueHeaderList(wmIssueHeader);
        return getDataTable(list);
    }

    /**
     * 导出生产领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:export')")
    @Log(title = "生产领料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmIssueHeader wmIssueHeader)
    {
        List<WmIssueHeader> list = wmIssueHeaderService.selectWmIssueHeaderList(wmIssueHeader);
        ExcelUtil<WmIssueHeader> util = new ExcelUtil<WmIssueHeader>(WmIssueHeader.class);
        util.exportExcel(response, list, "生产领料单头数据");
    }

    /**
     * 获取生产领料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId)
    {
        return AjaxResult.success(wmIssueHeaderService.selectWmIssueHeaderByIssueId(issueId));
    }

    /**
     * 新增生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueHeader wmIssueHeader)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmIssueHeaderService.checkIssueCodeUnique(wmIssueHeader))){
            return AjaxResult.error("领料单编号已存在");
        }
        wmIssueHeader.setCreateBy(getUsername());
        return toAjax(wmIssueHeaderService.insertWmIssueHeader(wmIssueHeader));
    }

    /**
     * 修改生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueHeader wmIssueHeader)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmIssueHeaderService.checkIssueCodeUnique(wmIssueHeader))){
            return AjaxResult.error("领料单编号已存在");
        }
        wmIssueHeader.setUpdateBy(getUsername());

        return toAjax(wmIssueHeaderService.updateWmIssueHeader(wmIssueHeader));
    }


    @GetMapping("/checkQuantity/{issueId}")
    public AjaxResult checkQuantity(@PathVariable("issueId") Long issueId){

        WmIssueLine param = new WmIssueLine();
        param.setIssueId(issueId);
        List<WmIssueLine> lines = wmIssueLineService.selectWmIssueLineList(param);
        boolean flag = true;
        if(CollectionUtils.isNotEmpty(lines)){
            for(WmIssueLine line : lines){
                if(!UserConstants.EQUAL_TO.equals(wmIssueDetailService.checkQuantity(line.getLineId()))){
                    flag = false;
                }
            }
        }
        return AjaxResult.success(flag);
    }

    /**
     * 删除生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单头", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds)
    {
        for (long issueId:issueIds
             ) {
            WmIssueHeader header = wmIssueHeaderService.selectWmIssueHeaderByIssueId(issueId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(header.getStatus())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            wmIssueLineService.deleteByIssueHeaderId(issueId);
        }

        return toAjax(wmIssueHeaderService.deleteWmIssueHeaderByIssueIds(issueIds));
    }

    /**
     * 执行出库
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{issueId}")
    public AjaxResult execute(@PathVariable Long issueId){
        WmIssueHeader header = wmIssueHeaderService.selectWmIssueHeaderByIssueId(issueId);
        WmIssueLine param = new WmIssueLine();
        param.setIssueId(issueId);
        List<WmIssueLine> lines = wmIssueLineService.selectWmIssueLineList(param);
        if(CollUtil.isEmpty(lines)){
            return AjaxResult.error("请指定领出的物资");
        }

        List<IssueTxBean> beans = wmIssueHeaderService.getTxBeans(issueId);

        //调用库存核心
        storageCoreService.processIssue(beans);

        //更新单据状态
        header.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmIssueHeaderService.updateWmIssueHeader(header);
        return AjaxResult.success();
    }


}
