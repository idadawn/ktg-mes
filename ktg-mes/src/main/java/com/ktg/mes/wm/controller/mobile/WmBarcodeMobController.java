package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.mes.dv.service.IDvMachineryService;
import com.ktg.mes.md.service.IMdClientService;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.mes.md.service.IMdVendorService;
import com.ktg.mes.md.service.IMdWorkstationService;
import com.ktg.mes.pro.service.IProWorkorderService;
import com.ktg.mes.wm.domain.WmBarcode;
import com.ktg.mes.wm.domain.vo.BarcodeObjResultVO;
import com.ktg.mes.wm.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/12/5
 */
@Api("赋码管理")
@RestController
@RequestMapping("/mobile/wm/barcode")
public class WmBarcodeMobController {
    @Autowired
    private IWmBarcodeService wmBarcodeService;

    @Autowired
    private IMdItemService mdItemService;

    @Autowired
    private IWmBatchService wmBatchService;

    @Autowired
    private IMdVendorService mdVendorService;

    @Autowired
    private IMdClientService mdClientService;

    @Autowired
    private IDvMachineryService dvMachineryService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IWmPackageService wmPackageService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IWmSnService wmSnService;

    /**
     * 获取某个对象的二维码地址
     * @return
     */
    @GetMapping("/getBarcodeUrl")
    public AjaxResult getBarcodeUrl(WmBarcode wmBarcode){
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        if(!CollectionUtils.isEmpty(list)){
            return AjaxResult.success(list.get(0));
        }
        return AjaxResult.success();
    }


    /**
     * 根据扫描的条码信息获取对应的对象信息
     * @param barcode
     * @return
     */
    @ApiOperation("根据扫描到的赋码内容查询对应的对象信息")
    @GetMapping("/getObjectByBarcode/{barcode}")
    public AjaxResult getBarcodeObject( @PathVariable String barcode){
        WmBarcode wmBarcode = new WmBarcode();
        wmBarcode.setBarcodeContent(barcode);
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        if(!CollectionUtils.isEmpty(list)){
            WmBarcode barcodeObject = list.get(0);
            Object object = getObjectByBarcode(barcodeObject);
            if(object!= null){
                return AjaxResult.success(object);
            }else{
                return AjaxResult.error("赋码信息无效!");
            }
        }else{
            return AjaxResult.error("赋码信息无效!");
        }
    }

    private Object getObjectByBarcode(WmBarcode wmBarcode){
        BarcodeObjResultVO result = new BarcodeObjResultVO();
        switch (wmBarcode.getBarcodeType()){
            case UserConstants.BARCODE_TYPE_ITEM:
                result.setObjectType(UserConstants.BARCODE_TYPE_ITEM);
                result.setObject(mdItemService.selectMdItemById(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_BATCH:
                result.setObjectType(UserConstants.BARCODE_TYPE_BATCH);
                result.setObject(wmBatchService.selectWmBatchByBatchId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_PACKAGE:
                result.setObjectType(UserConstants.BARCODE_TYPE_PACKAGE);
                result.setObject(wmPackageService.selectWmPackageByPackageId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_CLIENT:
                result.setObjectType(UserConstants.BARCODE_TYPE_CLIENT);
                result.setObject(mdClientService.selectMdClientByClientId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_VENDOR:
                result.setObjectType(UserConstants.BARCODE_TYPE_VENDOR);
                result.setObject(mdVendorService.selectMdVendorByVendorId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_MACHINERY:
                result.setObjectType(UserConstants.BARCODE_TYPE_MACHINERY);
                result.setObject(dvMachineryService.selectDvMachineryByMachineryId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_SN:
                result.setObjectType(UserConstants.BARCODE_TYPE_SN);
                result.setObject(wmSnService.selectWmSnBySnId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_STORAGEAREA:
                result.setObjectType(UserConstants.BARCODE_TYPE_STORAGEAREA);
                //返回的是WmPosition信息
                result.setObject(wmStorageAreaService.selectWmStorageAreaFullInfoByAreaId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_WORKORDER:
                result.setObjectType(UserConstants.BARCODE_TYPE_WORKORDER);
                result.setObject(proWorkorderService.selectProWorkorderByWorkorderId(wmBarcode.getBussinessId()));
                break;
            case UserConstants.BARCODE_TYPE_WORKSTATION:
                result.setObjectType(UserConstants.BARCODE_TYPE_WORKSTATION);
                result.setObject(mdWorkstationService.selectMdWorkstationByWorkstationId(wmBarcode.getBussinessId()));
                break;
            default:
                return null;
        }
        return result;
    }
}
