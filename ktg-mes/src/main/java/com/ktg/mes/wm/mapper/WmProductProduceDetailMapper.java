package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmProductProduceDetail;

/**
 * 产品产出记录明细Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-03-11
 */
public interface WmProductProduceDetailMapper 
{
    /**
     * 查询产品产出记录明细
     * 
     * @param detailId 产品产出记录明细主键
     * @return 产品产出记录明细
     */
    public WmProductProduceDetail selectWmProductProduceDetailByDetailId(Long detailId);

    /**
     * 查询产品产出记录明细列表
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 产品产出记录明细集合
     */
    public List<WmProductProduceDetail> selectWmProductProduceDetailList(WmProductProduceDetail wmProductProduceDetail);

    /**
     * 新增产品产出记录明细
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 结果
     */
    public int insertWmProductProduceDetail(WmProductProduceDetail wmProductProduceDetail);

    /**
     * 修改产品产出记录明细
     * 
     * @param wmProductProduceDetail 产品产出记录明细
     * @return 结果
     */
    public int updateWmProductProduceDetail(WmProductProduceDetail wmProductProduceDetail);

    /**
     * 删除产品产出记录明细
     * 
     * @param detailId 产品产出记录明细主键
     * @return 结果
     */
    public int deleteWmProductProduceDetailByDetailId(Long detailId);

    /**
     * 批量删除产品产出记录明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmProductProduceDetailByDetailIds(Long[] detailIds);
}
