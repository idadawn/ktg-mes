package com.ktg.mes.wm.mapper;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscRecpt;
import com.ktg.mes.wm.domain.tx.MiscRecptTxBean;

/**
 * 杂项入库单Mapper接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface WmMiscRecptMapper 
{
    /**
     * 查询杂项入库单
     * 
     * @param recptId 杂项入库单主键
     * @return 杂项入库单
     */
    public WmMiscRecpt selectWmMiscRecptByRecptId(Long recptId);

    /**
     * 查询杂项入库单列表
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 杂项入库单集合
     */
    public List<WmMiscRecpt> selectWmMiscRecptList(WmMiscRecpt wmMiscRecpt);


    public WmMiscRecpt checkRecptCodeUnique(WmMiscRecpt wmMiscRecpt);

    public List<MiscRecptTxBean> getTxBeans(Long recptId);

    /**
     * 新增杂项入库单
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 结果
     */
    public int insertWmMiscRecpt(WmMiscRecpt wmMiscRecpt);

    /**
     * 修改杂项入库单
     * 
     * @param wmMiscRecpt 杂项入库单
     * @return 结果
     */
    public int updateWmMiscRecpt(WmMiscRecpt wmMiscRecpt);

    /**
     * 删除杂项入库单
     * 
     * @param recptId 杂项入库单主键
     * @return 结果
     */
    public int deleteWmMiscRecptByRecptId(Long recptId);

    /**
     * 批量删除杂项入库单
     * 
     * @param recptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmMiscRecptByRecptIds(Long[] recptIds);
}
