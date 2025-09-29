package com.ktg.mes.md.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ktg.common.core.domain.entity.ItemType;
import com.ktg.common.exception.ServiceException;
import com.ktg.common.utils.bean.BeanValidators;
import com.ktg.mes.md.domain.MdVendor;
import com.ktg.mes.md.mapper.ItemTypeMapper;
import com.ktg.mes.md.service.IMdItemService;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdItem;
import com.ktg.mes.md.mapper.MdItemMapper;
import com.ktg.mes.wm.utils.WmBarCodeUtil;
import com.ktg.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.List;

@Service
public class MdItemServiceImpl implements IMdItemService {

    @Autowired
    private MdItemMapper mdItemMapper;

    @Autowired
    private ItemTypeMapper itemTypeMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private WmBarCodeUtil barCodeUtil;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    @Override
    public List<MdItem> selectMdItemList(MdItem mdItem) {
        return mdItemMapper.selectMdItemList(mdItem);
    }

    @Override
    public List<MdItem> selectMdItemAll() {
        return mdItemMapper.selectMdItemAll();
    }

    @Override
    public List<MdItem> getExeportList(MdItem mdItem) {
        return mdItemMapper.getExeportList(mdItem);
    }

    @Override
    public String importItem(List<MdItem> itemList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(itemList) || itemList.size() == 0)
        {
            throw new ServiceException("导入物料产品数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MdItem item : itemList)
        {
            try{
                if (ObjectUtil.isEmpty(item.getItemCode())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 缺少必填项");
                    continue;
                }
                if (ObjectUtil.isEmpty(item.getItemName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 缺少必填项");
                    continue;
                }
                if (ObjectUtil.isEmpty(item.getUnitOfMeasure())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 缺少必填项");
                    continue;
                }
                //物料分类是否正确
                if(StringUtils.isNotNull(item.getItemTypeName())){
                    List<ItemType> types = itemTypeMapper.selectItemTypeByName(item.getItemTypeName());
                    if(!CollectionUtils.isEmpty(types)){
                        item.setItemTypeId(types.get(0).getItemTypeId());
                        item.setItemTypeCode(types.get(0).getItemTypeCode());
                        item.setItemTypeName(types.get(0).getItemTypeName());
                        item.setItemOrProduct(types.get(0).getItemOrProduct());

                        item.setSafeStockFlag(UserConstants.NO);
                        item.setEnableFlag(UserConstants.YES);
                        item.setHighValue(UserConstants.NO);

                        if(StringUtils.isNull(item.getItemCode())){
                            //如果没有传递物料编码，则自动生成一个物料编码，然后新增一条物料记录
                            String itemCode = autoCodeUtil.genSerialCode(UserConstants.ITEM_CODE,"");
                            item.setItemCode(itemCode);
                            BeanValidators.validateWithException(validator, item);
                            this.insertMdItem(item);
                            barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM,item.getItemId(),item.getItemCode(),item.getItemName());
                            successNum++;
                        }else{
                            //如果传递了物料编码，则判断是否已存在
                            MdItem v = mdItemMapper.checkItemCodeUnique(item);
                            if(StringUtils.isNull(v)){
                                //如果不存在，则按用户给的物料编码新增一条物料记录
                                BeanValidators.validateWithException(validator, item);
                                this.insertMdItem(item);
                                barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM,item.getItemId(),item.getItemCode(),item.getItemName());
                                successNum++;
                            }else if (isUpdateSupport){
                                //如果存在则按用户给的物料编码更新条物料记录
                                BeanValidators.validateWithException(validator, item);
                                item.setUpdateBy(operName);
                                item.setItemId(v.getItemId());
                                this.updateMdItem(item);
                                successNum++;
                            }else {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 已存在");
                            }
                        }

                    }else{
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 请填写正确的分类");
                    }
                }else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 请填写分类");
                }
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、物料/产品 " + item.getItemName() + " 导入失败：";
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


    @Override
    public MdItem selectMdItemById(Long itemId) {
        return mdItemMapper.selectMdItemById(itemId);
    }

    @Override
    public String checkItemCodeUnique(MdItem mdItem) {
        MdItem item = mdItemMapper.checkItemCodeUnique(mdItem);
        Long itemId = mdItem.getItemId() == null? -1L:mdItem.getItemId();
        if(StringUtils.isNotNull(item) && item.getItemId().longValue() != itemId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }else{
            return UserConstants.UNIQUE;
        }
    }

    @Override
    public String checkItemNameUnique(MdItem mdItem) {
        MdItem item = mdItemMapper.checkItemNameUnique(mdItem);
        Long itemId = mdItem.getItemId() == null? -1L:mdItem.getItemId();
        if(StringUtils.isNotNull(item) && item.getItemId().longValue() != itemId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }else{
            return UserConstants.UNIQUE;
        }
    }

    @Override
    public int insertMdItem(MdItem mdItem) {
        return mdItemMapper.insertMdItem(mdItem);
    }

    @Override
    public int updateMdItem(MdItem mdItem) {
        return mdItemMapper.updateMdItem(mdItem);
    }

    @Override
    public int deleteByItemIds(Long[] itemIds) {
        return mdItemMapper.deleteMdItemByIds(itemIds);
    }

    @Override
    public int deleteByItemId(Long itemId) {
        return mdItemMapper.deleteMdItemById(itemId);
    }
}
