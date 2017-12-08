package com.syl.service;

import com.syl.dao.ItemsMapper;
import com.syl.pojo.Items;
import com.syl.pojo.ItemsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ainsc on 2017/7/29.
 */
@Service("itemSerice")
public class ItemSericeImpl implements  ItemsSerice {

    @Autowired
    private ItemsMapper itemsMapper;


    @Override
    public List<Items> list() {
        //不需要任何查询条件直接new
        ItemsExample example = new ItemsExample();
        return itemsMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public Items findItemsById(Integer id) {
        return  itemsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateItems(Items items) {
        itemsMapper.updateByPrimaryKeyWithBLOBs(items);
    }


}
