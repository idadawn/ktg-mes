package com.ktg.mes.pro.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * SN流转-工序信息对象 pro_sn_process
 * 
 * @author yinjinlu
 * @date 2024-11-22
 */
public class ProSnProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流水ID */
    private Long recordId;

    /** SNID */
    @Excel(name = "SNID")
    private Long snId;

    /** SN编号 */
    @Excel(name = "SN编号")
    private String snCode;

    /** 序号 */
    @Excel(name = "序号")
    private Long seqNum;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序编号 */
    @Excel(name = "工序编号")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 进入工序时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进入工序时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

    /** 出工序时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出工序时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outputTime;

    /** 投入数量 */
    @Excel(name = "投入数量")
    private BigDecimal quantityInput;

    /** 产出数量 */
    @Excel(name = "产出数量")
    private BigDecimal quantityOutput;

    /** 不合格品数量 */
    @Excel(name = "不合格品数量")
    private BigDecimal quantityUnquanlify;

    /** 工作站ID */
    @Excel(name = "工作站ID")
    private Long workstationId;

    /** 工作站编号 */
    @Excel(name = "工作站编号")
    private String workstationCode;

    /** 工作站名称 */
    @Excel(name = "工作站名称")
    private String workstationName;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 名称 */
    @Excel(name = "名称")
    private String nickName;

    /** 过程检验单ID */
    @Excel(name = "过程检验单ID")
    private Long ipqcId;


    private Long workOrderId;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setSnId(Long snId) 
    {
        this.snId = snId;
    }

    public Long getSnId() 
    {
        return snId;
    }
    public void setSnCode(String snCode) 
    {
        this.snCode = snCode;
    }

    public String getSnCode() 
    {
        return snCode;
    }
    public void setSeqNum(Long seqNum) 
    {
        this.seqNum = seqNum;
    }

    public Long getSeqNum() 
    {
        return seqNum;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setInputTime(Date inputTime) 
    {
        this.inputTime = inputTime;
    }

    public Date getInputTime() 
    {
        return inputTime;
    }
    public void setOutputTime(Date outputTime) 
    {
        this.outputTime = outputTime;
    }

    public Date getOutputTime() 
    {
        return outputTime;
    }
    public void setQuantityInput(BigDecimal quantityInput) 
    {
        this.quantityInput = quantityInput;
    }

    public BigDecimal getQuantityInput() 
    {
        return quantityInput;
    }
    public void setQuantityOutput(BigDecimal quantityOutput) 
    {
        this.quantityOutput = quantityOutput;
    }

    public BigDecimal getQuantityOutput() 
    {
        return quantityOutput;
    }
    public void setQuantityUnquanlify(BigDecimal quantityUnquanlify) 
    {
        this.quantityUnquanlify = quantityUnquanlify;
    }

    public BigDecimal getQuantityUnquanlify() 
    {
        return quantityUnquanlify;
    }
    public void setWorkstationId(Long workstationId) 
    {
        this.workstationId = workstationId;
    }

    public Long getWorkstationId() 
    {
        return workstationId;
    }
    public void setWorkstationCode(String workstationCode) 
    {
        this.workstationCode = workstationCode;
    }

    public String getWorkstationCode() 
    {
        return workstationCode;
    }
    public void setWorkstationName(String workstationName) 
    {
        this.workstationName = workstationName;
    }

    public String getWorkstationName() 
    {
        return workstationName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setIpqcId(Long ipqcId) 
    {
        this.ipqcId = ipqcId;
    }

    public Long getIpqcId() 
    {
        return ipqcId;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("snId", getSnId())
            .append("snCode", getSnCode())
            .append("seqNum", getSeqNum())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("inputTime", getInputTime())
            .append("outputTime", getOutputTime())
            .append("quantityInput", getQuantityInput())
            .append("quantityOutput", getQuantityOutput())
            .append("quantityUnquanlify", getQuantityUnquanlify())
            .append("workstationId", getWorkstationId())
            .append("workstationCode", getWorkstationCode())
            .append("workstationName", getWorkstationName())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("ipqcId", getIpqcId())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
