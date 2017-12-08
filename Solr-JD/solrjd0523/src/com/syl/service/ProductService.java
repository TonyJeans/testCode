package com.syl.service;

import com.syl.pojo.ResultModel;
import org.springframework.ui.Model;

/**
 * Created by ainsc on 2017/8/4.
 */
public interface ProductService {

    ResultModel query(String queryString, String catalog_name,
                      String price, Integer page, String sort) throws  Exception;
}
