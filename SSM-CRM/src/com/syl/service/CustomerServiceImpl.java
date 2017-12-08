package com.syl.service;

import com.syl.dao.CustomerMapper;
import com.syl.dao.DickMapper;
import com.syl.pojo.BaseDict;
import com.syl.pojo.Customer;
import com.syl.pojo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private DickMapper dickMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<BaseDict> findDictByCode(String code) {
        List<BaseDict> dictList = dickMapper.findDictByCode(code);
        return dictList;
    }

    @Override
    public List<Customer> findCustomerByVo(QueryVo vo) {
        return customerMapper.findCustomerByVo(vo);
    }

    @Override
    public Integer findCustomerByVoCount(QueryVo vo) {
        return customerMapper.findCustomerByVoCount(vo);
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerMapper.findCustomerById(id);
    }

    @Override
    public void updateCustomerById(Customer customer) {
        customerMapper.updateCustomerById(customer);
    }

    @Override
    public void delCustomerById(Long id) {
        customerMapper.delCustomerById(id);
    }
}
