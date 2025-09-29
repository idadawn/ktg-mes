package com.ktg.mes.wm.domain;

import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

public class WmPosition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 库位ID */
    private Long areaId;

    /** 库位编码 */
    private String areaCode;

    /** 库位名称 */
    private String areaName;

    /** 是否冻结 */
    private String frozenFlag;

    /** 是否允许产品混放 */
    private String productMixing;

    /** 是否允许批次混放 */
    private String batchMixing;

    /** 库区ID */
    private Long locationId;

    private String locationCode;

    private String locationName;

    private Long warehouseId;

    private String warehouseCode;

    private String warehouseName;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getProductMixing() {
        return productMixing;
    }

    public void setProductMixing(String productMixing) {
        this.productMixing = productMixing;
    }

    public String getBatchMixing() {
        return batchMixing;
    }

    public void setBatchMixing(String batchMixing) {
        this.batchMixing = batchMixing;
    }

    @Override
    public String toString() {
        return "WmPosition{" +
                "areaId=" + areaId +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", locationId=" + locationId +
                ", locationCode='" + locationCode + '\'' +
                ", locationName='" + locationName + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
