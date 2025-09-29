package com.ktg.print.domain;

import lombok.Data;

import java.util.Map;

/**
 * @author yanshikui
 */
@Data
public class PrintBarcodeModel {
    private Map<String, String> params;
    private String printerCode;
    private String businessType;
    private Long businessId;
    private String businessCode;
}
