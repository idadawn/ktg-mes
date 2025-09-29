package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.mes.wm.domain.WmIssueHeader;
import com.ktg.mes.wm.domain.WmIssueLine;
import com.ktg.mes.wm.domain.WmMiscIssueLine;
import com.ktg.mes.wm.domain.tx.IssueTxBean;
import com.ktg.mes.wm.domain.tx.MiscIssueTxBean;
import com.ktg.mes.wm.service.IStorageCoreService;
import com.ktg.mes.wm.service.IWmMiscIssueLineService;
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
import com.ktg.mes.wm.domain.WmMiscIssue;
import com.ktg.mes.wm.service.IWmMiscIssueService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 杂项出库单Controller
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/mes/wm/miscissue")
public class WmMiscIssueController extends BaseController
{
    @Autowired
    private IWmMiscIssueService wmMiscIssueService;

    @Autowired
    private IWmMiscIssueLineService wmMiscIssueLineService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询杂项出库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMiscIssue wmMiscIssue)
    {
        startPage();
        List<WmMiscIssue> list = wmMiscIssueService.selectWmMiscIssueList(wmMiscIssue);
        return getDataTable(list);
    }

    /**
     * 导出杂项出库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:export')")
    @Log(title = "杂项出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMiscIssue wmMiscIssue)
    {
        List<WmMiscIssue> list = wmMiscIssueService.selectWmMiscIssueList(wmMiscIssue);
        ExcelUtil<WmMiscIssue> util = new ExcelUtil<WmMiscIssue>(WmMiscIssue.class);
        util.exportExcel(response, list, "杂项出库单数据");
    }

    /**
     * 获取杂项出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId)
    {
        return AjaxResult.success(wmMiscIssueService.selectWmMiscIssueByIssueId(issueId));
    }

    /**
     * 新增杂项出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:add')")
    @Log(title = "杂项出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMiscIssue wmMiscIssue)
    {
        if (UserConstants.NOT_UNIQUE.equals(wmMiscIssueService.checkUnique(wmMiscIssue))){
            return AjaxResult.error("编号已存在，请重新输入！");
        }

        wmMiscIssue.setCreateBy(getUsername());
        wmMiscIssueService.insertWmMiscIssue(wmMiscIssue);
        return AjaxResult.success(wmMiscIssue);
    }

    /**
     * 修改杂项出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:edit')")
    @Log(title = "杂项出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMiscIssue wmMiscIssue)
    {
        if (UserConstants.NOT_UNIQUE.equals(wmMiscIssueService.checkUnique(wmMiscIssue))){
            return AjaxResult.error("编号已存在，请重新输入！");
        }

        if(UserConstants.ORDER_STATUS_APPROVED.equals(wmMiscIssue.getStatus())){
            WmMiscIssueLine param = new WmMiscIssueLine();
            param.setIssueId(wmMiscIssue.getIssueId());
            List<WmMiscIssueLine> lineList = wmMiscIssueLineService.selectWmMiscIssueLineList(param);
            if(CollectionUtils.isEmpty(lineList)){
                return AjaxResult.error("请添加出库单行！");
            }
        }

        return toAjax(wmMiscIssueService.updateWmMiscIssue(wmMiscIssue));
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
        WmMiscIssue issue = wmMiscIssueService.selectWmMiscIssueByIssueId(issueId);
        List<MiscIssueTxBean> beans = wmMiscIssueService.getTxBeans(issueId);

        //调用库存核心
        storageCoreService.processMiscIssue(beans);

        //更新单据状态
        issue.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmMiscIssueService.updateWmMiscIssue(issue);
        return AjaxResult.success();
    }


    /**
     * 删除杂项出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:miscissue:remove')")
    @Log(title = "杂项出库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds)
    {
        return toAjax(wmMiscIssueService.deleteWmMiscIssueByIssueIds(issueIds));
    }
}
