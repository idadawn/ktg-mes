package com.ktg.mes.qc.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.qc.domain.QcIqcLine;
import com.ktg.mes.qc.domain.QcOqcLine;
import com.ktg.mes.qc.service.IQcIqcLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动端来料检验单行接口
 */
@RestController
@RequestMapping("/mobile/qc/iqcline")
public class QCIqcLineMobController extends BaseController {

    @Autowired
    private IQcIqcLineService qcIqcLineService;

    /**
     * 查询来料检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIqcLine qcIqcLine)
    {
        startPage();
        List<QcIqcLine> list = qcIqcLineService.selectQcIqcLineList(qcIqcLine);
        return getDataTable(list);
    }

    /**
     * 获取来料检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcIqcLineService.selectQcIqcLineByLineId(lineId));
    }

    /**
     * 新增来料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqc:add')")
    @Log(title = "来料检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcIqcLine qcIqcLine)
    {
        qcIqcLine.setCreateBy(getUsername());
        qcIqcLineService.insertQcIqcLine(qcIqcLine);
        return AjaxResult.success(qcIqcLine);
    }

    /**
     * 修改来料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqc:edit')")
    @Log(title = "来料检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcIqcLine qcIqcLine)
    {
        return toAjax(qcIqcLineService.updateQcIqcLine(qcIqcLine));
    }

    /**
     * 删除来料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqc:remove')")
    @Log(title = "来料检验单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcIqcLineService.deleteQcIqcLineByLineIds(lineIds));
    }

}
