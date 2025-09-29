package com.ktg.mes.wm.mapper;

import java.util.List;

import com.ktg.mes.wm.domain.WmSn;
import com.ktg.mes.wm.domain.vo.WmSnPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * SN码Mapper接口
 * 
 * @author yinjinlu
 * @date 2022-12-08
 */
public interface WmSnMapper 
{
    /**
     * 查询SN码
     * 
     * @param snId SN码主键
     * @return SN码
     */
    public WmSn selectWmSnBySnId(Long snId);

    /**
     * 查询SN码列表
     *
     * @param wmSn SN码
     * @return SN码集合
     */
    public List<WmSn> selectWmSnList(WmSn wmSn);

    /**
     * 查询SN展示列表
     * @param wmSn
     * @return
     */
    public List<WmSn> selectSnList(WmSn wmSn);

    /**
     * 查询指定SN的流转记录
     * @param sn
     * @return
     */
    public List<WmSn> getStationList(WmSn sn);

    /**
     * 新增SN码
     * 
     * @param wmSn SN码
     * @return 结果
     */
    public int insertWmSn(WmSn wmSn);

    /**
     * 修改SN码
     * 
     * @param wmSn SN码
     * @return 结果
     */
    public int updateWmSn(WmSn wmSn);

    /**
     * 删除SN码
     * 
     * @param snId SN码主键
     * @return 结果
     */
    public int deleteWmSnBySnId(Long snId);

    /**
     * 批量删除SN码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmSnBySnIds(@Param("ids") List<Long> ids);

    List<WmSnPageVO> getWmSnList(WmSn wmSn);
}
