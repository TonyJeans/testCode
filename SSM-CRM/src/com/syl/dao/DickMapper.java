package com.syl.dao;

import com.syl.pojo.BaseDict;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
public interface DickMapper {
    List<BaseDict> findDictByCode(String code);
}
