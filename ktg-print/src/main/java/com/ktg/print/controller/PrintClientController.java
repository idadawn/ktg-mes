package com.ktg.print.controller;

import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.page.TableDataInfo;
import com.ktg.common.enums.BusinessType;
import com.ktg.print.domain.PrintClient;
import com.ktg.print.domain.PrintPrinterConfig;
import com.ktg.print.service.IPrintClientService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/print/client")
public class PrintClientController extends BaseController {

    @Autowired
    private IPrintClientService clientService;

    /**
     * 查询打印机配置列表
     */
    @GetMapping("/list")
    public AjaxResult list(PrintClient client) {
        List<PrintClient> list = clientService.getClientList(client);
        return AjaxResult.success(list);
    }

    @GetMapping("/page")
    public TableDataInfo page(PrintClient client)
    {
        startPage();
        List<PrintClient> list = clientService.getClientList(client);
        return getDataTable(list);
    }


    /**
     * 打印机客户端新增
     * @param client
     * @return
     */
    @Log(title = "打印机客户端配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PrintClient client) {
        return clientService.insertClient(client);
    }

    /**
     * 打印机客户端详情
     * @param clientId
     * @return
     */
    @GetMapping("/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") Long clientId) {
        return clientService.selectClientById(clientId);
    }

    /**
     * 打印机客户端修改
     * @param client
     * @return
     */
    @Log(title = "客户端配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PrintClient client) {
        return clientService.updateClient(client);
    }

    /**
     * 删除客户端配置
     * @param clientIds
     * @return
     */
    @Log(title = "客户端配置", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{clientIds}")
    public AjaxResult remove(@PathVariable String clientIds) {
        return clientService.remove(clientIds);
    }

    @GetMapping("/getWorkshopAndWorkstation")
    public AjaxResult getWorkshopAndWorkstation() {
        return clientService.getWorkshopAndWorkstation();
    }

    @GetMapping("/getAll")
    public AjaxResult getAll() {
        return clientService.getAll();
    }
}
