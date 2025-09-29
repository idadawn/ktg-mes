package com.ktg.mes.md.controller.mobile;

import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.mes.md.domain.MdProductSip;
import com.ktg.mes.md.service.IMdProductSipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/12/5
 */
@RestController
@RequestMapping("/mobile/md/sip")
public class MdProductSIPMobController extends BaseController {

    @Autowired
    private IMdProductSipService mdProductSipService;

    /**
     * 查询产品SIP列表
     */
    @GetMapping("/list")
    public AjaxResult list(MdProductSip mdProductSip)
    {
        List<MdProductSip> list = mdProductSipService.selectMdProductSipList(mdProductSip);
        return AjaxResult.success(list);
    }
}
