package com.ktg.mes.wm.domain.vo;

import com.ktg.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleRecordVO {

    private static final long serialVersionUID = 1L;

    /** 行ID */
    private Long lineId;

    /** 出库记录ID */
    @Excel(name = "出库记录ID")
    private Long salseId;

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

    /** 出库数量 */
    @Excel(name = "出库数量")
    private BigDecimal quantitySalse;

    /** 批次号 */
    @Excel(name = "批次号")
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

    /** 出库单编号 */
    @Excel(name = "出库单编号")
    private String salseCode;

    /** 出库单名称 */
    @Excel(name = "出库单名称")
    private String salseName;

    /** 出货检验单ID */
    @Excel(name = "出货检验单ID")
    private Long oqcId;

    /** 出货检验单编号 */
    @Excel(name = "出货检验单编号")
    private String oqcCode;

    /** 销售订单编号 */
    @Excel(name = "销售订单编号")
    private String soCode;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long clientId;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String clientCode;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;

    /** 客户简称 */
    @Excel(name = "客户简称")
    private String clientNick;
}
