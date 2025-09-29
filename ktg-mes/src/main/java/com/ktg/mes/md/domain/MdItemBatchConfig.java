package com.ktg.mes.md.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 物料批次属性配置对象 md_item_batch_config
 * 
 * @author yinjinlu
 * @date 2025-02-05
 */
public class MdItemBatchConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 批次ID */
    private Long configId;

    /** 产品物料ID */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /** 生产日期 */
    @Excel(name = "生产日期")
    private String produceDateFlag;

    /** 有效期 */
    @Excel(name = "有效期")
    private String expireDateFlag;

    /** 入库日期 */
    @Excel(name = "入库日期")
    private String recptDateFlag;

    /** 供应商 */
    @Excel(name = "供应商")
    private String vendorFlag;

    /** 客户 */
    @Excel(name = "客户")
    private String clientFlag;

    /** 销售订单编号 */
    @Excel(name = "销售订单编号")
    private String coCodeFlag;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String poCodeFlag;

    /** 生产工单 */
    @Excel(name = "生产工单")
    private String workorderFlag;

    /** 生产任务 */
    @Excel(name = "生产任务")
    private String taskFlag;

    /** 工作站 */
    @Excel(name = "工作站")
    private String workstationFlag;

    /** 工具 */
    @Excel(name = "工具")
    private String toolFlag;

    /** 模具 */
    @Excel(name = "模具")
    private String moldFlag;

    /** 生产批号 */
    @Excel(name = "生产批号")
    private String lotNumberFlag;

    /** 质量状态 */
    @Excel(name = "质量状态")
    private String qualityStatusFlag;

    /** 生效状态 */
    @Excel(name = "生效状态")
    private String enableFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String attr1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String attr2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private Long attr3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private Long attr4;

    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setProduceDateFlag(String produceDateFlag) 
    {
        this.produceDateFlag = produceDateFlag;
    }

    public String getProduceDateFlag() 
    {
        return produceDateFlag;
    }
    public void setExpireDateFlag(String expireDateFlag) 
    {
        this.expireDateFlag = expireDateFlag;
    }

    public String getExpireDateFlag() 
    {
        return expireDateFlag;
    }
    public void setRecptDateFlag(String recptDateFlag) 
    {
        this.recptDateFlag = recptDateFlag;
    }

    public String getRecptDateFlag() 
    {
        return recptDateFlag;
    }
    public void setVendorFlag(String vendorFlag) 
    {
        this.vendorFlag = vendorFlag;
    }

    public String getVendorFlag() 
    {
        return vendorFlag;
    }
    public void setClientFlag(String clientFlag) 
    {
        this.clientFlag = clientFlag;
    }

    public String getClientFlag() 
    {
        return clientFlag;
    }
    public void setCoCodeFlag(String coCodeFlag) 
    {
        this.coCodeFlag = coCodeFlag;
    }

    public String getCoCodeFlag() 
    {
        return coCodeFlag;
    }
    public void setPoCodeFlag(String poCodeFlag) 
    {
        this.poCodeFlag = poCodeFlag;
    }

    public String getPoCodeFlag() 
    {
        return poCodeFlag;
    }
    public void setWorkorderFlag(String workorderFlag) 
    {
        this.workorderFlag = workorderFlag;
    }

    public String getWorkorderFlag() 
    {
        return workorderFlag;
    }
    public void setTaskFlag(String taskFlag) 
    {
        this.taskFlag = taskFlag;
    }

    public String getTaskFlag() 
    {
        return taskFlag;
    }
    public void setWorkstationFlag(String workstationFlag) 
    {
        this.workstationFlag = workstationFlag;
    }

    public String getWorkstationFlag() 
    {
        return workstationFlag;
    }
    public void setToolFlag(String toolFlag) 
    {
        this.toolFlag = toolFlag;
    }

    public String getToolFlag() 
    {
        return toolFlag;
    }
    public void setMoldFlag(String moldFlag) 
    {
        this.moldFlag = moldFlag;
    }

    public String getMoldFlag() 
    {
        return moldFlag;
    }
    public void setLotNumberFlag(String lotNumberFlag) 
    {
        this.lotNumberFlag = lotNumberFlag;
    }

    public String getLotNumberFlag() 
    {
        return lotNumberFlag;
    }
    public void setQualityStatusFlag(String qualityStatusFlag) 
    {
        this.qualityStatusFlag = qualityStatusFlag;
    }

    public String getQualityStatusFlag() 
    {
        return qualityStatusFlag;
    }
    public void setEnableFlag(String enableFlag) 
    {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() 
    {
        return enableFlag;
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
            .append("configId", getConfigId())
            .append("itemId", getItemId())
            .append("produceDateFlag", getProduceDateFlag())
            .append("expireDateFlag", getExpireDateFlag())
            .append("recptDateFlag", getRecptDateFlag())
            .append("vendorFlag", getVendorFlag())
            .append("clientFlag", getClientFlag())
            .append("coCodeFlag", getCoCodeFlag())
            .append("poCodeFlag", getPoCodeFlag())
            .append("workorderFlag", getWorkorderFlag())
            .append("taskFlag", getTaskFlag())
            .append("workstationFlag", getWorkstationFlag())
            .append("toolFlag", getToolFlag())
            .append("moldFlag", getMoldFlag())
            .append("lotNumberFlag", getLotNumberFlag())
            .append("qualityStatusFlag", getQualityStatusFlag())
            .append("enableFlag", getEnableFlag())
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
