package com.ktg.mes.pro.service;

import java.util.List;
import com.ktg.mes.pro.domain.ProCard;

/**
 * 工序流转卡Service接口
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
public interface IProCardService 
{
    /**
     * 查询工序流转卡
     * 
     * @param cardId 工序流转卡主键
     * @return 工序流转卡
     */
    public ProCard selectProCardByCardId(Long cardId);

    /**
     * 查询工序流转卡列表
     * 
     * @param proCard 工序流转卡
     * @return 工序流转卡集合
     */
    public List<ProCard> selectProCardList(ProCard proCard);

    /**
     * 查询指定生产工单在指定工作站下的流转单记录
     * @param card
     * @return
     */
    public List<ProCard> getStationList(ProCard card);

    /**
     * 新增工序流转卡
     * 
     * @param proCard 工序流转卡
     * @return 结果
     */
    public int insertProCard(ProCard proCard);

    /**
     * 修改工序流转卡
     * 
     * @param proCard 工序流转卡
     * @return 结果
     */
    public int updateProCard(ProCard proCard);

    /**
     * 批量删除工序流转卡
     * 
     * @param cardIds 需要删除的工序流转卡主键集合
     * @return 结果
     */
    public int deleteProCardByCardIds(Long[] cardIds);

    /**
     * 删除工序流转卡信息
     * 
     * @param cardId 工序流转卡主键
     * @return 结果
     */
    public int deleteProCardByCardId(Long cardId);

    /**
     * 校验编码是否唯一
     * @param proCard
     * @return
     */
    String checkCardCodeUnique(ProCard proCard);
}
