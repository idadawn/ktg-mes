package com.ktg.mes.wm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdProductBom;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.mapper.MdWorkstationMapper;
import com.ktg.mes.pro.domain.*;
import com.ktg.mes.pro.mapper.*;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.tx.ItemConsumeTxBean;
import com.ktg.mes.wm.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.service.IWmItemConsumeService;

/**
 * 物料消耗记录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-09-19
 */
@Service
public class WmItemConsumeServiceImpl implements IWmItemConsumeService 
{
    @Autowired
    private WmItemConsumeMapper wmItemConsumeMapper;

    @Autowired
    private WmItemConsumeLineMapper wmItemConsumeLineMapper;

    @Autowired
    private WmItemConsumeDetailMapper wmItemConsumeDetailMapper;

    @Autowired
    private WmWarehouseMapper wmWarehouseMapper;

    @Autowired
    private WmStorageLocationMapper wmStorageLocationMapper;

    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;

    @Autowired
    private ProRouteMapper proRouteMapper;

    @Autowired
    private ProRouteProductBomMapper proRouteProductBomMapper;

    @Autowired
    private WmMaterialStockMapper wmMaterialStockMapper;

    /**
     * 查询物料消耗记录
     * 
     * @param recordId 物料消耗记录主键
     * @return 物料消耗记录
     */
    @Override
    public WmItemConsume selectWmItemConsumeByRecordId(Long recordId)
    {
        return wmItemConsumeMapper.selectWmItemConsumeByRecordId(recordId);
    }

    /**
     * 查询物料消耗记录列表
     * 
     * @param wmItemConsume 物料消耗记录
     * @return 物料消耗记录
     */
    @Override
    public List<WmItemConsume> selectWmItemConsumeList(WmItemConsume wmItemConsume)
    {
        return wmItemConsumeMapper.selectWmItemConsumeList(wmItemConsume);
    }

    /**
     * 新增物料消耗记录
     * 
     * @param wmItemConsume 物料消耗记录
     * @return 结果
     */
    @Override
    public int insertWmItemConsume(WmItemConsume wmItemConsume)
    {
        wmItemConsume.setCreateTime(DateUtils.getNowDate());
        return wmItemConsumeMapper.insertWmItemConsume(wmItemConsume);
    }

    /**
     * 修改物料消耗记录
     * 
     * @param wmItemConsume 物料消耗记录
     * @return 结果
     */
    @Override
    public int updateWmItemConsume(WmItemConsume wmItemConsume)
    {
        wmItemConsume.setUpdateTime(DateUtils.getNowDate());
        return wmItemConsumeMapper.updateWmItemConsume(wmItemConsume);
    }

    /**
     * 批量删除物料消耗记录
     * 
     * @param recordIds 需要删除的物料消耗记录主键
     * @return 结果
     */
    @Override
    public int deleteWmItemConsumeByRecordIds(Long[] recordIds)
    {
        return wmItemConsumeMapper.deleteWmItemConsumeByRecordIds(recordIds);
    }

    /**
     * 删除物料消耗记录信息
     * 
     * @param recordId 物料消耗记录主键
     * @return 结果
     */
    @Override
    public int deleteWmItemConsumeByRecordId(Long recordId)
    {
        return wmItemConsumeMapper.deleteWmItemConsumeByRecordId(recordId);
    }

    /**
     * 生成物料消耗单
     * 在启用批次功能后，线边库中物资的消耗就不能简单的按照物料来消耗了。因为同一个物料可能存在多个批次。
     * 具体消耗哪个批次的物料需要有一定的匹配规则
     * 规则：  判断当前工作站是否有BOM物料的过站扫码记录，如果有则优先消耗过站记录的物资；如果没有，则按照先进先出原则消耗线边库中领用到当前工单的物资。
     *      为了简单起见，当前规则为：从线边库查找所有此BOM物资的物料库存记录，按照先进先出原则进行消耗；如果线边库中物资库存不足则直接消耗当前物料（不带批次号）。
     *      备注：实际的库存管理中一般做不到精确控制物资消耗；除非在每个工作站上要求采用扫描SN码、批次码等；所以每个工作站实际消耗的是哪些批次的物资，是否是当前工单的均不可控。
     *           故此处只管根据物资去线边库进行消耗，不管具体的批次号。等每次对线边库的物资进行盘库时，再根据每个批次的领用数量、退料数量、库存数量来更正实际消耗数量。
     *
     * 详细逻辑:
     *    1.根据当前生产报工物资，计算出所有预计消耗的BOM物资及数量
     *    2.遍历所有的BOM消耗行
     *    3.查找此物资在线边库中的库存记录（根据工单领料单上的批次进行匹配）
     *    4.按照先进先出原则进行匹配，匹配到对应的库存信息，生成消耗单明细行；如果找不到，则生成一个不带批次号的消耗单明细行。
     *
     *
     * @param feedback
     */
    @Override
    public WmItemConsume generateItemConsume(ProFeedback feedback){

        //先获取当前生产的产品在此道工序中配置的物料BOM
        ProRoute route = proRouteMapper.getRouteByProductId(feedback.getItemId());
        if(StringUtils.isNull(route)){
            throw new RuntimeException("没有找到当前产品的工艺路线，无法进行物资消耗");
        }
        ProRouteProductBom param = new ProRouteProductBom();
        param.setProductId(feedback.getItemId());
        param.setRouteId(route.getRouteId());
        param.setProcessId(feedback.getProcessId());
        List<ProRouteProductBom> boms = proRouteProductBomMapper.selectProRouteProductBomList(param);
        if(CollectionUtil.isEmpty(boms)){
            //没有物料消耗直接退出
            return null;
        }

        WmWarehouse warehouse = wmWarehouseMapper.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
        WmStorageLocation location = wmStorageLocationMapper.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
        WmStorageArea area = wmStorageAreaMapper.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);

