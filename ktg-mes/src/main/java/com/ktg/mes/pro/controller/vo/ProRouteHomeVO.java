package com.ktg.mes.pro.controller.vo;

import com.ktg.mes.pro.domain.ProRouteProcess;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProRouteHomeVO extends ProRouteProcess {

    /**
     * 完成数量
     */
    private BigDecimal completeNumber;

    /**
     * 未完成数量
     */
    private BigDecimal incompleteNumber;

    /**
     * 总数
     */
    private BigDecimal total;

}
