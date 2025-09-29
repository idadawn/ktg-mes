package com.ktg.mes.wm.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.mes.wm.domain.*;
import com.ktg.mes.wm.domain.vo.WmAreaPrintVO;
import com.ktg.mes.wm.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmStorageAreaMapper;

/**
 * 库位设置Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-08
 */
@Service
public class WmStorageAreaServiceImpl implements IWmStorageAreaService 
{
    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmBarcodeService wmBarcodeService;

    /**
     * 查询库位设置
     * 
     * @param areaId 库位设置主键
     * @return 库位设置
     */
    @Override
    public WmStorageArea selectWmStorageAreaByAreaId(Long areaId)
    {
        return wmStorageAreaMapper.selectWmStorageAreaByAreaId(areaId);
    }

    @Override
    public WmPosition selectWmStorageAreaFullInfoByAreaId(Long areaId) {
        return wmStorageAreaMapper.selectWmStorageAreaFullInfoByAreaId(areaId);
    }

    @Override
    public WmStorageArea selectWmStorageAreaByAreaCode(String areaCode) {
        return wmStorageAreaMapper.selectWmStorageAreaByAreaCode(areaCode);
    }

    /**
     * 查询库位设置列表
     * 
     * @param wmStorageArea 库位设置
     * @return 库位设置
     */
    @Override
    public List<WmStorageArea> selectWmStorageAreaList(WmStorageArea wmStorageArea)
    {
        return wmStorageAreaMapper.selectWmStorageAreaList(wmStorageArea);
    }

    /**
     * 根据库位配置的物料和批次混放规则，检测传入的物料和批次，是否允许在当前库位存放*
     * @param itemId
     * @param batchId
     * @return OK：允许存放；NO_NATERIAL：不允许物料混放；NO_BATCH：不允许批次混放
     * 根据传入的库位ID查询库存记录
     * 查询当前库位的混放规则
     * 判断物资或者批次是否满足混放规则
     * 如果满足，返回OK；否则返回NO_NATERIAL或者NO_BATCH
     */
    @Override
    public String checkMatrialAndBatchMixing(Long areaId, Long itemId, Long batchId) {

        //查询当前库位上存放了哪些物资
        WmMaterialStock param = new WmMaterialStock();
        param.setAreaId(areaId);
        List<WmMaterialStock> stockList = wmMaterialStockService.selectWmMaterialStockList(param);

        if(!CollectionUtils.isEmpty(stockList)){
            WmStorageArea area = wmStorageAreaMapper.selectWmStorageAreaByAreaId(areaId);

            boolean materialMixingFlag = false;
            boolean batchMixingFlag = false;

            for(WmMaterialStock stock : stockList){
                if(UserConstants.NO.equals(area.getProductMixing()) && itemId!= null){
                    if(stock.getItemId().longValue() != itemId.longValue()){
                        materialMixingFlag = true;
                    }
                }

                if(UserConstants.NO.equals(area.getBatchMixing()) && batchId!= null){
                    if(stock.getBatchId().longValue() != batchId.longValue()){
                        batchMixingFlag = true;
                    }
                }
            }
            if(materialMixingFlag){
                return UserConstants.AREA_MIXING_CHECK_RESULT_NO_MATERIAL;
            }
            if(batchMixingFlag){
                return UserConstants.AREA_MIXING_CHECK_RESULT_NO_BATCH;
            }
            return UserConstants.AREA_MIXING_CHECK_RESULT_OK;
        }
        return UserConstants.AREA_MIXING_CHECK_RESULT_OK;
    }