        //生成消耗单头
        WmItemConsume itemConsume = new WmItemConsume();
        itemConsume.setWorkorderId(feedback.getWorkorderId());
        itemConsume.setWorkorderCode(feedback.getWorkorderCode());
        itemConsume.setWorkorderName(feedback.getWorkorderName());
        itemConsume.setWorkstationId(feedback.getWorkstationId());
        itemConsume.setWorkstationCode(feedback.getWorkstationCode());
        itemConsume.setWorkstationName(feedback.getWorkstationName());
        itemConsume.setProcessId(feedback.getProcessId());
        itemConsume.setProcessCode(feedback.getProcessCode());
        itemConsume.setProcessName(feedback.getProcessName());
        itemConsume.setTaskId(feedback.getTaskId());
        itemConsume.setTaskCode(feedback.getTaskCode());
        itemConsume.setFeedbackId(feedback.getRecordId());
        itemConsume.setConsumeDate(new Date());

        wmItemConsumeMapper.insertWmItemConsume(itemConsume);

        for(ProRouteProductBom bom : boms){
            WmItemConsumeLine line = new WmItemConsumeLine();
            line.setRecordId(itemConsume.getRecordId());
            line.setItemId(bom.getItemId());
            line.setItemCode(bom.getItemCode());
            line.setItemName(bom.getItemName());
            line.setSpecification(bom.getSpecification());
            line.setUnitOfMeasure(bom.getUnitOfMeasure());
            line.setUnitName(bom.getUnitName());
            line.setQuantityConsume(bom.getQuantity().multiply(feedback.getQuantityFeedback()));
            wmItemConsumeLineMapper.insertWmItemConsumeLine(line);


            WmMaterialStock p1 = new WmMaterialStock();
            p1.setItemId(line.getItemId());
            p1.setWarehouseCode(UserConstants.VIRTUAL_WH);
            p1.setLocationCode(UserConstants.VIRTUAL_WS);
            p1.setAreaCode(UserConstants.VIRTUAL_WA);
            List<WmMaterialStock> stocks = wmMaterialStockMapper.selectWmMaterialStockList(p1);
            if(CollectionUtil.isEmpty(stocks)){
                //线边库中没有该物料，则生成一个不带批次号的消耗单明细行
                WmItemConsumeDetail detail = new WmItemConsumeDetail();
                detail.setRecordId(line.getRecordId());
                detail.setLineId(line.getLineId());
                //此处的materialStockId为null
                detail.setItemId(line.getItemId());
                detail.setItemCode(line.getItemCode());
                detail.setItemName(line.getItemName());
                detail.setSpecification(line.getSpecification());
                detail.setUnitOfMeasure(line.getUnitOfMeasure());
                detail.setUnitName(line.getUnitName());
                detail.setQuantity(line.getQuantityConsume());
                detail.setBatchId(null);
                detail.setBatchCode(null);
                detail.setWarehouseId(warehouse.getWarehouseId());
                detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
                detail.setWarehouseName(warehouse.getWarehouseName());
                detail.setLocationId(location.getLocationId());
                detail.setLocationCode(UserConstants.VIRTUAL_WS);
                detail.setLocationName(location.getLocationName());
                detail.setAreaId(area.getAreaId());
                detail.setAreaCode(UserConstants.VIRTUAL_WA);
                detail.setAreaName(area.getAreaName());
                wmItemConsumeDetailMapper.insertWmItemConsumeDetail(detail);
                continue;
            }else{
                //线边库中有该物料，则按照先进先出原则进行消耗
                BigDecimal quantityToConsume = line.getQuantityConsume();

                for(WmMaterialStock stock : stocks){
                    if(stock.getQuantityOnhand().compareTo(quantityToConsume) >= 0){
                        //线边库中当前批次有足够的库存，则消耗掉该批次的物料
                        WmItemConsumeDetail detail = new WmItemConsumeDetail();
                        detail.setRecordId(line.getRecordId());
                        detail.setLineId(line.getLineId());
                        detail.setMaterialStockId(stock.getMaterialStockId());
                        detail.setItemId(line.getItemId());
                        detail.setItemCode(line.getItemCode());
                        detail.setItemName(line.getItemName());
                        detail.setSpecification(line.getSpecification());
                        detail.setUnitOfMeasure(line.getUnitOfMeasure());
                        detail.setUnitName(line.getUnitName());
                        detail.setQuantity(quantityToConsume);
                        detail.setBatchId(stock.getBatchId());
                        detail.setBatchCode(stock.getBatchCode());
                        detail.setWarehouseId(warehouse.getWarehouseId());
                        detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
                        detail.setWarehouseName(warehouse.getWarehouseName());
                        detail.setLocationId(location.getLocationId());
                        detail.setLocationCode(UserConstants.VIRTUAL_WS);
                        detail.setLocationName(location.getLocationName());
                        detail.setAreaId(area.getAreaId());
                        detail.setAreaCode(UserConstants.VIRTUAL_WA);
                        detail.setAreaName(area.getAreaName());
                        wmItemConsumeDetailMapper.insertWmItemConsumeDetail(detail);
                        quantityToConsume = BigDecimal.ZERO;
                        break; //处理下一行
                    }else{
                        //线边库中没有足够的库存，则消耗掉线边库中当前批次所有该物料的库存，继续扣减下一批次
                        WmItemConsumeDetail detail = new WmItemConsumeDetail();
                        detail.setRecordId(line.getRecordId());
                        detail.setLineId(line.getLineId());
                        detail.setMaterialStockId(stock.getMaterialStockId());
                        detail.setItemId(line.getItemId());
                        detail.setItemCode(line.getItemCode());
                        detail.setItemName(line.getItemName());
                        detail.setSpecification(line.getSpecification());
                        detail.setUnitOfMeasure(line.getUnitOfMeasure());
                        detail.setUnitName(line.getUnitName());
                        detail.setQuantity(stock.getQuantityOnhand());
                        detail.setBatchId(stock.getBatchId());
                        detail.setBatchCode(stock.getBatchCode());
                        detail.setWarehouseId(warehouse.getWarehouseId());
                        detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
                        detail.setWarehouseName(warehouse.getWarehouseName());
                        detail.setLocationId(location.getLocationId());
                        detail.setLocationCode(UserConstants.VIRTUAL_WS);
                        detail.setLocationName(location.getLocationName());
                        detail.setAreaId(area.getAreaId());
                        detail.setAreaCode(UserConstants.VIRTUAL_WA);
                        detail.setAreaName(area.getAreaName());
                        wmItemConsumeDetailMapper.insertWmItemConsumeDetail(detail);
                        quantityToConsume = quantityToConsume.subtract(stock.getQuantityOnhand());
                    }

                    if(quantityToConsume.compareTo(BigDecimal.ZERO)==0){
                        //量已经扣减完，则退出
                        break;
                    }
                }

                //库存遍历完成后还有剩余未扣除的数量，直接在库中新增一条为负的记录（后期手工核销）
                if(quantityToConsume.compareTo(BigDecimal.ZERO)==1){
                    WmItemConsumeDetail detail = new WmItemConsumeDetail();
                    detail.setRecordId(line.getRecordId());
                    detail.setLineId(line.getLineId());
                    //此处的materialStockId为null
                    detail.setItemId(line.getItemId());
                    detail.setItemCode(line.getItemCode());
                    detail.setItemName(line.getItemName());
                    detail.setSpecification(line.getSpecification());
                    detail.setUnitOfMeasure(line.getUnitOfMeasure());
                    detail.setUnitName(line.getUnitName());
                    detail.setQuantity(quantityToConsume);
                    detail.setBatchId(null);
                    detail.setBatchCode(null);
                    detail.setWarehouseId(warehouse.getWarehouseId());
                    detail.setWarehouseCode(UserConstants.VIRTUAL_WH);
                    detail.setWarehouseName(warehouse.getWarehouseName());
                    detail.setLocationId(location.getLocationId());
                    detail.setLocationCode(UserConstants.VIRTUAL_WS);
                    detail.setLocationName(location.getLocationName());
                    detail.setAreaId(area.getAreaId());
                    detail.setAreaCode(UserConstants.VIRTUAL_WA);
                    detail.setAreaName(area.getAreaName());
                    wmItemConsumeDetailMapper.insertWmItemConsumeDetail(detail);
                }
            }
        }

        return itemConsume;
    }


    @Override
    public List<ItemConsumeTxBean> getTxBeans(Long recordId) {
        return wmItemConsumeMapper.getTxBeans(recordId);
    }
}
