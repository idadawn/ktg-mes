package com.ktg.mes.qc.controller.mobile;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.qc.domain.QcIpqcLine;
import com.ktg.mes.qc.service.IQcIpqcLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动端过程检验、质量巡检、完工检验行接口
 */
@RestController
@RequestMapping("/mobile/qc/ipqcline")
public class QCIpqcLineMobController extends BaseController {

    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    /**
     * 查询过程检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIpqcLine qcIpqcLine)
    {
        startPage();
        List<QcIpqcLine> list = qcIpqcLineService.selectQcIpqcLineList(qcIpqcLine);
        return getDataTable(list);
    }

    /**
     * 获取过程检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcIpqcLineService.selectQcIpqcLineByLineId(lineId));
    }

    /**
     * 新增过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:add')")
    @Log(title = "过程检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcIpqcLine qcIpqcLine)
    {
        qcIpqcLine.setCreateBy(getUsername());
        qcIpqcLineService.insertQcIpqcLine(qcIpqcLine);
        return AjaxResult.success(qcIpqcLine);
    }

    /**
     * 修改过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:edit')")
    @Log(title = "过程检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcIpqcLine qcIpqcLine)
    {
        return toAjax(qcIpqcLineService.updateQcIpqcLine(qcIpqcLine));
    }

    /**
     * 删除过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:remove')")
    @Log(title = "过程检验单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcIpqcLineService.deleteQcIpqcLineByLineIds(lineIds));
    }

}
