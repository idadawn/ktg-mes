package com.ktg.mes.wm.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 到货通知单行对象 wm_arrival_notice_line
 * 
 * @author yinjinlu
 * @date 2024-11-12
 */
public class WmArrivalNoticeLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 行ID */
    private Long lineId;

    /** 通知单ID */
    @Excel(name = "通知单ID")
    private Long noticeId;

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

    /** 到货数量 */
    @Excel(name = "到货数量")
    private BigDecimal quantityArrival;

    /** 合格数量 */
    @Excel(name = "合格数量")
    private BigDecimal quantityQuanlified;

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
    public void setNoticeId(Long noticeId) 
    {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() 
    {
        return noticeId;
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
    public void setQuantityArrival(BigDecimal quantityArrival) 
    {
        this.quantityArrival = quantityArrival;
    }

    public BigDecimal getQuantityArrival() 
    {
        return quantityArrival;
    }
    public void setQuantityQuanlified(BigDecimal quantityQuanlified) 
    {
        this.quantityQuanlified = quantityQuanlified;
    }

    public BigDecimal getQuantityQuanlified() 
    {
        return quantityQuanlified;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("lineId", getLineId())
            .append("noticeId", getNoticeId())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("specification", getSpecification())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("quantityArrival", getQuantityArrival())
            .append("quantityQuanlified", getQuantityQuanlified())
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
