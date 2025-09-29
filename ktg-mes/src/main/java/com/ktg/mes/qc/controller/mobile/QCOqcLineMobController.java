package com.ktg.mes.qc.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.qc.domain.QcOqcLine;
import com.ktg.mes.qc.service.IQcOqcLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动端出厂检验单行接口
 */
@RestController
@RequestMapping("/mobile/qc/oqcline")
public class QCOqcLineMobController extends BaseController {

    @Autowired
    private IQcOqcLineService qcOqcLineService;

    /**
     * 查询出货检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcOqcLine qcOqcLine)
    {
        startPage();
        List<QcOqcLine> list = qcOqcLineService.selectQcOqcLineList(qcOqcLine);
        return getDataTable(list);
    }

    /**
     * 获取出货检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcOqcLineService.selectQcOqcLineByLineId(lineId));
    }

    /**
     * 新增出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:add')")
    @Log(title = "出货检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcOqcLine qcOqcLine)
    {
        qcOqcLine.setCreateBy(getUsername());
        qcOqcLineService.insertQcOqcLine(qcOqcLine);
        return AjaxResult.success(qcOqcLine);
    }

    /**
     * 修改出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:edit')")
    @Log(title = "出货检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcOqcLine qcOqcLine)
    {
        return toAjax(qcOqcLineService.updateQcOqcLine(qcOqcLine));
    }

    /**
     * 删除出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:remove')")
    @Log(title = "出货检验单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcOqcLineService.deleteQcOqcLineByLineIds(lineIds));
    }

}