    /**
     * 新增库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    @Override
    public int insertWmStorageArea(WmStorageArea wmStorageArea)
    {
        wmStorageArea.setCreateTime(DateUtils.getNowDate());
        return wmStorageAreaMapper.insertWmStorageArea(wmStorageArea);
    }

    /**
     * 修改库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    @Override
    public int updateWmStorageArea(WmStorageArea wmStorageArea)
    {
        wmStorageArea.setUpdateTime(DateUtils.getNowDate());
        return wmStorageAreaMapper.updateWmStorageArea(wmStorageArea);
    }

    @Override
    public void updateWmStorageAreaProductMixing(Long locationId, String flag) {
        WmStorageArea area = new WmStorageArea();
        area.setLocationId(locationId);
        area.setProductMixing(flag);
        wmStorageAreaMapper.updateWmStorageAreaProductMixing(area);
    }

    @Override
    public void updateWmStorageAreaBatchMixing(Long locationId, String flag) {
        WmStorageArea area = new WmStorageArea();
        area.setLocationId(locationId);
        area.setBatchMixing(flag);
        wmStorageAreaMapper.updateWmStorageAreaBatchMixing(area);
    }

    /**
     * 批量删除库位设置
     *
     * @param areaIds 需要删除的库位设置主键
     * @return 结果
     */
    @Override
    public AjaxResult deleteWmStorageAreaByAreaIds(Long[] areaIds)
    {
        // 查询所有删除数据
        List<WmStorageArea> areaList = wmStorageAreaMapper.selectByAreaIds(areaIds);
        for (WmStorageArea item : areaList) {
            String areaCode = item.getAreaCode();
            if (areaCode.contains("VIRTUAL")) {
                return AjaxResult.error("虚拟库位不能删除");
            }
        }

        // 校验库位中是否存在物料
        for (Long areaId : areaIds) {
            // 根据库位查询相关数据
            List<WmMaterialStock> list = wmMaterialStockService.getByAreaId(areaId);
            if (list != null && list.size() > 0) {
                Map<Long, List<WmMaterialStock>> collect = list.stream()
                        .collect(Collectors.groupingBy(WmMaterialStock::getItemId));
                for (Long l : collect.keySet()) {
                    List<WmMaterialStock> wmMaterialStocks = collect.get(l);
                    BigDecimal reduce = wmMaterialStocks.stream().map(WmMaterialStock::getQuantityOnhand).reduce(BigDecimal.ZERO, BigDecimal::add);
                    if (!(reduce.compareTo(BigDecimal.ZERO) == 0)) {
                        return AjaxResult.error("库位中还有库存不能删除");
                    }
                }
            }
        }
        return AjaxResult.success(wmStorageAreaMapper.deleteWmStorageAreaByAreaIds(areaIds));
    }

    /**
     * 删除库位设置信息
     * 
     * @param areaId 库位设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageAreaByAreaId(Long areaId)
    {
        return wmStorageAreaMapper.deleteWmStorageAreaByAreaId(areaId);
    }

    @Override
    public int deleteByWarehouseId(Long warehouseId) {
        return wmStorageAreaMapper.deleteByWarehouseId(warehouseId);
    }

    @Override
    public int deleteByLocationId(Long locationId) {
        return wmStorageAreaMapper.deleteByLocationId(locationId);
    }

    @Override
    public int deleteByLocationIds(Long[] locationIds) {
        return wmStorageAreaMapper.deleteByLocationIds(locationIds);
    }

    @Override
    public AjaxResult getLocationName(WmStorageArea area) {
        WmAreaPrintVO wmAreaPrintVO = new WmAreaPrintVO();
        // 获取库位的所在位置（仓库/库位）
        WmStorageLocation wmStorageLocation = wmStorageLocationService.selectWmStorageLocationByLocationId(area.getLocationId());
        WmWarehouse wmWarehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmStorageLocation.getWarehouseId());
        // 获取库位的二维码信息
        WmBarcode wmBarcode = new WmBarcode();
        wmBarcode.setBussinessId(area.getAreaId());
        wmBarcode.setBussinessCode(area.getAreaCode());
        wmBarcode.setBarcodeFormart("QR_CODE");
        wmBarcode.setBarcodeType("AREA");
        List<WmBarcode> wmBarcodes = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        if (!CollectionUtils.isEmpty(wmBarcodes)) {
            wmAreaPrintVO.setBarcodeContent(wmBarcodes.get(0).getBarcodeContent());
        } else {
            wmAreaPrintVO.setBarcodeContent("");
        }
        wmAreaPrintVO.setLocationName(wmWarehouse.getWarehouseName() + "/" + wmStorageLocation.getLocationName());
        return AjaxResult.success(wmAreaPrintVO);
    }
}
