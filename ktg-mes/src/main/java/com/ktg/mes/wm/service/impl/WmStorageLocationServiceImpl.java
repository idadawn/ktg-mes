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
import com.ktg.mes.wm.service.IWmMaterialStockService;
import com.ktg.mes.wm.service.IWmStorageAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.wm.mapper.WmStorageLocationMapper;
import com.ktg.mes.wm.domain.WmStorageLocation;
import com.ktg.mes.wm.service.IWmStorageLocationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库区设置Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-07
 */
@Service
public class WmStorageLocationServiceImpl implements IWmStorageLocationService 
{
    @Autowired
    private WmStorageLocationMapper wmStorageLocationMapper;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    /**
     * 查询库区设置
     * 
     * @param locationId 库区设置主键
     * @return 库区设置
     */
    @Override
    public WmStorageLocation selectWmStorageLocationByLocationId(Long locationId)
    {
        return wmStorageLocationMapper.selectWmStorageLocationByLocationId(locationId);
    }

    @Override
    public WmStorageLocation selectWmStorageLocationByLocationCode(String locationCode) {
        return wmStorageLocationMapper.selectWmStorageLocationByLocationCode(locationCode);
    }

    /**
     * 查询库区设置列表
     * 
     * @param wmStorageLocation 库区设置
     * @return 库区设置
     */
    @Override
    public List<WmStorageLocation> selectWmStorageLocationList(WmStorageLocation wmStorageLocation)
    {
        return wmStorageLocationMapper.selectWmStorageLocationList(wmStorageLocation);
    }

    @Override
    public String checkLocationCodeUnique(WmStorageLocation wmStorageLocation) {
        WmStorageLocation location = wmStorageLocationMapper.checkLocationCodeUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getLocationId()==null?-1L:wmStorageLocation.getLocationId();
        if(StringUtils.isNotNull(location) && location.getLocationId().longValue() != locationId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkLocationNameUnique(WmStorageLocation wmStorageLocation) {
        WmStorageLocation location = wmStorageLocationMapper.checkLocationNameUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getLocationId()==null?-1L:wmStorageLocation.getLocationId();
        if(StringUtils.isNotNull(location) && location.getLocationId().longValue() != locationId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增库区设置
     * 
     * @param wmStorageLocation 库区设置
     * @return 结果
     */
    @Override
    public int insertWmStorageLocation(WmStorageLocation wmStorageLocation)
    {
        wmStorageLocation.setCreateTime(DateUtils.getNowDate());
        return wmStorageLocationMapper.insertWmStorageLocation(wmStorageLocation);
    }

    /**
     * 修改库区设置
     * 
     * @param wmStorageLocation 库区设置
     * @return 结果
     */
    @Override
    public int updateWmStorageLocation(WmStorageLocation wmStorageLocation)
    {
        wmStorageLocation.setUpdateTime(DateUtils.getNowDate());
        return wmStorageLocationMapper.updateWmStorageLocation(wmStorageLocation);
    }

    /**
     * 批量删除库区设置
     *
     * @param locationIds 需要删除的库区设置主键
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult deleteWmStorageLocationByLocationIds(Long[] locationIds)
    {
        // 查询所有需要删除的数据
        List<WmStorageLocation> list = wmStorageLocationMapper.selectByLocationIds(locationIds);
        for (WmStorageLocation item : list) {
            String locationCode = item.getLocationCode();
            if (locationCode.contains("VIRTUAL")) {
                return AjaxResult.error("虚拟库区不能被删除");
            }
        }
        // 校验库区中是否存在物料
        for (Long locationId : locationIds) {
            // 根据库区查询相关数据
            List<WmMaterialStock> materialStocks = wmMaterialStockService.getLocationId(locationId);
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
        // 关联删除相关库位
        wmStorageAreaService.deleteByLocationIds(locationIds);
        wmStorageLocationMapper.deleteWmStorageLocationByLocationIds(locationIds);
        return AjaxResult.success();
    }

    /**
     * 删除库区设置信息
     * 
     * @param locationId 库区设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageLocationByLocationId(Long locationId)
    {
        return wmStorageLocationMapper.deleteWmStorageLocationByLocationId(locationId);
    }

    @Override
    public int deleteByWarehouseId(Long warehouseId) {
        return wmStorageLocationMapper.deleteByWarehouseId(warehouseId);
    }
}
