package com.ktg.mes.dv.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.dv.domain.DvMachinery;
import com.ktg.mes.dv.mapper.DvMachineryMapper;
import com.ktg.mes.dv.service.IDvMachineryService;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 设备Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-08
 */
@Service
public class DvMachineryServiceImpl implements IDvMachineryService 
{
    @Autowired
    private DvMachineryMapper dvMachineryMapper;


    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询设备
     * 
     * @param machineryId 设备主键
     * @return 设备
     */
    @Override
    public DvMachinery selectDvMachineryByMachineryId(Long machineryId)
    {
        return dvMachineryMapper.selectDvMachineryByMachineryId(machineryId);
    }

    /**
     * 查询设备列表
     * 
     * @param dvMachinery 设备
     * @return 设备
     */
    @Override
    public List<DvMachinery> selectDvMachineryList(DvMachinery dvMachinery)
    {
        return dvMachineryMapper.selectDvMachineryList(dvMachinery);
    }

    /**
     * 新增设备
     *
     * @param dvMachinery 设备
     * @return 结果
     */
    @Override
    public AjaxResult insertDvMachinery(DvMachinery dvMachinery)
    {
        List<DvMachinery> existing = dvMachineryMapper.selectByMachineryCode(dvMachinery.getMachineryCode());
        if (existing != null && existing.size() > 0) {
            return AjaxResult.error("设备编码重复");
        }
        dvMachinery.setCreateTime(DateUtils.getNowDate());
        dvMachineryMapper.insertDvMachinery(dvMachinery);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_MACHINERY,dvMachinery.getMachineryId(),dvMachinery.getMachineryCode(),dvMachinery.getMachineryName());
        return AjaxResult.success("操作成功");
    }

    /**
     * 修改设备
     *
     * @param dvMachinery 设备
     * @return 结果
     */
    @Override
    public AjaxResult updateDvMachinery(DvMachinery dvMachinery)
    {
        dvMachinery.setUpdateTime(DateUtils.getNowDate());
        return AjaxResult.success(dvMachineryMapper.updateDvMachinery(dvMachinery));
    }

    /**
     * 批量删除设备
     * 
     * @param machineryIds 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteDvMachineryByMachineryIds(Long[] machineryIds)
    {
        return dvMachineryMapper.deleteDvMachineryByMachineryIds(machineryIds);
    }

    /**
     * 删除设备信息
     * 
     * @param machineryId 设备主键
     * @return 结果
     */
    @Override
    public int deleteDvMachineryByMachineryId(Long machineryId)
    {
        return dvMachineryMapper.deleteDvMachineryByMachineryId(machineryId);
    }

    /**
     * 依据上传的文件更新或插入设备信息
     */
    @Override
    public String importMachinery(List<DvMachinery> machineryList, Boolean isUpdateSupport, String operName) {
        if (machineryList == null || machineryList.isEmpty()) {
            return "导入数据为空";
        }
        int successCount = 0;
        int failureCount = 0;
        for (DvMachinery machinery : machineryList) {
            // 判断必填项是否为空
            if (ObjectUtil.isEmpty(machinery.getMachineryCode())) {
                failureCount++;
                continue;
            }
            if (ObjectUtil.isEmpty(machinery.getMachineryName())) {
                failureCount++;
                continue;
            }
            if (ObjectUtil.isEmpty(machinery.getMachineryTypeId())) {
                failureCount++;
                continue;
            }
            if (ObjectUtil.isEmpty(machinery.getWorkshopId())) {
                failureCount++;
                continue;
            }
            if (ObjectUtil.isEmpty(machinery.getStatus())) {
                failureCount++;
                continue;
            }
            // 去除空格
            String machineryCode = machinery.getMachineryCode().trim();
            machinery.setCreateTime(new Date());
            List<DvMachinery> existing = dvMachineryMapper.selectByMachineryCode(machineryCode);
            if (existing != null && existing.size() > 0) {
                if (isUpdateSupport) {
                    // 更新数据
                    machinery.setMachineryId(existing.get(0).getMachineryId()); // 确保使用现有 ID 进行更新
                    dvMachineryMapper.updateDvMachinery(machinery);
                    successCount++;
                } else {
                    // 不更新数据
                    failureCount++;
                }
            } else {
                // 新增数据
                dvMachineryMapper.insertDvMachinery(machinery);
                successCount++;
            }
        }
        return String.format("操作用户：%s，导入完成，成功 %d 条，失败 %d 条。", operName, successCount, failureCount);
    }

    @Override
    public String checkRecptCodeUnique(DvMachinery dvMachinery) {
        DvMachinery machinery = dvMachineryMapper.checkRecptCodeUnique(dvMachinery);
        Long machineryId = dvMachinery.getMachineryId() == null ? -1L : dvMachinery.getMachineryId();
        if (StringUtils.isNotNull(machinery) && machinery.getMachineryId().longValue() != machineryId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
