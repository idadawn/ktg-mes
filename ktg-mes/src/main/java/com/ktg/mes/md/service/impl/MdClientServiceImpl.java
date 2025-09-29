package com.ktg.mes.md.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.exception.ServiceException;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.bean.BeanValidators;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.md.mapper.MdClientMapper;
import com.ktg.mes.md.domain.MdClient;
import com.ktg.mes.md.service.IMdClientService;

import javax.validation.Validator;

/**
 * 客户Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-06
 */
@Service
public class MdClientServiceImpl implements IMdClientService 
{
    @Autowired
    private MdClientMapper mdClientMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private WmBarCodeUtil barCodeUtil;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询客户
     * 
     * @param clientId 客户主键
     * @return 客户
     */
    @Override
    public MdClient selectMdClientByClientId(Long clientId)
    {
        return mdClientMapper.selectMdClientByClientId(clientId);
    }

    /**
     * 查询客户列表
     * 
     * @param mdClient 客户
     * @return 客户
     */
    @Override
    public List<MdClient> selectMdClientList(MdClient mdClient)
    {
        return mdClientMapper.selectMdClientList(mdClient);
    }

    @Override
    public String checkClientCodeUnique(MdClient mdClient) {
        MdClient client = mdClientMapper.checkClientCodeUnique(mdClient);
        Long clientId = mdClient.getClientId()==null?-1L:mdClient.getClientId();
        if(StringUtils.isNotNull(client) && client.getClientId().longValue() !=clientId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkClientNameUnique(MdClient mdClient) {
        MdClient client = mdClientMapper.checkClientNameUnique(mdClient);
        Long clientId = mdClient.getClientId()==null?-1L:mdClient.getClientId();
        if(StringUtils.isNotNull(client) && client.getClientId().longValue() !=clientId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkClientNickUnique(MdClient mdClient) {
        MdClient client = mdClientMapper.checkClientNickUnique(mdClient);
        Long clientId = mdClient.getClientId()==null?-1L:mdClient.getClientId();
        if(StringUtils.isNotNull(client) && client.getClientId().longValue() !=clientId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增客户
     * 
     * @param mdClient 客户
     * @return 结果
     */
    @Override
    public int insertMdClient(MdClient mdClient)
    {
        mdClient.setCreateTime(DateUtils.getNowDate());
        return mdClientMapper.insertMdClient(mdClient);
    }

    /**
     * 修改客户
     * 
     * @param mdClient 客户
     * @return 结果
     */
    @Override
    public int updateMdClient(MdClient mdClient)
    {
        mdClient.setUpdateTime(DateUtils.getNowDate());
        return mdClientMapper.updateMdClient(mdClient);
    }

    /**
     * 批量删除客户
     * 
     * @param clientIds 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteMdClientByClientIds(Long[] clientIds)
    {
        return mdClientMapper.deleteMdClientByClientIds(clientIds);
    }

    /**
     * 删除客户信息
     * 
     * @param clientId 客户主键
     * @return 结果
     */
    @Override
    public int deleteMdClientByClientId(Long clientId)
    {
        return mdClientMapper.deleteMdClientByClientId(clientId);
    }


    /**
     * 导入客户信息
     * @param clientList
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    @Override
    public String importClient(List<MdClient> clientList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(clientList) || clientList.size() == 0)
        {
            throw new ServiceException("导入客户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MdClient client : clientList)
        {
            try{
                if(ObjectUtil.isEmpty(client.getClientCode())) {
                    failureNum++;
                    failureMsg.append("<br/>" + "必填字段为空");
                    continue;
                }
                if(ObjectUtil.isEmpty(client.getClientName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + "必填字段为空");
                    continue;
                }

                if(StringUtils.isNull(client.getClientCode())){
                    String clientCode = autoCodeUtil.genSerialCode(UserConstants.VENDOR_CODE,"");
                    client.setClientCode(clientCode);
                    BeanValidators.validateWithException(validator, client);
                    this.insertMdClient(client);
                    barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_CLIENT,client.getClientId(),client.getClientCode(),client.getClientName());
                    successNum++;
                }else{
                    //是否存在 - （根据客户编码查询）
                    MdClient v = mdClientMapper.checkClientCodeUnique(client);
                    if(StringUtils.isNull(v)){
                        BeanValidators.validateWithException(validator, client);
                        this.insertMdClient(client);
                        barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_CLIENT,client.getClientId(),client.getClientCode(),client.getClientName());
                        successNum++;
                    }else if (isUpdateSupport){
                        BeanValidators.validateWithException(validator, client);
                        client.setUpdateBy(operName);
                        client.setClientId(v.getClientId());
                        this.updateMdClient(client);
                        successNum++;
                    }else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、客户 " + client.getClientName() + " 已存在");
                    }
                }






            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、客户 " + client.getClientName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }
}
