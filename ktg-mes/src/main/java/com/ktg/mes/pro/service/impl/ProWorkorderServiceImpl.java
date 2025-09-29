package com.ktg.mes.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.pro.controller.vo.ProRouteHomeVO;
import com.ktg.mes.pro.controller.vo.ProWorkorderHomeVO;
import com.ktg.mes.pro.domain.ProFeedback;
import com.ktg.mes.pro.domain.ProRouteProcess;
import com.ktg.mes.pro.domain.ProRouteProduct;
import com.ktg.mes.pro.domain.ProWorkorder;
import com.ktg.mes.pro.mapper.ProWorkorderMapper;
import com.ktg.mes.pro.service.IProFeedbackService;
import com.ktg.mes.pro.service.IProRouteProcessService;
import com.ktg.mes.pro.service.IProRouteProductService;
import com.ktg.mes.pro.service.IProWorkorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 生产工单Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
@Service
public class ProWorkorderServiceImpl implements IProWorkorderService
{
    @Autowired
    private ProWorkorderMapper proWorkorderMapper;

    @Autowired
    private IProRouteProductService proRouteProductService;

    @Autowired
    private IProRouteProcessService proRouteProcessService;

    @Autowired
    private IProFeedbackService proFeedbackService;

    /**
     * 查询生产工单
     *
     * @param workorderId 生产工单主键
     * @return 生产工单
     */
    @Override
    public ProWorkorder selectProWorkorderByWorkorderId(Long workorderId)
    {
        return proWorkorderMapper.selectProWorkorderByWorkorderId(workorderId);
    }

    /**
     * 查询生产工单
     *
     * @param workorderId 生产工单主键
     * @return 生产工单
     */
    @Override
    public List<ProWorkorder> selectProWorkorderListByParentId(Long workorderId)
    {
        return proWorkorderMapper.selectProWorkorderListByParentId(workorderId);
    }

    /**
     * 查询生产工单列表
     *
     * @param proWorkorder 生产工单
     * @return 生产工单
     */
    @Override
    public List<ProWorkorder> selectProWorkorderList(ProWorkorder proWorkorder)
    {
        return proWorkorderMapper.selectProWorkorderList(proWorkorder);
    }

