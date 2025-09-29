package com.ktg.mes.wm.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.domain.MdItemBatchConfig;
import com.ktg.mes.md.service.IMdItemBatchConfigService;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmBatchMapper;
import com.ktg.mes.wm.domain.WmBatch;
import com.ktg.mes.wm.service.IWmBatchService;

/**
 * 批次记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2025-02-24
 */
@Service
public class WmBatchServiceImpl implements IWmBatchService 
{
    @Autowired
    private WmBatchMapper wmBatchMapper;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    @Autowired
    private IMdItemService mdItemService;

    @Autowired
    private WmBarCodeUtil barcodeUtil;

    @Autowired
    private IMdItemBatchConfigService mdItemBatchConfigService;

    /**
     * 查询批次记录
     * 
     * @param batchId 批次记录主键
     * @return 批次记录
     */
    @Override
    public WmBatch selectWmBatchByBatchId(Long batchId)
    {
        return wmBatchMapper.selectWmBatchByBatchId(batchId);
    }

    /**
     * 查询批次记录列表
     * 
     * @param wmBatch 批次记录
     * @return 批次记录
     */
    @Override
    public List<WmBatch> selectWmBatchList(WmBatch wmBatch)
    {
        return wmBatchMapper.selectWmBatchList(wmBatch);
    }

    @Override
    public List<WmBatch> selectForwardBatchList(String batchCode) {
        return wmBatchMapper.selectForwardBatchList(batchCode);
    }

    @Override
    public List<WmBatch> selectBackwardBatchList(String batchCode) {
        return wmBatchMapper.selectBackwardBatchList(batchCode);
    }

    /**
     * 新增批次记录
     * 
     * @param wmBatch 批次记录
     * @return 结果
     */
    @Override
    public int insertWmBatch(WmBatch wmBatch)
    {
        wmBatch.setCreateTime(DateUtils.getNowDate());
        return wmBatchMapper.insertWmBatch(wmBatch);
    }

    /**
     * 修改批次记录
     * 
     * @param wmBatch 批次记录
     * @return 结果
     */
    @Override
    public int updateWmBatch(WmBatch wmBatch)
    {
        wmBatch.setUpdateTime(DateUtils.getNowDate());
        return wmBatchMapper.updateWmBatch(wmBatch);
    }

    /**
     * 批量删除批次记录
     * 
     * @param batchIds 需要删除的批次记录主键
     * @return 结果
     */
    @Override
    public int deleteWmBatchByBatchIds(Long[] batchIds)
    {
        return wmBatchMapper.deleteWmBatchByBatchIds(batchIds);
    }

    /**
     * 删除批次记录信息
     * 
     * @param batchId 批次记录主键
     * @return 结果
     */
    @Override
    public int deleteWmBatchByBatchId(Long batchId)
    {
        return wmBatchMapper.deleteWmBatchByBatchId(batchId);
    }


