package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotaoo.common.utils.TaotaoResult;

/**
 * Created by ainsc on 2017/8/8.
 */
public interface ItemService {
    TbItem getItemsById(long itemdId);
    EasyUIDataGridResult getItemList(int page,int rows);
    TaotaoResult addItem(TbItem item,String desc);
    TbItemDesc getItemDescById(long itemId);
}
