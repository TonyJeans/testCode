package com.syl.service;

import com.syl.pojo.Items;
import com.syl.pojo.QueryVo;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
public interface ItemsService {
    List<Items> findItemsByQueryVo(QueryVo vo);
}
