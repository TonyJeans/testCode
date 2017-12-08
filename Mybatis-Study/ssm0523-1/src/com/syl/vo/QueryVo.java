package com.syl.vo;

import com.syl.pojo.Items;

import java.util.List;

/**
 * Created by ainsc on 2017/7/29.
 */
public class QueryVo {
    //商品对象
    private Items items;

    //订单对象
    //用户对象

    //批量删除
    private Integer [] ids;

    private List<Items> itemsList;

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }



    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }



    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
