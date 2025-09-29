package com.ktg.mes.pro.service.impl;

import java.util.List;

import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProCardMapper;
import com.ktg.mes.pro.domain.ProCard;
import com.ktg.mes.pro.service.IProCardService;

/**
 * 工序流转卡Service业务层处理
 * 
 * @author yinjinlu
 * @date 2024-07-04
 */
@Service
public class ProCardServiceImpl implements IProCardService 
{
    @Autowired
    private ProCardMapper proCardMapper;

    /**
     * 查询工序流转卡
     * 
     * @param cardId 工序流转卡主键
     * @return 工序流转卡
     */
    @Override
    public ProCard selectProCardByCardId(Long cardId)
    {
        return proCardMapper.selectProCardByCardId(cardId);
    }

    /**
     * 查询工序流转卡列表
     * 
     * @param proCard 工序流转卡
     * @return 工序流转卡
     */
    @Override
    public List<ProCard> selectProCardList(ProCard proCard)
    {
        return proCardMapper.selectProCardList(proCard);
    }

    /**
     * 查询指定生产工单在指定工作站下的流转单记录
     * @param card
     * @return
     */
    @Override
    public List<ProCard> getStationList(ProCard card) {
        return proCardMapper.getStationList(card);
    }

    /**
     * 新增工序流转卡
     * 
     * @param proCard 工序流转卡
     * @return 结果
     */
    @Override
    public int insertProCard(ProCard proCard)
    {
        proCard.setCreateTime(DateUtils.getNowDate());
        return proCardMapper.insertProCard(proCard);
    }

    /**
     * 修改工序流转卡
     * 
     * @param proCard 工序流转卡
     * @return 结果
     */
    @Override
    public int updateProCard(ProCard proCard)
    {
        proCard.setUpdateTime(DateUtils.getNowDate());
        return proCardMapper.updateProCard(proCard);
    }

    /**
     * 批量删除工序流转卡
     * 
     * @param cardIds 需要删除的工序流转卡主键
     * @return 结果
     */
    @Override
    public int deleteProCardByCardIds(Long[] cardIds)
    {
        return proCardMapper.deleteProCardByCardIds(cardIds);
    }

    /**
     * 删除工序流转卡信息
     * 
     * @param cardId 工序流转卡主键
     * @return 结果
     */
    @Override
    public int deleteProCardByCardId(Long cardId)
    {
        return proCardMapper.deleteProCardByCardId(cardId);
    }

    @Override
    public String checkCardCodeUnique(ProCard proCard) {
        ProCard card = proCardMapper.checkCardCodeUnique(proCard);
        Long cardId = proCard.getCardId() == null ? -1L : proCard.getCardId();
        if (StringUtils.isNotNull(card) && card.getCardId().longValue() != cardId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
