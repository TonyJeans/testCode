package com.syl.service;

import com.syl.dao.ItemsDao;
import com.syl.pojo.Items;
import com.syl.pojo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsDao itemsDao;


    public List<Items> findItemsByQueryVo(QueryVo vo) {
        return itemsDao.findItemsByQueryVo(vo);
    }
}
