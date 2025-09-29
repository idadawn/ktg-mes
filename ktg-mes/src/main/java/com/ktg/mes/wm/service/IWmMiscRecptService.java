package com.ktg.mes.wm.service;

import java.util.List;
import com.ktg.mes.wm.domain.WmMiscRecpt;
import com.ktg.mes.wm.domain.tx.MiscRecptTxBean;

/**
 * 杂项入库单Service接口
 * 
 * @author yinjinlu
 * @date 2025-05-15
 */
public interface IWmMiscRecptService 
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

    /**
     * 检查入库单号是否重复
     * @param wmMiscRecpt
     * @return
     */
    public String checkRecptCodeUnique(WmMiscRecpt wmMiscRecpt);


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
     * 批量删除杂项入库单
     * 
     * @param recptIds 需要删除的杂项入库单主键集合
     * @return 结果
     */
    public int deleteWmMiscRecptByRecptIds(Long[] recptIds);

    /**
     * 删除杂项入库单信息
     * 
     * @param recptId 杂项入库单主键
     * @return 结果
     */
    public int deleteWmMiscRecptByRecptId(Long recptId);
}
