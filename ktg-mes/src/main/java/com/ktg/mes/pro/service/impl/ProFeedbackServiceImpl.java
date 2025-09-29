package com.ktg.mes.pro.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.mes.pro.domain.ProTask;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.mapper.ProTaskMapper;
import com.ktg.mes.pro.mapper.ProWorkorderMapper;
import com.ktg.mes.pro.service.IProTaskService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmItemConsumeLine;
import com.ktg.mes.wm.domain.WmProductProduceLine;
import com.ktg.mes.wm.mapper.WmProductProduceLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProFeedbackMapper;
import com.ktg.mes.pro.domain.ProFeedback;
import com.ktg.mes.pro.service.IProFeedbackService;
import org.springframework.util.CollectionUtils;

/**
 * 生产报工记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-07-10
 */
@Service
public class ProFeedbackServiceImpl implements IProFeedbackService 
{
    @Autowired
    private ProFeedbackMapper proFeedbackMapper;

    @Autowired
    private ProTaskMapper proTaskMapper;

    @Autowired
    private ProWorkorderMapper proWorkorderMapper;

    @Autowired
    private WmProductProduceLineMapper wmProductProduceLineMapper;


    /**
     * 查询生产报工记录
     * 
     * @param recordId 生产报工记录主键
     * @return 生产报工记录
     */
    @Override
    public ProFeedback selectProFeedbackByRecordId(Long recordId)
    {
        return proFeedbackMapper.selectProFeedbackByRecordId(recordId);
    }

    /**
     * 查询生产报工记录列表
     * 
     * @param proFeedback 生产报工记录
     * @return 生产报工记录
     */
    @Override
    public List<ProFeedback> selectProFeedbackList(ProFeedback proFeedback)
    {
        return proFeedbackMapper.selectProFeedbackList(proFeedback);
    }

    /**
     * 新增生产报工记录
     * 
     * @param proFeedback 生产报工记录
     * @return 结果
     */
    @Override
    public int insertProFeedback(ProFeedback proFeedback)
    {
        proFeedback.setCreateTime(DateUtils.getNowDate());
        return proFeedbackMapper.insertProFeedback(proFeedback);
    }

    /**
     * 修改生产报工记录
     * 
     * @param proFeedback 生产报工记录
     * @return 结果
     */
    @Override
    public int updateProFeedback(ProFeedback proFeedback)
    {
        proFeedback.setUpdateTime(DateUtils.getNowDate());
        return proFeedbackMapper.updateProFeedback(proFeedback);
    }

    /**
     * 根据当前报工单的最终结果更新生产任务和生产工单的进度
     * @param feedback
     * 使用当前报工单的报工数量，更新任务的已生产数量
     * 使用当前报工单的报工数量，更新工单的已完成数量
     * 使用当前报工单关联的产品产出单上的合格品、不合格品数量更新任务的合格品、不合格品数量
     */
    @Override
    public void updateProTaskAndWorkorderByFeedback(ProFeedback feedback) {

        ProWorkorder workorder = proWorkorderMapper.selectProWorkorderByWorkorderId(feedback.getWorkorderId());

        ProTask task = proTaskMapper.selectProTaskByTaskId(feedback.getTaskId());

        //更新生产任务的生产数量(此处不良品计入生产总数）
        BigDecimal quantityProduced = task.getQuantityProduced()==null? new BigDecimal(0):task.getQuantityProduced();
        task.setQuantityProduced(quantityProduced.add(feedback.getQuantityFeedback()));

        BigDecimal quantityQuanlify = BigDecimal.ZERO;
        BigDecimal quantityUnquanlify = BigDecimal.ZERO;
        WmProductProduceLine param = new WmProductProduceLine();
        param.setFeedbackId(feedback.getRecordId());
        List<WmProductProduceLine> lines = wmProductProduceLineMapper.selectWmProductProduceLineList(param);
        if(!CollectionUtils.isEmpty(lines)){
            for(WmProductProduceLine line:lines){
                if(UserConstants.QUALITY_STATUS_NOTGOOD.equals(line.getQualityStatus())){
                    quantityUnquanlify = quantityUnquanlify.add(line.getQuantityProduce());
                }

                if(UserConstants.QUALITY_STATUS_PASS.equals(line.getQualityStatus())){
                    quantityQuanlify = quantityQuanlify.add(line.getQuantityProduce());
                }
            }
        }

        task.setQuantityQuanlify(task.getQuantityQuanlify().add(quantityQuanlify));
        task.setQuantityUnquanlify(task.getQuantityUnquanlify().add(quantityUnquanlify));
        proTaskMapper.updateProTask(task);

        //更新生产工单的生产数量;这里不良品也计入生产数量
        BigDecimal produced = workorder.getQuantityProduced() == null?new BigDecimal(0):workorder.getQuantityProduced();
        BigDecimal feedBackQuantity = feedback.getQuantityFeedback() ==null?new BigDecimal(0):feedback.getQuantityFeedback();
        workorder.setQuantityProduced( produced.add(feedBackQuantity));
        proWorkorderMapper.updateProWorkorder(workorder);
    }

    /**
     * 批量删除生产报工记录
     * 
     * @param recordIds 需要删除的生产报工记录主键
     * @return 结果
     */
    @Override
    public int deleteProFeedbackByRecordIds(Long[] recordIds)
    {
        return proFeedbackMapper.deleteProFeedbackByRecordIds(recordIds);
    }

    /**
     * 删除生产报工记录信息
     * 
     * @param recordId 生产报工记录主键
     * @return 结果
     */
    @Override
    public int deleteProFeedbackByRecordId(Long recordId)
    {
        return proFeedbackMapper.deleteProFeedbackByRecordId(recordId);
    }

    @Override
    public List<ProFeedback> selectByWorkorderIds(List<Long> workorderIds) {
        return proFeedbackMapper.selectByWorkorderIds(workorderIds);
    }
}
