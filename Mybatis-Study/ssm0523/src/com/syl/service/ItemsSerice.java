package com.syl.service;

import com.syl.pojo.Items;

import java.util.List;

/**
 * Created by ainsc on 2017/7/29.
 */
public interface ItemsSerice {
    List<Items> list();

    Items findItemsById(Integer id);

    void updateItems(Items items);
}
