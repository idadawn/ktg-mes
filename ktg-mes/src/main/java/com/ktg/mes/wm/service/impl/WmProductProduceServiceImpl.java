package com.ktg.mes.wm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.bean.BeanUtils;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.mapper.MdWorkstationMapper;
import com.ktg.mes.pro.domain.ProFeedback;
import com.ktg.mes.pro.domain.ProProcess;
import com.ktg.mes.pro.domain.ProTask;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.mapper.ProProcessMapper;
import com.ktg.mes.pro.mapper.ProTaskMapper;
import com.ktg.mes.pro.mapper.ProWorkorderMapper;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.ProductProductTxBean;
import com.ktg.mes.wm.mapper.*;
import com.ktg.mes.wm.service.IStorageCoreService;
import com.ktg.mes.wm.service.IWmBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.service.IWmProductProduceService;

/**
 * 产品产出记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-09-21
 */
@Service
public class WmProductProduceServiceImpl implements IWmProductProduceService 
{
    @Autowired
    private WmProductProduceMapper wmProductProduceMapper;

    @Autowired
    private WmProductProduceLineMapper wmProductProduceLineMapper;

    @Autowired
    private WmProductProduceDetailMapper wmProductProduceDetailMapper;

    @Autowired
    private WmWarehouseMapper wmWarehouseMapper;

    @Autowired
    private WmStorageLocationMapper wmStorageLocationMapper;

    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private ProWorkorderMapper proWorkorderMapper;

    @Autowired
    private ProTaskMapper proTaskMapper;

    @Autowired
    private MdWorkstationMapper mdWorkstationMapper;

    @Autowired
    private ProProcessMapper proProcessMapper;

    @Autowired
    private IWmBatchService wmBatchService;

    /**
     * 查询产品产出记录
     * 
     * @param recordId 产品产出记录主键
     * @return 产品产出记录
     */
    @Override
    public WmProductProduce selectWmProductProduceByRecordId(Long recordId)
    {
        return wmProductProduceMapper.selectWmProductProduceByRecordId(recordId);
    }

    /**
     * 查询产品产出记录列表
     * 
     * @param wmProductProduce 产品产出记录
     * @return 产品产出记录
     */
    @Override
    public List<WmProductProduce> selectWmProductProduceList(WmProductProduce wmProductProduce)
    {
        return wmProductProduceMapper.selectWmProductProduceList(wmProductProduce);
    }

    /**
     * 新增产品产出记录
     * 
     * @param wmProductProduce 产品产出记录
     * @return 结果
     */
    @Override
    public int insertWmProductProduce(WmProductProduce wmProductProduce)
    {
        wmProductProduce.setCreateTime(DateUtils.getNowDate());
        return wmProductProduceMapper.insertWmProductProduce(wmProductProduce);
    }

    /**
     * 修改产品产出记录
     * 
     * @param wmProductProduce 产品产出记录
     * @return 结果
     */
    @Override
    public int updateWmProductProduce(WmProductProduce wmProductProduce)
    {
        wmProductProduce.setUpdateTime(DateUtils.getNowDate());
        return wmProductProduceMapper.updateWmProductProduce(wmProductProduce);
    }

    /**
     * 批量删除产品产出记录
     * 
     * @param recordIds 需要删除的产品产出记录主键
     * @return 结果
     */
    @Override
    public int deleteWmProductProduceByRecordIds(Long[] recordIds)
    {
        return wmProductProduceMapper.deleteWmProductProduceByRecordIds(recordIds);
    }

    /**
     * 删除产品产出记录信息
     * 
     * @param recordId 产品产出记录主键
     * @return 结果
     */
    @Override
    public int deleteWmProductProduceByRecordId(Long recordId)
    {
        return wmProductProduceMapper.deleteWmProductProduceByRecordId(recordId);
    }

    /**
     * 根据报工单生成
     * @param feedback
     * @return
     */
    @Override
    public WmProductProduce generateProductProduce(ProFeedback feedback) {
        ProWorkorder workorder = proWorkorderMapper.selectProWorkorderByWorkorderId(feedback.getWorkorderId());
        MdWorkstation workstation = mdWorkstationMapper.selectMdWorkstationByWorkstationId(feedback.getWorkstationId());
        ProProcess process = proProcessMapper.selectProProcessByProcessId(workstation.getProcessId());
        ProTask task = proTaskMapper.selectProTaskByTaskId(feedback.getTaskId());
        //生成单据头信息
        WmProductProduce productProduce = new WmProductProduce();
        productProduce.setWorkorderId(feedback.getWorkorderId());
        productProduce.setWorkorderCode(feedback.getWorkorderCode());
        productProduce.setWorkorderName(feedback.getWorkorderName());
        productProduce.setFeedbackId(feedback.getRecordId());
        productProduce.setTaskId(feedback.getTaskId());
        productProduce.setTaskCode(task.getTaskCode());
        productProduce.setTaskName(task.getTaskName());

        productProduce.setWorkstationId(feedback.getWorkstationId());
        productProduce.setWorkstationCode(workstation.getWorkstationCode());
        productProduce.setWorkstationName(workstation.getWorkstationName());

        productProduce.setProcessId(process.getProcessId());
        productProduce.setProcessCode(process.getProcessCode());
        productProduce.setProcessName(process.getProcessName());

        productProduce.setProduceDate(new Date());
        productProduce.setStatus(UserConstants.ORDER_STATUS_PREPARE);
        wmProductProduceMapper.insertWmProductProduce(productProduce);

        //生成单据行信息; 以后如果是在生产过程中产生多种副产品可以在这里添加更多的行信息进行支持
        WmProductProduceLine line = new WmProductProduceLine();
        line.setRecordId(productProduce.getRecordId());
        line.setItemId(feedback.getItemId());
        line.setItemCode(feedback.getItemCode());
        line.setItemName(feedback.getItemName());
        line.setSpecification(feedback.getSpecification());
        line.setUnitOfMeasure(feedback.getUnitOfMeasure());
        line.setUnitName(feedback.getUnitName());
        line.setExpireDate(feedback.getExpireDate()); //TODO: 这里的过期日期需要根据物资配置的有效期自动生成，在生成line时生成
        line.setLotNumber(feedback.getLotNumber());   //TODO: 这里的批次号需要在生产报工时提供 （不过当前版本的批次号生成规则暂时不启用批号和过期日期）
        line.setQuantityProduce(feedback.getQuantityFeedback());


        //此处要根据物资配置的批次属性规则生成对应的批次号
        WmBatch batch = new WmBatch();
        batch.setItemId(line.getItemId());
        batch.setItemCode(line.getItemCode());
        batch.setItemName(line.getItemName());
        batch.setSpecification(line.getSpecification());
        batch.setUnitOfMeasure(line.getUnitOfMeasure());
        batch.setUnitName(line.getUnitName());
        //这里的生产日期必须是yyyy-MM-dd格式的，不能是精确到时分秒的时间格式，否则会出现同一天的产品批次号不一致的情况
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            batch.setProduceDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            throw new RuntimeException("日期格式化错误!");
        }
        batch.setExpireDate(line.getExpireDate());
        batch.setCoCode(workorder.getSourceCode());
        batch.setWorkorderCode(workorder.getWorkorderCode());
        batch.setClientId(workorder.getClientId());
        batch.setClientCode(workorder.getClientCode());
        batch.setLotNumber(line.getLotNumber());
        batch.setWorkstationId(workstation.getWorkstationId());
        batch.setWorkstationCode(workstation.getWorkstationCode());

