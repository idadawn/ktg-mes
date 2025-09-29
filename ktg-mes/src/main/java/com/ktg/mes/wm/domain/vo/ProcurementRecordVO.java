package com.ktg.mes.wm.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ktg.common.annotation.Excel;
import com.ktg.mes.wm.domain.WmItemRecptLine;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProcurementRecordVO extends WmItemRecptLine {

    private static final long serialVersionUID = 1L;

    /** 入库单ID */
    @Excel(name = "入库单ID")
    private Long recptId;

    /**
     * 到货通知单行ID
     */
    private Long noticeLineId;

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

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal quantityRecived;

    /** 入库批次号 */
    @Excel(name = "入库批次号")
    private String batchCode;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 库区ID */
    @Excel(name = "库区ID")
    private Long locationId;

    /** 库区编码 */
    @Excel(name = "库区编码")
    private String locationCode;

    /** 库区名称 */
    @Excel(name = "库区名称")
    private String locationName;

    /** 库位ID */
    @Excel(name = "库位ID")
    private Long areaId;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String areaCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String areaName;

    /** 有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 是否来料检验
     */
    private String iqcCheck;

    /**
     * 来料检验单ID
     */
    private Long iqcId;

    /**
     * 来料检验单编码
     */
    private String iqcCode;

    /** 入库单编号 */
    @Excel(name = "入库单编号")
    private String recptCode;

    /** 入库单名称 */
    @Excel(name = "入库单名称")
    private String recptName;

    /**
     * 到货通知单ID
     */
    private Long noticeId;

    /**
     * 到货通知单编号
     */
    private String noticeCode;


    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String poCode;

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

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recptDate;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private String status;
}
