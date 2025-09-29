package com.ktg.mes.qc.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.qc.domain.QcResultDetail;
import com.ktg.mes.qc.domain.ValidList;
import com.ktg.mes.qc.service.IQcResultDetailService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/12/9
 */
@RestController
@RequestMapping("/mobile/qc/qcresultdetail")
public class QCResultDetailMobController extends BaseController {

    @Autowired
    private IQcResultDetailService qcResultDetailService;

    /**
     * 查询检测结果明细记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcResultDetail qcResultDetail)
    {
        startPage();
        List<QcResultDetail> list = qcResultDetailService.selectQcResultDetailList(qcResultDetail);
        return getDataTable(list);
    }

    /**
     * 修改检验单结果
     */
    @Log(title = "更新质检记录列表", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult updateList(@Validated @RequestBody ValidList<QcResultDetail> defects){
        if(CollectionUtils.isNotEmpty(defects)){
            for(QcResultDetail index: defects){
                switch (index.getQcResultType()){
                    case UserConstants.QC_RESULT_TYPE_FLOAT:
                        if(StringUtils.isNull(index.getQcValFloat())){
                            return AjaxResult.error("请填写检测值!【"+index.getIndexName()+"】");
                        }
                        break;
                    case UserConstants.QC_RESULT_TYPE_INTEGER:
                        if(StringUtils.isNull(index.getQcValInteger())){
                            return AjaxResult.error("请填写检测值!【"+index.getIndexName()+"】");
                        }
                        break;
                    case UserConstants.QC_RESULT_TYPE_TEXT:
                        if(StringUtils.isNull(index.getQcValText())){
                            return AjaxResult.error("请填写检测值!【"+index.getIndexName()+"】");
                        }
                        break;
                    case UserConstants.QC_RESULT_TYPE_DICT:
                        if(StringUtils.isNull(index.getQcValDict())){
                            return AjaxResult.error("请填写检测值!【"+index.getIndexName()+"】");
                        }
                        break;
                    case UserConstants.QC_RESULT_TYPE_FILE:
                        if(StringUtils.isNull(index.getQcValFile())){
                            return AjaxResult.error("请填写检测值!【"+index.getIndexName()+"】");
                        }
                        break;
                    default:
                        break;
                }
                qcResultDetailService.updateQcResultDetail(index);
            }
        }
        return  AjaxResult.success();
    }


}
