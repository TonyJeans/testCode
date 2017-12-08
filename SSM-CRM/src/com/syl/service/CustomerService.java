package com.syl.service;

import com.syl.pojo.BaseDict;
import com.syl.pojo.Customer;
import com.syl.pojo.QueryVo;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
public interface CustomerService {

    List<BaseDict> findDictByCode(String code);

    List<Customer> findCustomerByVo(QueryVo vo);

    Integer findCustomerByVoCount(QueryVo vo);

    Customer findCustomerById(Long id);

    void updateCustomerById(Customer customer);

    void delCustomerById(Long id);
}