    /**
     * 获取批次号
     * 根据传入的参数获取批次号
     * 如果根据参数可以查询到批次号，则返回批次号，否则生成新的批次号
     * @param wmBatch
     * @return
     */
    @Override
    public WmBatch getOrGenerateBatchCode(WmBatch wmBatch) {

        //1.检查物料ID/物料编码参数是否为空
        if(wmBatch.getItemId() == null && wmBatch.getItemCode() == null){
            throw new IllegalArgumentException("物料ID/物料编码不能同时为空!");
        }else{
            //检查是否批次管理
            MdItem item = mdItemService.selectMdItemById(wmBatch.getItemId());
            if(UserConstants.YES.equals(item.getBatchFlag())){
                MdItemBatchConfig config = mdItemBatchConfigService.getMdItemBatchConfigByItemId(wmBatch.getItemId());
                if(config == null){
                    throw new IllegalArgumentException("物料"+wmBatch.getItemCode()+"未配置批次管理!");
                }

                //检查传入的参数是否符合配置
                if(UserConstants.YES.equals(config.getProduceDateFlag())){
                    if(wmBatch.getProduceDate() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要生产日期参数!");
                    }
                }else{
                    wmBatch.setProduceDate(null); //不需要则无论是否传入此参数都直接置空
                }

                if(UserConstants.YES.equals(config.getRecptDateFlag())){
                    if(wmBatch.getRecptDate() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要入库日期参数!");
                    }
                }else{
                    wmBatch.setRecptDate(null);
                }

                if(UserConstants.YES.equals(config.getExpireDateFlag())){
                    if(wmBatch.getExpireDate() == null){
                        throw new IllegalArgumentException("当前物料批生成次号，需要过期日期参数!");
                    }
                }else{
                    wmBatch.setExpireDate(null);
                }

                if(UserConstants.YES.equals(config.getVendorFlag())){
                    if(wmBatch.getVendorId() == null && wmBatch.getVendorCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要供应商参数!");
                    }
                }else{
                    wmBatch.setVendorId(null);
                    wmBatch.setVendorCode(null);
                }

                if(UserConstants.YES.equals(config.getClientFlag())){
                    if(wmBatch.getClientId() == null && wmBatch.getClientCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要客户参数!");
                    }
                }else{
                    wmBatch.setClientId(null);
                    wmBatch.setClientCode(null);
                }

                if(UserConstants.YES.equals(config.getPoCodeFlag())){
                    if(wmBatch.getPoCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要订单号参数!");
                    }
                }else{
                    wmBatch.setPoCode(null);
                }

                if(UserConstants.YES.equals(config.getCoCodeFlag())){
                    if(wmBatch.getCoCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要销售订单号参数!");
                    }
                }else{
                    wmBatch.setCoCode(null);
                }

                if(UserConstants.YES.equals(config.getWorkorderFlag())){
                    if(wmBatch.getWorkorderId() == null && wmBatch.getWorkorderCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要生产工单号参数!");
                    }
                }else{
                    wmBatch.setWorkorderId(null);
                    wmBatch.setWorkorderCode(null);
                }

                if(UserConstants.YES.equals(config.getTaskFlag())){
                    if(wmBatch.getTaskId() == null && wmBatch.getTaskCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要生产任务参数!");
                    }
                }else{
                    wmBatch.setTaskId(null);
                    wmBatch.setTaskCode(null);
                }

                if(UserConstants.YES.equals(config.getWorkstationFlag())){
                    if(wmBatch.getWorkstationId() == null && wmBatch.getWorkstationCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要工作站参数!");
                    }
                }else{
                    wmBatch.setWorkstationId(null);
                    wmBatch.setWorkstationCode(null);
                }

                if(UserConstants.YES.equals(config.getToolFlag())){
                    if(wmBatch.getToolId() == null && wmBatch.getToolCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要工具参数!");
                    }
                }else{
                    wmBatch.setToolId(null);
                    wmBatch.setToolCode(null);
                }

                if(UserConstants.YES.equals(config.getMoldFlag())){
                    if(wmBatch.getMoldId() == null && wmBatch.getMoldCode() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要模具参数!");
                    }
                }else{
                    wmBatch.setMoldId(null);
                    wmBatch.setMoldCode(null);
                }

                if(UserConstants.YES.equals(config.getLotNumberFlag())){
                    if(wmBatch.getLotNumber() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要生产批号参数!");
                    }
                }else{
                    wmBatch.setLotNumber(null);
                }

                if(UserConstants.YES.equals(config.getQualityStatusFlag())){
                    if(wmBatch.getQualityStatus() == null){
                        throw new IllegalArgumentException("当前物料生成批次号，需要质量状态参数!");
                    }
                }else{
                    wmBatch.setQualityStatus(null);
                }
            } else {
                //未启用批次管理则直接返回NULL
                return null;
            }
        }

        //2.根据参数查询现有的批次号
        WmBatch batch = wmBatchMapper.getBatchCodeByParams(wmBatch);

        //3.如果查询到批次号，则返回批次号，否则生成新的批次号
        if(batch != null){
            return batch;
        }

        //4.生成新的批次号
        String batchCode = autoCodeUtil.genSerialCode(UserConstants.BATCH_CODE,"");
        wmBatch.setBatchCode(batchCode);
        wmBatchMapper.insertWmBatch(wmBatch);
        barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_BATCH,wmBatch.getBatchId(),wmBatch.getBatchCode(), wmBatch.getItemName()); //为每个批次号生成一个码
        return wmBatch;
    }

    @Override
    public WmBatch checkBatchCodeByBatchAndProperties(WmBatch wmBatch) {
        //未启用批次管理则直接返回NULL
        MdItem item = mdItemService.selectMdItemById(wmBatch.getItemId());
        if(UserConstants.NO.equals(item.getBatchFlag())){
            return null;
        }

        //1.批次号和物料不能为空
        if(wmBatch.getBatchCode() == null && wmBatch.getBatchId() == null){
            throw new IllegalArgumentException("批次号不能为空!");
        }
        if(wmBatch.getItemId() == null && wmBatch.getItemCode() == null){
            throw new IllegalArgumentException("物资不能为空!");
        }

        //2.查询批次号与对应的树形是否匹配
        WmBatch batch = wmBatchMapper.checkBatchCodeByBatchAndProperties(wmBatch);

        //3.如果查询到批次号，则返回批次号，否则生成新的批次号
        if(batch != null){
            return batch;
        }

        //4.只通过批次号匹配
        WmBatch param = new WmBatch();
        param.setBatchId(wmBatch.getBatchId());
        param.setBatchCode(wmBatch.getBatchCode());
        WmBatch oldBatch = wmBatchMapper.checkBatchCodeByBatchAndProperties(param);

        //5.更新除了batchId,batchCode之外的其他属性
        oldBatch.setBatchId(null);
        oldBatch.setBatchCode(null);
        oldBatch.updateFileds(wmBatch);


        //4.生成新的批次号
        String batchCode = autoCodeUtil.genSerialCode(UserConstants.BATCH_CODE,"");
        oldBatch.setBatchCode(batchCode);
        wmBatchMapper.insertWmBatch(oldBatch);

        return oldBatch;
    }
}
