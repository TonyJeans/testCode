package com.syl.dao;

import com.syl.pojo.Items;
import com.syl.pojo.QueryVo;

import java.util.List;

/**
 * Created by ainsc on 2017/8/1.
 */
public interface ItemsDao {
    List<Items> findItemsByQueryVo(QueryVo vo);
}
