package com.ktg.print.service;

import com.ktg.print.domain.PrintBarcodeModel;
import com.ktg.print.domain.PrintTemplate;

import java.util.List;
import java.util.Map;

/**
 * 打印模板配置Service接口
 *
 * @author yanshikui
 * @date 2024-04-17
 */
public interface IPrintService
{
    /**
     * 标签打印
     * @param printBarcodeModel
     * @return
     */
    public Map<String,Object> printLabel(PrintBarcodeModel printBarcodeModel);

}
