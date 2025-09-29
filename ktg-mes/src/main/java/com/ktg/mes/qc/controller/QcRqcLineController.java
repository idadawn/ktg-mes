package com.ktg.mes.qc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.qc.domain.QcRqcLine;
import com.ktg.mes.qc.service.IQcRqcLineService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 退料检验单行Controller
 * 
 * @author yinjinlu
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/mes/qc/rqcline")
public class QcRqcLineController extends BaseController
{
    @Autowired
    private IQcRqcLineService qcRqcLineService;

    /**
     * 查询退料检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcRqcLine qcRqcLine)
    {
        startPage();
        List<QcRqcLine> list = qcRqcLineService.selectQcRqcLineList(qcRqcLine);
        return getDataTable(list);
    }

    /**
     * 导出退料检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:export')")
    @Log(title = "退料检验单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcRqcLine qcRqcLine)
    {
        List<QcRqcLine> list = qcRqcLineService.selectQcRqcLineList(qcRqcLine);
        ExcelUtil<QcRqcLine> util = new ExcelUtil<QcRqcLine>(QcRqcLine.class);
        util.exportExcel(response, list, "退料检验单行数据");
    }

    /**
     * 获取退料检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcRqcLineService.selectQcRqcLineByLineId(lineId));
    }

    /**
     * 新增退料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:add')")
    @Log(title = "退料检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcRqcLine qcRqcLine)
    {
        return toAjax(qcRqcLineService.insertQcRqcLine(qcRqcLine));
    }

    /**
     * 修改退料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:edit')")
    @Log(title = "退料检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcRqcLine qcRqcLine)
    {
        return toAjax(qcRqcLineService.updateQcRqcLine(qcRqcLine));
    }

    /**
     * 删除退料检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:rqc:remove')")
    @Log(title = "退料检验单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcRqcLineService.deleteQcRqcLineByLineIds(lineIds));
    }
}
