package com.ktg.mes.pro.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.service.IMdWorkstationService;
import com.ktg.mes.pro.domain.ProCard;
import com.ktg.mes.pro.domain.ProCardProcess;
import com.ktg.mes.pro.domain.ProTask;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProCardProcessService;
import com.ktg.mes.pro.service.IProCardService;
import com.ktg.mes.pro.service.IProTaskService;
import com.ktg.mes.pro.service.IProWorkorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/11/22
 */
@Api
@RestController
@RequestMapping("/mobile/pro/procardprocess")
public class ProCardProcessMobController extends BaseController {

    @Autowired
    private IProCardProcessService proCardProcessService;

    @Autowired
    private IProCardService proCardService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IProTaskService proTaskService;


    /**
     * 查询工序流转卡-工序信息列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/list")
    public AjaxResult list(ProCardProcess proCardProcess)
    {
        List<ProCardProcess> list = proCardProcessService.selectProCardProcessList(proCardProcess);
        return AjaxResult.success(list);
    }

    /**
     * 新增流转卡在某个工作站流转记录
     */
    @ApiOperation("新增流转卡在某个工作站流转记录接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:add')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProCardProcess proCardProcess)
    {
        if(!StringUtils.isNotNull(proCardProcess.getCardCode())){
            return AjaxResult.error("请传递流转卡编号参数!");
        }

        if(!StringUtils.isNotNull(proCardProcess.getQuantityInput())){
            return AjaxResult.error("请输入流转数量！");
        }

        ProCard p1 = new ProCard();
        p1.setCardCode(proCardProcess.getCardCode());
        List<ProCard> cardList = proCardService.selectProCardList(p1);
        ProCard card = null;
        if(!CollectionUtils.isEmpty(cardList)){
            card = cardList.get(0);
        }else{
            return AjaxResult.error("流转卡无效!");
        }

        proCardProcess.setCardId(card.getCardId());



        if(!StringUtils.isNotNull(proCardProcess.getWorkstationId())){
            return AjaxResult.error("请传递工作站参数!");
        }

        MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(proCardProcess.getWorkstationId());
        if(StringUtils.isNotNull(workstation)){
            proCardProcess.setWorkstationCode(workstation.getWorkstationCode());
            proCardProcess.setWorkstationName(workstation.getWorkstationName());
            proCardProcess.setProcessId(workstation.getProcessId());
            proCardProcess.setProcessCode(workstation.getProcessCode());
            proCardProcess.setProcessName(workstation.getProcessName());
        }else{
            return AjaxResult.error("工作站无效!");
        }

        /** 流转卡在某个工作站流转时的校验
         * 1.对应的生产工单没有结束
         * 2.为当前工作站分配了对应的生产任务
        **/
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(card.getWorkorderId());
        if(StringUtils.isNotNull(workorder)){

            if(UserConstants.ORDER_STATUS_FINISHED.equals(workorder.getStatus())){
                return AjaxResult.error("此流转卡对应的生产工单已经完成，无法再进行流转！");
            }

            ProTask param = new ProTask();
            param.setWorkorderId(workorder.getWorkorderId());
            param.setWorkstationId(workstation.getWorkstationId());
            List<ProTask> tasks = proTaskService.selectProTaskList(param);
            if(CollectionUtils.isEmpty(tasks)){
                return AjaxResult.error("此工作站未被分配当前流转卡对应的生产任务!");
            }
        }else{
            return AjaxResult.error("流转卡对应的生产工单无效！");
        }

        proCardProcess.setInputTime(new Date());
        proCardProcess.setUserId(getUserId());
        proCardProcess.setUserName(getUsername());
        proCardProcess.setNickName(getLoginUser().getUser().getNickName());

        proCardProcessService.insertProCardProcess(proCardProcess);

        return AjaxResult.success(proCardProcess);
    }

    /**
     * 根据扫描到的流转卡编号查询此工作站分配的生产任务
     * @param cardProcess
     * @return
     */
    @ApiOperation("根据流转卡号和工作站查询所有的生产任务")
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:list')")
    @GetMapping("/getCardProTask")
    public AjaxResult getCardProTask(ProCardProcess cardProcess){

        ProCard p1 = new ProCard();
        p1.setCardCode(cardProcess.getCardCode());
        List<ProCard> cardList = proCardService.selectProCardList(p1);
        ProCard card = null;
        if(!CollectionUtils.isEmpty(cardList)){
            card = cardList.get(0);
        }else{
            return AjaxResult.error("流转卡无效!");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(card.getWorkorderId());
        if(StringUtils.isNotNull(workorder)){
            ProTask p = new ProTask();
            p.setWorkorderId(workorder.getWorkorderId());
            p.setWorkstationId(cardProcess.getWorkstationId());
            List<ProTask> tasks = proTaskService.selectProTaskList(p);
            if(CollectionUtils.isEmpty(tasks)){
                return AjaxResult.error("此工作站未被分配当前流转卡对应的生产任务!");
            }

            return AjaxResult.success(tasks);
        }else{
            return AjaxResult.error("流转卡对应的生产工单无效！");
        }

    }


    /**
     * 删除工序流转卡在某个工作站的流转记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:procard:remove')")
    @Log(title = "工序流转卡-工序信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proCardProcessService.deleteProCardProcessByRecordIds(recordIds));
    }

}
