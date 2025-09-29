package com.ktg.mes.pro.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.service.IMdWorkstationService;
import com.ktg.mes.pro.domain.*;
import com.ktg.mes.pro.service.IProSnProcessService;
import com.ktg.mes.pro.service.IProTaskService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmSn;
import com.ktg.mes.wm.service.IWmSnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/11/22
 */


@Api
@RestController
@RequestMapping("/mobile/pro/prosn")
public class ProSnProcessMobController extends BaseController {

    @Autowired
    private IProSnProcessService proSnProcessService;

    @Autowired
    private IWmSnService wmSnService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IProTaskService proTaskService;

    /**
     * 新增SN流转-工序信息
     */
    @PreAuthorize("@ss.hasPermi('pro:prosnprocess:add')")
    @Log(title = "SN流转-工序信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProSnProcess proSnProcess)
    {
        if(!StringUtils.isNotNull(proSnProcess.getSnCode())){
            return AjaxResult.error("请传递SN编号参数!");
        }

        WmSn param = new WmSn();
        param.setSnCode(proSnProcess.getSnCode());
        List<WmSn> snList = wmSnService.selectWmSnList(param);

        if(CollectionUtils.isEmpty(snList)){
            return AjaxResult.error("SN编码无效!");
        }else {
            proSnProcess.setSnId(snList.get(0).getSnId());
        }

        if(!StringUtils.isNotNull(proSnProcess.getWorkstationId())){
            return AjaxResult.error("请传递工作站参数!");
        }

        MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(proSnProcess.getWorkstationId());
        if(StringUtils.isNotNull(workstation)){
            proSnProcess.setWorkstationCode(workstation.getWorkstationCode());
            proSnProcess.setWorkstationName(workstation.getWorkstationName());
            proSnProcess.setProcessId(workstation.getProcessId());
            proSnProcess.setProcessCode(workstation.getProcessCode());
            proSnProcess.setProcessName(workstation.getProcessName());
        }else{
            return AjaxResult.error("工作站无效!");
        }

        proSnProcess.setInputTime(new Date());
        proSnProcess.setUserId(getUserId());
        proSnProcess.setUserName(getUsername());
        proSnProcess.setNickName(getLoginUser().getUser().getNickName());
        proSnProcess.setQuantityInput(BigDecimal.ONE);
        proSnProcessService.insertProSnProcess(proSnProcess);

        return AjaxResult.success(proSnProcess);
    }


    /**
     * 根据扫描到的SN编号查询此工作站分配的生产任务
     * @param snProcess
     * @return
     */
    @ApiOperation("根据流转卡号和工作站查询所有的生产任务")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/getSnProTask")
    public AjaxResult getCardProTask(ProSnProcess snProcess){

        WmSn param = new WmSn();
        param.setSnCode(snProcess.getSnCode());
        List<WmSn> snList = wmSnService.selectWmSnList(param);

        WmSn sn = null;
        if(CollectionUtils.isEmpty(snList)){
            return AjaxResult.error("SN编码无效!");
        }else {
            sn = snList.get(0);
        }

        if(!StringUtils.isNotNull(snProcess.getWorkstationId())){
            return AjaxResult.error("请传递工作站参数!");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(sn.getWorkorderId());
        if(StringUtils.isNotNull(workorder)){
            ProTask p = new ProTask();
            p.setWorkorderId(workorder.getWorkorderId());
            p.setWorkstationId(snProcess.getWorkstationId());
            List<ProTask> tasks = proTaskService.selectProTaskList(p);
            if(CollectionUtils.isEmpty(tasks)){
                return AjaxResult.error("此工作站未被分配当前SN对应的生产任务!");
            }

            return AjaxResult.success(tasks);
        }else{
            return AjaxResult.error("SN对应的生产工单无效！");
        }

    }

}
