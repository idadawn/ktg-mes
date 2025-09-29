package com.ktg.mes.md.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.entity.SysUser;
import com.ktg.common.exception.ServiceException;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.common.utils.bean.BeanValidators;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.md.mapper.MdVendorMapper;
import com.ktg.mes.md.domain.MdVendor;
import com.ktg.mes.md.service.IMdVendorService;

import javax.validation.Validator;

/**
 * 供应商Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-06
 */
@Service
public class MdVendorServiceImpl implements IMdVendorService 
{
    @Autowired
    private MdVendorMapper mdVendorMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private WmBarCodeUtil barCodeUtil;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询供应商
     * 
     * @param vendorId 供应商主键
     * @return 供应商
     */
    @Override
    public MdVendor selectMdVendorByVendorId(Long vendorId)
    {
        return mdVendorMapper.selectMdVendorByVendorId(vendorId);
    }

    /**
     * 查询供应商列表
     * 
     * @param mdVendor 供应商
     * @return 供应商
     */
    @Override
    public List<MdVendor> selectMdVendorList(MdVendor mdVendor)
    {
        return mdVendorMapper.selectMdVendorList(mdVendor);
    }

    @Override
    public String checkVendorCodeUnique(MdVendor mdVendor) {
        MdVendor vendor = mdVendorMapper.checkVendorCodeUnique(mdVendor);
        Long vendorId = mdVendor.getVendorId()==null?-1L:mdVendor.getVendorId();
        if(StringUtils.isNotNull(vendor) && vendor.getVendorId().longValue() !=vendorId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkVendorNameUnique(MdVendor mdVendor) {
        MdVendor vendor = mdVendorMapper.checkVendorNameUnique(mdVendor);
        Long vendorId = mdVendor.getVendorId()==null?-1L:mdVendor.getVendorId();
        if(StringUtils.isNotNull(vendor) && vendor.getVendorId().longValue() !=vendorId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkVendorNickUnique(MdVendor mdVendor) {
        MdVendor vendor = mdVendorMapper.checkVendorNickUnique(mdVendor);
        Long vendorId = mdVendor.getVendorId()==null?-1L:mdVendor.getVendorId();
        if(StringUtils.isNotNull(vendor) && vendor.getVendorId().longValue() !=vendorId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String importVendor(List<MdVendor> vendorList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(vendorList) || vendorList.size() == 0)
        {
            throw new ServiceException("导入供应商数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MdVendor vendor : vendorList)
        {
            try{
                if(ObjectUtil.isEmpty(vendor.getVendorName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + "必填字段为空");
                    continue;
                }
                if(ObjectUtil.isEmpty(vendor.getVendorCode())) {
                    failureNum++;
                    failureMsg.append("<br/>" + "必填字段为空");
                    continue;
                }

                //判断是否传递了供应商编码
                if(StringUtils.isNull(vendor.getVendorCode())){
                    //没有传递，则自动生成一个供应商编码，并新增一条供应商记录
                    String vendorCode = autoCodeUtil.genSerialCode(UserConstants.VENDOR_CODE,"");
                    vendor.setVendorCode(vendorCode);
                    BeanValidators.validateWithException(validator, vendor);
                    this.insertMdVendor(vendor);
                    barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_VENDOR,vendor.getVendorId(),vendor.getVendorCode(),vendor.getVendorName());
                    successNum++;
                }else{
                    //是否存在
                    MdVendor v = mdVendorMapper.checkVendorCodeUnique(vendor);
                    if(StringUtils.isNull(v)){
                        //不存在，则新增一条供应商记录
                        BeanValidators.validateWithException(validator, vendor);
                        this.insertMdVendor(vendor);
                        barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_VENDOR,vendor.getVendorId(),vendor.getVendorCode(),vendor.getVendorName());
                        successNum++;
                    }else if (isUpdateSupport){
                        //存在，则更新供应商记录
                        BeanValidators.validateWithException(validator, vendor);
                        vendor.setUpdateBy(operName);
                        vendor.setVendorId(v.getVendorId());
                        this.updateMdVendor(vendor);
                        successNum++;
                    }else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、供应商 " + vendor.getVendorName() + " 已存在");
                    }
                }
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、供应商 " + vendor.getVendorName() + " 导入失败：";
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

    /**
     * 新增供应商
     * 
     * @param mdVendor 供应商
     * @return 结果
     */
    @Override
    public int insertMdVendor(MdVendor mdVendor)
    {
        mdVendor.setCreateTime(DateUtils.getNowDate());
        return mdVendorMapper.insertMdVendor(mdVendor);
    }

    /**
     * 修改供应商
     * 
     * @param mdVendor 供应商
     * @return 结果
     */
    @Override
    public int updateMdVendor(MdVendor mdVendor)
    {
        mdVendor.setUpdateTime(DateUtils.getNowDate());
        return mdVendorMapper.updateMdVendor(mdVendor);
    }

    /**
     * 批量删除供应商
     * 
     * @param vendorIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteMdVendorByVendorIds(Long[] vendorIds)
    {
        return mdVendorMapper.deleteMdVendorByVendorIds(vendorIds);
    }

    /**
     * 删除供应商信息
     * 
     * @param vendorId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteMdVendorByVendorId(Long vendorId)
    {
        return mdVendorMapper.deleteMdVendorByVendorId(vendorId);
    }
}
