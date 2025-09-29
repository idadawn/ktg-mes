package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.mes.wm.domain.WmBatch;
import com.ktg.mes.wm.service.IWmBatchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("赋码管理")
@RestController
@RequestMapping("/mobile/wm/batch")
public class WmBatchMobController extends BaseController {

    @Autowired
    private IWmBatchService wmBatchService;

    /**
     * 查询批次记录列表
     */
    @GetMapping("/list")
    public AjaxResult list(WmBatch wmBatch)
    {
        List<WmBatch> list = wmBatchService.selectWmBatchList(wmBatch);
        return AjaxResult.success(list);
    }

}