        WmBatch wmBatch = wmBatchService.getOrGenerateBatchCode(batch);
        if (wmBatch != null) {
            line.setBatchId(wmBatch.getBatchId());
            line.setBatchCode(wmBatch.getBatchCode());
        }else{
            line.setBatchId(null);
            line.setBatchCode(null);
        }

        WmWarehouse warehouse = wmWarehouseMapper.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
        WmStorageLocation location = wmStorageLocationMapper.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
        WmStorageArea area = wmStorageAreaMapper.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);


        //根据产品是否需要检验设置行的质量状态
        if (UserConstants.YES.equals(feedback.getIsCheck())) {
            line.setQualityStatus(UserConstants.QUALITY_STATUS_NOTTEST);
            wmProductProduceLineMapper.insertWmProductProduceLine(line);
            //先不生成明细行，等待检验完成时，再根据行的质量状态生成明细行
        }else{
            //根据合格品/不合格品对行进行拆分
            //生成单据明细信息
            WmProductProduceDetail detail = new WmProductProduceDetail();
            detail.setRecordId(productProduce.getRecordId());
            detail.setItemId(line.getItemId());
            detail.setItemCode(line.getItemCode());
            detail.setItemName(line.getItemName());
            detail.setSpecification(line.getSpecification());
            detail.setUnitOfMeasure(line.getUnitOfMeasure());
            detail.setUnitName(line.getUnitName());
            detail.setQuantity(line.getQuantityProduce());
            detail.setBatchId(line.getBatchId());
            detail.setBatchCode(line.getBatchCode());
            detail.setWarehouseId(warehouse.getWarehouseId());
            detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
            detail.setWarehouseName(warehouse.getWarehouseName());
            detail.setLocationId(location.getLocationId());
            detail.setLocationCode(UserConstants.VIRTUAL_WS);
            detail.setLocationName(location.getLocationName());
            detail.setAreaId(area.getAreaId());
            detail.setAreaCode(UserConstants.VIRTUAL_WA);
            detail.setAreaName(area.getAreaName());

            //不良品部分
            WmProductProduceLine unqualifyLine = new WmProductProduceLine();
            BeanUtils.copyBeanProp(unqualifyLine,line);
            unqualifyLine.setQuantityProduce(feedback.getQuantityUnquanlified());
            unqualifyLine.setQualityStatus(UserConstants.QUALITY_STATUS_NOTGOOD);
            wmProductProduceLineMapper.insertWmProductProduceLine(unqualifyLine);

            WmProductProduceDetail unqualifyDetail = new WmProductProduceDetail();
            BeanUtils.copyBeanProp(unqualifyDetail,detail);
            unqualifyDetail.setLineId(unqualifyLine.getLineId());
            unqualifyDetail.setQuantity(feedback.getQuantityUnquanlified());
            wmProductProduceDetailMapper.insertWmProductProduceDetail(unqualifyDetail);

            //合格品部分
            line.setQuantityProduce(feedback.getQuantityQualified());
            line.setQualityStatus(UserConstants.QUALITY_STATUS_PASS);
            wmProductProduceLineMapper.insertWmProductProduceLine(line);
            detail.setQuantity(feedback.getQuantityQualified());
            detail.setLineId(line.getLineId());
            wmProductProduceDetailMapper.insertWmProductProduceDetail(detail);
        }

        return productProduce;
    }

    @Override
    public List<ProductProductTxBean> getTxBeans(Long recordId) {
        return wmProductProduceMapper.getTxBeans(recordId);
    }

    @Override
    public void executeProductProduce(WmProductProduce wmProductProduce) {
        List<ProductProductTxBean> beans = wmProductProduceMapper.getTxBeans(wmProductProduce.getRecordId());
        storageCoreService.processProductProduce(beans);
        wmProductProduce.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmProductProduceMapper.updateWmProductProduce(wmProductProduce);
    }
}
