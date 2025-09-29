package com.ktg.mes.qc.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 来料检验单对象 qc_iqc
 * 
 * @author yinjinlu
 * @date 2022-05-19
 */
public class QcIqc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 来料检验单ID */
    private Long iqcId;

    /** 来料检验单编号 */
    @Excel(name = "来料检验单编号")
    private String iqcCode;

    /** 来料检验单名称 */
    @Excel(name = "来料检验单名称")
    private String iqcName;

    /** 检验模板ID */
    @Excel(name = "检验模板ID")
    private Long templateId;

    private Long sourceDocId;

    @Excel(name = "来源单据类型")
    private String sourceDocType;

    @Excel(name = "来源单据编号")
    private String sourceDocCode;

    private Long sourceLineId;


    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String vendorCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String vendorName;

    /** 供应商简称 */
    @Excel(name = "供应商简称")
    private String vendorNick;

    /** 供应商批次号 */
    @Excel(name = "供应商批次号")
    private String vendorBatch;

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

    /** 最低检测数 */
    @Excel(name = "最低检测数")
    private BigDecimal quantityMinCheck;

    /** 最大不合格数 */
    @Excel(name = "最大不合格数")
    private BigDecimal quantityMaxUnqualified;

    /** 本次接收数量 */
    @Excel(name = "本次接收数量")
    private BigDecimal quantityRecived;

    /** 本次检测数量 */
    @Excel(name = "本次检测数量")
    private BigDecimal quantityCheck;

    /**
     * 合格品数量
     */
    @Excel(name = "合格品数量")
    private BigDecimal quantityQualified;

    /** 不合格数 */
    @Excel(name = "不合格数")
    private BigDecimal quantityUnqualified;

    /** 致命缺陷率 */
    @Excel(name = "致命缺陷率")
    private BigDecimal crRate;

    /** 严重缺陷率 */
    @Excel(name = "严重缺陷率")
    private BigDecimal majRate;

    /** 轻微缺陷率 */
    @Excel(name = "轻微缺陷率")
    private BigDecimal minRate;

    /** 致命缺陷数量 */
    @Excel(name = "致命缺陷数量")
    private BigDecimal crQuantity;

    /** 严重缺陷数量 */
    @Excel(name = "严重缺陷数量")
    private BigDecimal majQuantity;

    /** 轻微缺陷数量 */
    @Excel(name = "轻微缺陷数量")
    private BigDecimal minQuantity;

    /** 检测结果 */
    @Excel(name = "检测结果")
    private String checkResult;

    /** 来料日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "来料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reciveDate;

    /** 检测日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectDate;

    /** 检测人员 */
    @Excel(name = "检测人员")
    private String inspector;

    private String inspectorName;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private String status;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
    public void setIqcName(String iqcName) 
    {
        this.iqcName = iqcName;
    }

    public String getIqcName() 
    {
        return iqcName;
    }
    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public Long getSourceDocId() {
        return sourceDocId;
    }

    public void setSourceDocId(Long sourceDocId) {
        this.sourceDocId = sourceDocId;
    }

    public String getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(String sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocCode() {
        return sourceDocCode;
    }

    public void setSourceDocCode(String sourceDocCode) {
        this.sourceDocCode = sourceDocCode;
    }

    public Long getSourceLineId() {
        return sourceLineId;
    }

    public void setSourceLineId(Long sourceLineId) {
        this.sourceLineId = sourceLineId;
    }

    public void setVendorId(Long vendorId)
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }
    public void setVendorCode(String vendorCode) 
    {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() 
    {
        return vendorCode;
    }
    public void setVendorName(String vendorName) 
    {
        this.vendorName = vendorName;
    }

    public String getVendorName() 
    {
        return vendorName;
    }
    public void setVendorNick(String vendorNick) 
    {
        this.vendorNick = vendorNick;
    }

    public String getVendorNick() 
    {
        return vendorNick;
    }
    public void setVendorBatch(String vendorBatch) 
    {
        this.vendorBatch = vendorBatch;
    }

    public String getVendorBatch() 
    {
        return vendorBatch;
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
    public void setQuantityMinCheck(BigDecimal quantityMinCheck)
    {
        this.quantityMinCheck = quantityMinCheck;
    }

    public BigDecimal getQuantityMinCheck()
    {
        return quantityMinCheck;
    }
    public void setQuantityMaxUnqualified(BigDecimal quantityMaxUnqualified)
    {
        this.quantityMaxUnqualified = quantityMaxUnqualified;
    }

    public BigDecimal getQuantityMaxUnqualified()
    {
        return quantityMaxUnqualified;
    }
    public void setQuantityRecived(BigDecimal quantityRecived) 
    {
        this.quantityRecived = quantityRecived;
    }

    public BigDecimal getQuantityRecived() 
    {
        return quantityRecived;
    }
    public void setQuantityCheck(BigDecimal quantityCheck)
    {
        this.quantityCheck = quantityCheck;
    }

    public BigDecimal getQuantityCheck()
    {
        return quantityCheck;
    }

    public BigDecimal getQuantityQualified() {
        return quantityQualified;
    }

    public void setQuantityQualified(BigDecimal quantityQualified) {
        this.quantityQualified = quantityQualified;
    }

    public void setQuantityUnqualified(BigDecimal quantityUnqualified)
    {
        this.quantityUnqualified = quantityUnqualified;
    }

    public BigDecimal getQuantityUnqualified()
    {
        return quantityUnqualified;
    }
    public void setCrRate(BigDecimal crRate) 
    {
        this.crRate = crRate;
    }

    public BigDecimal getCrRate() 
    {
        return crRate;
    }
    public void setMajRate(BigDecimal majRate) 
    {
        this.majRate = majRate;
    }

    public BigDecimal getMajRate() 
    {
        return majRate;
    }
    public void setMinRate(BigDecimal minRate) 
    {
        this.minRate = minRate;
    }

    public BigDecimal getMinRate() 
    {
        return minRate;
    }
    public void setCrQuantity(BigDecimal crQuantity)
    {
        this.crQuantity = crQuantity;
    }

    public BigDecimal getCrQuantity()
    {
        return crQuantity;
    }
    public void setMajQuantity(BigDecimal majQuantity)
    {
        this.majQuantity = majQuantity;
    }

    public BigDecimal getMajQuantity()
    {
        return majQuantity;
    }
    public void setMinQuantity(BigDecimal minQuantity)
    {
        this.minQuantity = minQuantity;
    }

    public BigDecimal getMinQuantity()
    {
        return minQuantity;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setReciveDate(Date reciveDate) 
    {
        this.reciveDate = reciveDate;
    }

    public Date getReciveDate() 
    {
        return reciveDate;
    }
    public void setInspectDate(Date inspectDate) 
    {
        this.inspectDate = inspectDate;
    }

    public Date getInspectDate() 
    {
        return inspectDate;
    }
    public void setInspector(String inspector) 
    {
        this.inspector = inspector;
    }

    public String getInspector() 
    {
        return inspector;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getStatus()
    {
        return status;
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

    @Override
    public String toString() {
        return "QcIqc{" +
                "iqcId=" + iqcId +
                ", iqcCode='" + iqcCode + '\'' +
                ", iqcName='" + iqcName + '\'' +
                ", templateId=" + templateId +
                ", vendorId=" + vendorId +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorNick='" + vendorNick + '\'' +
                ", vendorBatch='" + vendorBatch + '\'' +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specification='" + specification + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", unitName='" + unitName + "\'" +
                ", quantityMinCheck=" + quantityMinCheck +
                ", quantityMaxUnqualified=" + quantityMaxUnqualified +
                ", quantityRecived=" + quantityRecived +
                ", quantityCheck=" + quantityCheck +
                ", quantityUnqualified=" + quantityUnqualified +
                ", crRate=" + crRate +
                ", majRate=" + majRate +
                ", minRate=" + minRate +
                ", crQuantity=" + crQuantity +
                ", majQuantity=" + majQuantity +
                ", minQuantity=" + minQuantity +
                ", checkResult='" + checkResult + '\'' +
                ", reciveDate=" + reciveDate +
                ", inspectDate=" + inspectDate +
                ", inspector='" + inspector + '\'' +
                ", inspectorName='" + inspectorName + '\'' +
                ", status='" + status + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                '}';
    }
}
