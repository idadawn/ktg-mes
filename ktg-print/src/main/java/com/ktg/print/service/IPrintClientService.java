package com.ktg.print.service;

import com.ktg.common.core.domain.AjaxResult;
import com.ktg.print.domain.PrintClient;

import java.util.List;

public interface IPrintClientService {
    List<PrintClient> getClientList(PrintClient client);

    AjaxResult insertClient(PrintClient client);

    AjaxResult selectClientById(Long clientId);

    AjaxResult updateClient(PrintClient client);

    AjaxResult remove(String clientIds);

    AjaxResult getWorkshopAndWorkstation();

    AjaxResult getAll();
}
