package com.ktg.mes.qc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 检测结果记录对象 qc_result
 * 
 * @author yinjinlu
 * @date 2024-11-29
 */
public class QcResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long resultId;

    /** 记录编号 */
    @Excel(name = "记录编号")
    private String resultCode;

    /** 关联的质检单ID */
    @Excel(name = "关联的质检单ID")
    private Long sourceDocId;

    /** 关联的质检单编号 */
    @Excel(name = "关联的质检单编号")
    private String sourceDocCode;

    /** 关联的质检单名称 */
    @Excel(name = "关联的质检单名称")
    private String sourceDocName;

    /** 关联的质检单类型 */
    @Excel(name = "关联的质检单类型")
    private String sourceDocType;

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

    /** 对应的物资SN */
    @Excel(name = "对应的物资SN")
    private String snCode;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    private List<QcResultDetail> items;

    public void setResultId(Long resultId) 
    {
        this.resultId = resultId;
    }

    public Long getResultId() 
    {
        return resultId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setSourceDocId(Long sourceDocId)
    {
        this.sourceDocId = sourceDocId;
    }

    public Long getSourceDocId() 
    {
        return sourceDocId;
    }
    public void setSourceDocCode(String sourceDocCode) 
    {
        this.sourceDocCode = sourceDocCode;
    }

    public String getSourceDocCode() 
    {
        return sourceDocCode;
    }
    public void setSourceDocName(String sourceDocName) 
    {
        this.sourceDocName = sourceDocName;
    }

    public String getSourceDocName() 
    {
        return sourceDocName;
    }
    public void setSourceDocType(String sourceDocType) 
    {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocType() 
    {
        return sourceDocType;
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
    public void setSnCode(String snCode) 
    {
        this.snCode = snCode;
    }

    public String getSnCode() 
    {
        return snCode;
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

    public List<QcResultDetail> getItems() {
        return items;
    }

    public void setItems(List<QcResultDetail> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("resultId", getResultId())
            .append("resultCode", getResultCode())
            .append("sourceDocId", getSourceDocId())
            .append("sourceDocCode", getSourceDocCode())
            .append("sourceDocName", getSourceDocName())
            .append("sourceDocType", getSourceDocType())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("specification", getSpecification())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("snCode", getSnCode())
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
