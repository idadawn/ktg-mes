package com.ktg.mes.report.bean;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmBarcode;
import com.ktg.mes.wm.service.IWmBarcodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class PmReportBean {

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IWmBarcodeService wmBarcodeService;
        public List<ProWorkorder> getData(String dsName, String datasetName, Map<String, Object> parameters) {

            String id =   MapUtils.getString(parameters, "id");
            if (StringUtil.isEmpty(id)) {
                id = "0";
            }
            ProWorkorder proWorkorder = proWorkorderService.selectProWorkorderByWorkorderId(Long.parseLong(id));

            List<ProWorkorder> list = new ArrayList<>();
            list.add(proWorkorder);
            log.info("PmReportBean dsName :" + dsName + ", datasetName :" +  datasetName + ", resp:" + JSON.toJSONString(list));
            return list;
        }
        public List<ProWorkorder> getChildData(String dsName, String datasetName, Map<String, Object> parameters) {

            String id =   MapUtils.getString(parameters, "id");
            if (StringUtil.isEmpty(id)) {
                id = "0";
            }
            List<ProWorkorder> proWorkorders = proWorkorderService.selectProWorkorderListByParentId(Long.parseLong(id));


            List<ProWorkorder> list = new ArrayList<>();
            log.info("PmReportBean dsName :" + dsName + ", datasetName :" +  datasetName + ", resp:" + JSON.toJSONString(list));
            return proWorkorders;
        }

    public List<WmBarcode> getQc(String dsName, String datasetName, Map<String, Object> parameters) {

        String id =   MapUtils.getString(parameters, "id");
        if (StringUtil.isEmpty(id)) {
            id = "0";
        }
        WmBarcode wmBarcode = new WmBarcode();
        wmBarcode.setBarcodeId(Long.parseLong(id));

        List<WmBarcode> codeList = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        List<WmBarcode> list = new ArrayList<>();

        if(!CollectionUtils.isEmpty(codeList)){
            list.add(codeList.get(0));
        }
        log.info("PmReportBean dsName :" + dsName + ", datasetName :" +  datasetName + ", resp:" + JSON.toJSONString(list));
        return list;
    }
}
