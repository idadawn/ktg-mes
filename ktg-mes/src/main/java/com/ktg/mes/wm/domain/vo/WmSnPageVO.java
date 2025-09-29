package com.ktg.mes.wm.domain.vo;

import com.ktg.mes.wm.domain.WmSn;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class WmSnPageVO extends WmSn {

    private String ids;
}
