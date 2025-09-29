package com.ktg.mes.wm.domain;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 产品销售出库行对象 wm_product_salse_line
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
public class WmProductSalesLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 行ID */
    private Long lineId;

    /** 出库记录ID */
    @Excel(name = "出库记录ID")
    private Long salesId;

    /** 库存记录ID */
    @Excel(name = "库存记录ID")
    private Long materialStockId;

    /** 产品物料ID */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /** 产品物料编码 */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /** 产品物料名称 */
    @Excel(name = "产品物料名称")
    private String itemName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /** 单位名称 */
    private String unitName;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private BigDecimal quantitySales;

    private BigDecimal quantity;

    /** 批次ID */
    private Long batchId;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchCode;

    /**
     * 是否出厂检验
     */
    private String oqcCheck;

    /**
     * 出厂检验单ID
     */
    private Long oqcId;

    /**
     * 出厂检验单编码
     */
    private String oqcCode;

    /**
     * 质量状态
     */
    private String qualityStatus;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    private List<WmProductSalesDetail> details;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setLineId(Long lineId)
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public void setMaterialStockId(Long materialStockId)
    {
        this.materialStockId = materialStockId;
    }

    public Long getMaterialStockId() 
    {
        return materialStockId;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setUnitOfMeasure(String unitOfMeasure) 
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() 
    {
        return unitOfMeasure;
    }
    public void setQuantitySales(BigDecimal quantitySales) 
    {
        this.quantitySales = quantitySales;
    }

    public BigDecimal getQuantitySales() 
    {
        return quantitySales;
    }
    public void setBatchCode(String batchCode) 
    {
        this.batchCode = batchCode;
    }

    public String getBatchCode() 
    {
        return batchCode;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getQualityStatus() {
        return qualityStatus;
    }

    public void setQualityStatus(String qualityStatus) {
        this.qualityStatus = qualityStatus;
    }

    public String getOqcCheck() {
        return oqcCheck;
    }

    public void setOqcCheck(String oqcCheck) {
        this.oqcCheck = oqcCheck;
    }

    public Long getOqcId() {
        return oqcId;
    }

    public void setOqcId(Long oqcId) {
        this.oqcId = oqcId;
    }

    public String getOqcCode() {
        return oqcCode;
    }

    public void setOqcCode(String oqcCode) {
        this.oqcCode = oqcCode;
    }

    public void setAttr1(String attr1)
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public List<WmProductSalesDetail> getDetails() {
        return details;
    }

    public void setDetails(List<WmProductSalesDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "WmProductSalesLine{" +
                "lineId=" + lineId +
                ", materialStockId=" + materialStockId +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specification='" + specification + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", unitName='" + unitName + '\'' +
                ", quantitySales=" + quantitySales +
                ", batchCode='" + batchCode + '\'' +
                ", oqcCheck='" + oqcCheck + '\'' +
                ", oqcId=" + oqcId +
                ", oqcCode='" + oqcCode + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                '}';
    }
}
