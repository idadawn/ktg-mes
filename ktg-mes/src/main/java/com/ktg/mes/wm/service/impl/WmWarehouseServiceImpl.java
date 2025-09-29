package com.ktg.mes.wm.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.wm.domain.WmMaterialStock;
import com.ktg.mes.wm.domain.WmStorageArea;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.mapper.WmStorageAreaMapper;
import com.ktg.mes.wm.mapper.WmStorageLocationMapper;
import com.ktg.mes.wm.service.IWmMaterialStockService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmWarehouseMapper;
import com.ktg.mes.wm.domain.WmWarehouse;
import com.ktg.mes.wm.service.IWmWarehouseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓库设置Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-07
 */
@Service
public class WmWarehouseServiceImpl implements IWmWarehouseService 
{
    @Autowired
    private WmWarehouseMapper wmWarehouseMapper;

    @Autowired
    private WmStorageLocationMapper wmStorageLocationMapper;

    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询仓库设置
     * 
     * @param warehouseId 仓库设置主键
     * @return 仓库设置
     */
    @Override
    public WmWarehouse selectWmWarehouseByWarehouseId(Long warehouseId)
    {
        return wmWarehouseMapper.selectWmWarehouseByWarehouseId(warehouseId);
    }

    @Override
    public WmWarehouse selectWmWarehouseByWarehouseCode(String warehouseCdoe) {
        return wmWarehouseMapper.selectWmWarehouseByWarehouseCode(warehouseCdoe);
    }

    /**
     * 查询仓库设置列表
     * 
     * @param wmWarehouse 仓库设置
     * @return 仓库设置
     */
    @Override
    public List<WmWarehouse> selectWmWarehouseList(WmWarehouse wmWarehouse)
    {
        return wmWarehouseMapper.selectWmWarehouseList(wmWarehouse);
    }

    @Override
    public List<WmWarehouse> getTreeList() {

        return wmWarehouseMapper.getTreeList();
    }

    @Override
    public String checkWarehouseCodeUnique(WmWarehouse wmWarehouse) {
        WmWarehouse warehouse = wmWarehouseMapper.checkWarehouseCodeUnique(wmWarehouse);
        Long warehouseId = wmWarehouse.getWarehouseId()==null?-1L:wmWarehouse.getWarehouseId();
        if(StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId().longValue() != warehouseId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkWarehouseNameUnique(WmWarehouse wmWarehouse) {
        WmWarehouse warehouse = wmWarehouseMapper.checkWarehouseNameUnique(wmWarehouse);
        Long warehouseId = wmWarehouse.getWarehouseId()==null?-1L:wmWarehouse.getWarehouseId();
        if(StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId().longValue() != warehouseId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增仓库设置
     * 
     * @param wmWarehouse 仓库设置
     * @return 结果
     */
    @Override
    public int insertWmWarehouse(WmWarehouse wmWarehouse)
    {
        wmWarehouse.setCreateTime(DateUtils.getNowDate());
        return wmWarehouseMapper.insertWmWarehouse(wmWarehouse);
    }

    /**
     * 修改仓库设置
     * 
     * @param wmWarehouse 仓库设置
     * @return 结果
     */
    @Override
    public int updateWmWarehouse(WmWarehouse wmWarehouse)
    {
        wmWarehouse.setUpdateTime(DateUtils.getNowDate());
        return wmWarehouseMapper.updateWmWarehouse(wmWarehouse);
    }

    /**
     * 批量删除仓库设置
     *
     * @param warehouseIds 需要删除的仓库设置主键
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult deleteWmWarehouseByWarehouseIds(Long[] warehouseIds)
    {
        // 查询所有需要删除的数据
        List<WmWarehouse> warehouseList = wmWarehouseMapper.selectByWarehouseId(warehouseIds);
        for (WmWarehouse item : warehouseList) {
            String warehouseCode = item.getWarehouseCode();
            if (warehouseCode.contains("VIRTUAL")) {
                return AjaxResult.error("虚拟仓库不能被删除");
            }
        }
        // 校验仓库中是否存在物料
        for (Long warehouseId : warehouseIds) {
            // 根据仓库id查询相关数据
            List<WmMaterialStock> materialStocks = wmMaterialStockService.getByWarehouseId(warehouseId);
            if (materialStocks != null && materialStocks.size() > 0) {
                Map<Long, List<WmMaterialStock>> collect = materialStocks.stream()
                        .collect(Collectors.groupingBy(WmMaterialStock::getItemId));
                for (Long l : collect.keySet()) {
                    List<WmMaterialStock> wmMaterialStocks = collect.get(l);
                    BigDecimal reduce = wmMaterialStocks.stream().map(WmMaterialStock::getQuantityOnhand).reduce(BigDecimal.ZERO, BigDecimal::add);
                    if (!(reduce.compareTo(BigDecimal.ZERO) == 0)) {
                        return AjaxResult.error("库区中还有库存不能删除");
                    }
                }
            }
        }
        for (Long wahouseId: warehouseIds) {
            wmStorageAreaService.deleteByWarehouseId(wahouseId);
            wmStorageLocationService.deleteByWarehouseId(wahouseId);
        }
        return AjaxResult.success(wmWarehouseMapper.deleteWmWarehouseByWarehouseIds(warehouseIds));
    }

    /**
     * 删除仓库设置信息
     * 
     * @param warehouseId 仓库设置主键
     * @return 结果
     */
    @Override
    public int deleteWmWarehouseByWarehouseId(Long warehouseId)
    {
        return wmWarehouseMapper.deleteWmWarehouseByWarehouseId(warehouseId);
    }

    @Override
    public WmWarehouse initVirtualWarehouse() {

        WmWarehouse warehouse = wmWarehouseMapper.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
        if(!StringUtils.isNotNull(warehouse)){
            warehouse = new WmWarehouse();
            warehouse.setWarehouseCode(UserConstants.VIRTUAL_WH);
            warehouse.setWarehouseName("线边库-虚拟");
            wmWarehouseMapper.insertWmWarehouse(warehouse);
        }


        WmStorageLocation location = wmStorageLocationMapper.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
        if(!StringUtils.isNotNull(location)){
            location = new WmStorageLocation();
            location.setWarehouseId(warehouse.getWarehouseId());
            location.setLocationCode(UserConstants.VIRTUAL_WS);
            location.setLocationName("线边库库区-虚拟");
            location.setAreaFlag(UserConstants.YES);
            wmStorageLocationMapper.insertWmStorageLocation(location);
        }


        WmStorageArea area = wmStorageAreaMapper.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
        if(!StringUtils.isNotNull(area)){
            area = new WmStorageArea();
            area.setLocationId(location.getLocationId());
            area.setAreaCode(UserConstants.VIRTUAL_WA);
            area.setAreaName("线边库库位-虚拟");
            wmStorageAreaMapper.insertWmStorageArea(area);
        }

        return warehouse;
    }
}
