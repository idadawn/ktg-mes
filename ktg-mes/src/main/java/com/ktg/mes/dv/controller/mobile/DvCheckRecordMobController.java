package com.ktg.mes.dv.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.dv.domain.DvCheckPlan;
import com.ktg.mes.dv.domain.DvCheckRecord;
import com.ktg.mes.dv.domain.DvCheckRecordLine;
import com.ktg.mes.dv.domain.DvCheckSubject;
import com.ktg.mes.dv.domain.dto.DvCheckPlanDTO;
import com.ktg.mes.dv.service.*;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2025/4/30
 */
@Api("设备台账")
@RestController
@RequestMapping("/mobile/dv/checkrecord")
public class DvCheckRecordMobController extends BaseController {

    @Autowired
    private IDvCheckRecordService dvCheckRecordService;

    @Autowired
    private IDvCheckSubjectService dvCheckSubjectService;

    @Autowired
    private IDvCheckRecordLineService dvCheckRecordLineService;

    @Autowired
    private IDvCheckPlanService dvCheckPlanService;


    /**
     * 查询设备点检记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvCheckRecord dvCheckRecord)
    {
        startPage();
        List<DvCheckRecord> list = dvCheckRecordService.selectDvCheckRecordList(dvCheckRecord);
        return getDataTable(list);
    }

    /**
     * 获取设备点检记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(dvCheckRecordService.selectDvCheckRecordByRecordId(recordId));
    }

    /**
     * 新增设备点检记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:add')")
    @Log(title = "设备点检记录", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody DvCheckRecord dvCheckRecord)
    {
        dvCheckRecordService.insertDvCheckRecord(dvCheckRecord);
        //查找生效的点检方案，生成对应的行信息
        DvCheckPlan plan = dvCheckPlanService.selectDvCheckPlanByMachineryCodeAndType(dvCheckRecord.getMachineryCode(),UserConstants.DV_PLAN_TYPE_CHECK);

        if(StringUtils.isNotNull(plan)){
            dvCheckRecord.setPlanId(plan.getPlanId());
            dvCheckRecord.setPlanCode(plan.getPlanCode());
            dvCheckRecord.setPlanName(plan.getPlanName());
            dvCheckRecord.setPlanType(plan.getPlanType());
            dvCheckRecordService.updateDvCheckRecord(dvCheckRecord);
            //根据选择的点检计划自动生成对应的行信息
            DvCheckSubject param = new DvCheckSubject();
            param.setPlanId(dvCheckRecord.getPlanId());
            List<DvCheckSubject> subjectList = dvCheckSubjectService.selectDvCheckSubjectList(param);
            if(!CollectionUtils.isEmpty(subjectList)){
                for(DvCheckSubject subject : subjectList){
                    DvCheckRecordLine line = new DvCheckRecordLine();
                    line.setRecordId(dvCheckRecord.getRecordId());
                    line.setSubjectId(subject.getSubjectId());
                    line.setSubjectCode(subject.getSubjectCode());
                    line.setSubjectName(subject.getSubjectName());
                    line.setSubjectType(subject.getSubjectType());
                    line.setSubjectContent(subject.getSubjectContent());
                    line.setSubjectStandard(subject.getSubjectStandard());
                    line.setCheckStatus(UserConstants.DV_CHECK_STATUS_NOTCHECK);
                    dvCheckRecordLineService.insertDvCheckRecordLine(line);
                }
            }
            return AjaxResult.success(dvCheckRecord);
        }else{
            return AjaxResult.error("当前设备未找到有效的点检方案，请先添加点检方案");
        }
    }


    /**
     * 修改设备点检记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:edit')")
    @Log(title = "设备点检记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckRecord dvCheckRecord)
    {
        if(UserConstants.ORDER_STATUS_FINISHED.equals(dvCheckRecord.getStatus())){
            DvCheckRecordLine param = new DvCheckRecordLine();
            param.setRecordId(dvCheckRecord.getRecordId());
            List<DvCheckRecordLine> lineList = dvCheckRecordLineService.selectDvCheckRecordLineList(param);
            if(CollectionUtils.isEmpty(lineList)){
                return AjaxResult.error("请添加设备点检项目结果信息");
            }
        }
        //如果设备不同则重新生成对应的点检行信息
        DvCheckRecord oldRecord = dvCheckRecordService.selectDvCheckRecordByRecordId(dvCheckRecord.getRecordId());
        if(!oldRecord.getMachineryCode().equals(dvCheckRecord.getMachineryCode())){
            dvCheckRecordLineService.deleteDvCheckRecordLineByRecordId(dvCheckRecord.getRecordId());
            DvCheckPlan plan = dvCheckPlanService.selectDvCheckPlanByMachineryCodeAndType(dvCheckRecord.getMachineryCode(),UserConstants.DV_PLAN_TYPE_CHECK);

            if(StringUtils.isNotNull(plan)){
                dvCheckRecord.setPlanId(plan.getPlanId());
                dvCheckRecord.setPlanCode(plan.getPlanCode());
                dvCheckRecord.setPlanName(plan.getPlanName());
                dvCheckRecord.setPlanType(plan.getPlanType());
                dvCheckRecordService.updateDvCheckRecord(dvCheckRecord);
                //根据选择的点检计划自动生成对应的行信息
                DvCheckSubject param = new DvCheckSubject();
                param.setPlanId(dvCheckRecord.getPlanId());
                List<DvCheckSubject> subjectList = dvCheckSubjectService.selectDvCheckSubjectList(param);
                if(!CollectionUtils.isEmpty(subjectList)){
                    for(DvCheckSubject subject : subjectList){
                        DvCheckRecordLine line = new DvCheckRecordLine();
                        line.setRecordId(dvCheckRecord.getRecordId());
                        line.setSubjectId(subject.getSubjectId());
                        line.setSubjectCode(subject.getSubjectCode());
                        line.setSubjectName(subject.getSubjectName());
                        line.setSubjectType(subject.getSubjectType());
                        line.setSubjectContent(subject.getSubjectContent());
                        line.setSubjectStandard(subject.getSubjectStandard());
                        line.setCheckStatus(UserConstants.DV_CHECK_STATUS_NOTCHECK);
                        dvCheckRecordLineService.insertDvCheckRecordLine(line);
                    }
                }
            }else{
                return AjaxResult.error("当前设备未找到有效的点检方案，请先添加点检方案");
            }
        }
        return toAjax(dvCheckRecordService.updateDvCheckRecord(dvCheckRecord));
    }

    /**
     * 删除设备点检记录
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkrecord:remove')")
    @Log(title = "设备点检记录", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {

        for(Long recordId : recordIds){
            dvCheckRecordLineService.deleteDvCheckRecordLineByRecordId(recordId);
        }

        return toAjax(dvCheckRecordService.deleteDvCheckRecordByRecordIds(recordIds));
    }

}
