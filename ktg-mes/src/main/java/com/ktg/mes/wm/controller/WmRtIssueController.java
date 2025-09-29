package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.RtIssueTxBean;
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
 * 生产退料单头Controller
 * 
 * @author yinjinlu
 * @date 2022-09-15
 */
@RestController
@RequestMapping("/mes/wm/rtissue")
public class WmRtIssueController extends BaseController
{
    @Autowired
    private IWmRtIssueService wmRtIssueService;

    @Autowired
    private IWmRtIssueLineService wmRtIssueLineService;

    @Autowired
    private IWmRtIssueDetailService wmRtIssueDetailService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtIssue wmRtIssue)
    {
        startPage();
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        return getDataTable(list);
    }

    /**
     * 导出生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:export')")
    @Log(title = "生产退料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtIssue wmRtIssue)
    {
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        ExcelUtil<WmRtIssue> util = new ExcelUtil<WmRtIssue>(WmRtIssue.class);
        util.exportExcel(response, list, "生产退料单头数据");
    }

    /**
     * 获取生产退料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId)
    {
        return AjaxResult.success(wmRtIssueService.selectWmRtIssueByRtId(rtId));
    }

    /**
     * 新增生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:add')")
    @Log(title = "生产退料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtIssue wmRtIssue)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))){
            return AjaxResult.error("退料单编号已存在");
        }

        wmRtIssue.setCreateBy(getUsername());
        return toAjax(wmRtIssueService.insertWmRtIssue(wmRtIssue));
    }

    /**
     * 修改生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtIssue wmRtIssue)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))){
            return AjaxResult.error("退料单编号已存在");
        }

        WmRtIssueLine line = new WmRtIssueLine();
        line.setRtId(wmRtIssue.getRtId());
        List<WmRtIssueLine> lines = wmRtIssueLineService.selectWmRtIssueLineList(line);

        //草稿状态下可以根据头上的退料类型，更新行上物资的质量状态
        if(UserConstants.RTISSUE_STATUS_PREPARE.equals(wmRtIssue.getStatus())){
            //根据头上的退料类型，设置行上物资的质量状态
            for(WmRtIssueLine wmRtIssueLine : lines){
                //如果是余料退料，则行上物资的质量状态为合格；如果是废料/坏料退料，则行上物资的质量状态为不合格
                if(UserConstants.YES.equals(wmRtIssueLine.getQcFlag())){
                    wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
                }else{
                    if(UserConstants.RTISSUE_TYPE_RMR.equals(wmRtIssue.getRtType())){
                        wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
                    }else{
                        wmRtIssueLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
                    }
                }
                wmRtIssueLineService.updateWmRtIssueLine(wmRtIssueLine);
            }
        }

        //如果是向“待上架"状态提交，则判断行里面是否有待检验的物资，如果是，则状态改为“待检验”
        if(UserConstants.RTISSUE_STATUS_UNSTOCK.equals(wmRtIssue.getStatus())){
            if(CollectionUtils.isNotEmpty(lines)){
                for(WmRtIssueLine wmRtIssueLine : lines){
                    if(UserConstants.QUALITY_STATUS_NOTTEST.equals(wmRtIssueLine.getQualityStatus())){
                        wmRtIssue.setStatus(UserConstants.RTISSUE_STATUS_UNCHECK);
                        break;
                    }
                }
            }else{
                return AjaxResult.error("请添加退料物资!");
            }
        }

        //如果是向“待执行退料”状态提交，则检查行上的数量与明细行上总数量是否一致
        if(UserConstants.RTISSUE_STATUS_UNEXECUTE.equals(wmRtIssue.getStatus())) {
            boolean flag = true;
            StringBuilder sb = new StringBuilder();
            if(CollectionUtils.isNotEmpty(lines)){
                for(WmRtIssueLine wmRtIssueLine : lines){
                    if(UserConstants.LESS_THAN.equals(wmRtIssueDetailService.checkQuantity(wmRtIssueLine.getLineId()))){
                        flag = false;
                        sb.append(wmRtIssueLine.getItemName()).append(wmRtIssueLine.getBatchCode()).append("未完成上架！");
                    }
                }
            }

            if(!flag){
                return AjaxResult.error(sb.toString());
            }
        }


        return toAjax(wmRtIssueService.updateWmRtIssue(wmRtIssue));
    }

    /**
     * 删除生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:remove')")
    @Log(title = "生产退料单头", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds)
    {
        for (Long rtId: rtIds
        ) {
            if(UserConstants.ORDER_STATUS_PREPARE.equals(wmRtIssueService.selectWmRtIssueByRtId(rtId).getStatus())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AjaxResult.error("只能删除草稿状态的单据！");
            }
            wmRtIssueLineService.deleteByRtId(rtId);
        }
        return toAjax(wmRtIssueService.deleteWmRtIssueByRtIds(rtIds));
    }

    /**
     * 执行退料
     * @param rtId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{rtId}")
    public AjaxResult execute(@PathVariable Long rtId){
        WmRtIssue rtIssue = wmRtIssueService.selectWmRtIssueByRtId(rtId);
        WmRtIssueLine param = new WmRtIssueLine();
        param.setRtId(rtId);
        List<WmRtIssueLine> lines = wmRtIssueLineService.selectWmRtIssueLineList(param);
        if(CollUtil.isEmpty(lines)){
            return AjaxResult.error("请选择要退料的物资");
        }

        List<RtIssueTxBean> beans = wmRtIssueService.getTxBeans(rtId);

        //执行生产退料
        storageCoreService.processRtIssue(beans);


        rtIssue.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmRtIssueService.updateWmRtIssue(rtIssue);
        return AjaxResult.success();
    }

}