    @Override
    public String checkWorkorderCodeUnique(ProWorkorder proWorkorder) {
        ProWorkorder workorder = proWorkorderMapper.checkWorkorderCodeUnique(proWorkorder);
        Long workorderId = proWorkorder.getWorkorderId() == null? -1L: proWorkorder.getWorkorderId();
        if(StringUtils.isNotNull(workorder) && workorder.getWorkorderId().longValue() != workorderId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    @Override
    public int insertProWorkorder(ProWorkorder proWorkorder)
    {
        if(proWorkorder.getParentId()!= null){
            ProWorkorder parent = proWorkorderMapper.selectProWorkorderByWorkorderId(proWorkorder.getParentId());
            if(StringUtils.isNotNull(parent)){
                proWorkorder.setAncestors(parent.getAncestors()+","+parent.getWorkorderId());
            }
        }

        proWorkorder.setCreateTime(DateUtils.getNowDate());
        return proWorkorderMapper.insertProWorkorder(proWorkorder);
    }

    /**
     * 修改生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    @Override
    public int updateProWorkorder(ProWorkorder proWorkorder)
    {
        proWorkorder.setUpdateTime(DateUtils.getNowDate());
        return proWorkorderMapper.updateProWorkorder(proWorkorder);
    }

    /**
     * 批量删除生产工单
     *
     * @param workorderIds 需要删除的生产工单主键
     * @return 结果
     */
    @Override
    public int deleteProWorkorderByWorkorderIds(Long[] workorderIds)
    {
        return proWorkorderMapper.deleteProWorkorderByWorkorderIds(workorderIds);
    }

    /**
     * 删除生产工单信息
     *
     * @param workorderId 生产工单主键
     * @return 结果
     */
    @Override
    public int deleteProWorkorderByWorkorderId(Long workorderId)
    {
        return proWorkorderMapper.deleteProWorkorderByWorkorderId(workorderId);
    }

    @Override
    public AjaxResult getHomeList(ProWorkorder proWorkorder) {
        // 获取所有工单
        List<ProWorkorder> workorders = proWorkorderMapper.selectProWorkorderList(proWorkorder);
        if (!(workorders != null && workorders.size() > 0)) {
            // 生产工单数据为空，则不进行后续操作
            return AjaxResult.success(workorders);
        }
        // 获取所有的工单ID
        List<Long> workorderIds = workorders.stream().map(ProWorkorder::getWorkorderId).collect(Collectors.toList());
        List<ProFeedback> feedbacks = proFeedbackService.selectByWorkorderIds(workorderIds);
        if (feedbacks != null && feedbacks.size() > 0) {
            // 处理报工数据
            feedbacks.stream().forEach(item -> {
                if (ObjectUtil.isEmpty(item.getQuantityFeedback())) {
                    item.setQuantityFeedback(BigDecimal.ZERO);
                }
            });
        }

        // 获取所有工单所需的工序
        List<Long> productIds = workorders.stream().map(ProWorkorder::getProductId).distinct().collect(Collectors.toList());
        // 根据所有产品ID查询所有工序数据
        List<ProRouteProduct> products = proRouteProductService.selectByProductIds(productIds);
        if (!(products != null && products.size() > 0)) {
            return AjaxResult.success(workorders);
        }
        List<Long> routeIds = products.stream().map(ProRouteProduct::getRouteId).collect(Collectors.toList());
        List<ProRouteProcess> routeList = proRouteProcessService.selectByRouteIds(routeIds);
        if (!(routeList != null && routeList.size() > 0)) {
            return AjaxResult.success(workorders);
        }
        List<ProRouteHomeVO> routeHomeList = new ArrayList<>();
        routeList.forEach(item -> {
            ProRouteHomeVO proWorkorderHomeVO = new ProRouteHomeVO();
            BeanUtil.copyProperties(item, proWorkorderHomeVO);
            routeHomeList.add(proWorkorderHomeVO);
        });
        // 构建工序流程数据
        Map<Long, List<ProRouteHomeVO>> routeMap = new HashMap<>();
        products.forEach(item -> {
            routeMap.putIfAbsent(item.getItemId(), routeHomeList.stream().filter(val -> val.getRouteId().equals(item.getRouteId())).collect(Collectors.toList()));
        });

        // 遍历构造
        List<ProWorkorderHomeVO> proWorkorders = new ArrayList<>();
        workorders.forEach(item -> {
            ProWorkorderHomeVO proWorkorderHomeVO = new ProWorkorderHomeVO();
            BeanUtil.copyProperties(item, proWorkorderHomeVO);
            Long productId = item.getProductId();
            List<ProRouteHomeVO> proRouteHomeVOS = routeMap.get(productId);
            if (proRouteHomeVOS != null && proRouteHomeVOS.size() > 0) {
                List<ProRouteHomeVO> processes = new ArrayList<>();
                proRouteHomeVOS.forEach(entity -> {
                    ProRouteHomeVO proRouteHomeVO = new ProRouteHomeVO();
                    BeanUtil.copyProperties(entity, proRouteHomeVO);
                    processes.add(proRouteHomeVO);
                });
                    processes.forEach(val -> {
                        val.setTotal(item.getQuantity());
                        List<ProFeedback> collect = new ArrayList<>();
                        if (feedbacks != null && feedbacks.size() > 0) {
                            collect = feedbacks.stream().filter(entity ->
                                            (entity.getWorkorderId().equals(item.getWorkorderId())
                                                    && entity.getRouteId().equals(val.getRouteId())
                                                    && entity.getProcessId().equals(val.getProcessId())))
                                    .collect(Collectors.toList());
                        }
                        if (collect != null && collect.size() > 0) {
                            BigDecimal reduce = collect.stream().map(ProFeedback::getQuantityFeedback).reduce(BigDecimal.ZERO, BigDecimal::add);
                            val.setCompleteNumber(reduce);
                            val.setIncompleteNumber(val.getTotal().subtract(val.getCompleteNumber()));
                        } else {
                            val.setCompleteNumber(BigDecimal.ZERO);
                            val.setIncompleteNumber(val.getTotal().subtract(val.getCompleteNumber()));
                        }
                    });
                proWorkorderHomeVO.setRouteHomg(processes);
            }
            proWorkorders.add(proWorkorderHomeVO);
        });

        // 构建工单树结构数据
        Map<Long, List<ProWorkorderHomeVO>> map = proWorkorders.stream().collect(Collectors.groupingBy(ProWorkorderHomeVO::getParentId));
        proWorkorders.stream().forEach(item -> {
            item.setChildren(map.get(item.getWorkorderId()));
        });
        List<ProWorkorderHomeVO> collect = proWorkorders.stream().filter(item -> 0L == item.getParentId()).collect(Collectors.toList());
        return AjaxResult.success(collect);
    }
}
