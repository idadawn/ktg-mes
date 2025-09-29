package com.ktg.mes.wm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 外协入库单行对象 wm_outsource_recpt_line
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
public class WmOutsourceRecptLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 行ID */
    private Long lineId;

    /** 入库单ID */
    @Excel(name = "入库单ID")
    private Long recptId;

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

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal quantityRecived;

    private BigDecimal quantity;

    /** 批次ID */
    private Long batchId;

    /** 入库批次号 */
    @Excel(name = "入库批次号")
    private String batchCode;

    /** 有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date produceDate;

    @Excel(name = "生产批号")
    private String lotNumber;

    /** 质量状态 */
    private String qualityStatus;

    /** 是否来料检验 */
    @Excel(name = "是否来料检验")
    private String iqcCheck;

    /** 来料检验单ID */
    @Excel(name = "来料检验单ID")
    private Long iqcId;

    /** 来料检验单编号 */
    @Excel(name = "来料检验单编号")
    private String iqcCode;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    List<WmOutsourceRecptDetail> details;

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
    public void setRecptId(Long recptId) 
    {
        this.recptId = recptId;
    }

    public Long getRecptId() 
    {
        return recptId;
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
    public void setQuantityRecived(BigDecimal quantityRecived) 
    {
        this.quantityRecived = quantityRecived;
    }

    public BigDecimal getQuantityRecived() 
    {
        return quantityRecived;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getQualityStatus() {
        return qualityStatus;
    }

    public void setQualityStatus(String qualityStatus) {
        this.qualityStatus = qualityStatus;
    }

    public void setBatchCode(String batchCode)
    {
        this.batchCode = batchCode;
    }

    public String getBatchCode() 
    {
        return batchCode;
    }
    public void setExpireDate(Date expireDate) 
    {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() 
    {
        return expireDate;
    }
    public void setIqcCheck(String iqcCheck) 
    {
        this.iqcCheck = iqcCheck;
    }

    public String getIqcCheck() 
    {
        return iqcCheck;
    }
    public void setIqcId(Long iqcId) 
    {
        this.iqcId = iqcId;
    }

    public Long getIqcId() 
    {
        return iqcId;
    }
    public void setIqcCode(String iqcCode) 
    {
        this.iqcCode = iqcCode;
    }

    public String getIqcCode() 
    {
        return iqcCode;
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

    public List<WmOutsourceRecptDetail> getDetails() {
        return details;
    }

    public void setDetails(List<WmOutsourceRecptDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("lineId", getLineId())
            .append("recptId", getRecptId())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("specification", getSpecification())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("unitName", getUnitName())
            .append("quantityRecived", getQuantityRecived())
            .append("batchCode", getBatchCode())
            .append("expireDate", getExpireDate())
            .append("iqcCheck", getIqcCheck())
            .append("iqcId", getIqcId())
            .append("iqcCode", getIqcCode())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
