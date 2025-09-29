package com.ktg.print.domain.vo;

import com.ktg.mes.md.domain.MdWorkshop;
import com.ktg.mes.md.domain.MdWorkstation;
import lombok.Data;

import java.util.List;

@Data
public class ClientDictVO {

    // 车间列表
    private List<MdWorkshop> workshopList;

    // 工作站列表
    private List<MdWorkstation> workstations;

}
