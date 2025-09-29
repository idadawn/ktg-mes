package com.ktg.mes.pro.controller.vo;

import com.ktg.mes.pro.domain.ProWorkorder;
import lombok.Data;

import java.util.List;

@Data
public class ProWorkorderHomeVO extends ProWorkorder {

    private List<ProRouteHomeVO> routeHomg;

}
