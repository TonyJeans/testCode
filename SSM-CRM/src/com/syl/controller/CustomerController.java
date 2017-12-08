package com.syl.controller;

import cn.itcast.utils.Page;
import com.syl.pojo.BaseDict;
import com.syl.pojo.Customer;
import com.syl.pojo.QueryVo;
import com.syl.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //  @Value("customer.dict.source")
    @Value("${customer.dict.source}")
    private String source;

    @Value("${customer.dict.industry}")
    private String industry;

    @Value("${customer.dict.level}")
    private String level;

    @RequestMapping("/list")
    public String list(QueryVo vo, Model model) throws Exception {
        //客户来源
        List<BaseDict> sourceList = customerService.findDictByCode(source);
        //所属行业
        List<BaseDict> industryList = customerService.findDictByCode(industry);
        //客户级别
        List<BaseDict> levelList = customerService.findDictByCode(level);

        if (vo != null && vo.getCustName() != null && vo.getCustName().length() > 0) {

            vo.setCustName(new String(vo.getCustName().getBytes("iso8859-1"), "utf-8"));
        }


        if (vo.getPage() == null) {
            vo.setPage(1);
        }
        //查询开始条数  limit star ,end=10(已知)
        // limit 0,10
        //limit 10,10
        //limit 20,10
        //(当前页-1)*每页显示条数
        vo.setStart((vo.getPage() - 1) * vo.getSize());


        //查询数据和数据总数
        List<Customer> customerList = customerService.findCustomerByVo(vo);
        Integer count = customerService.findCustomerByVoCount(vo);

        Page<Customer> page = new Page<Customer>();
        page.setTotal(count);//数据总共页数,数据库查询
        page.setSize(vo.getSize());//每页显示条数,已知
        page.setPage(vo.getPage());//当前页,已知
        page.setRows(customerList);//数据,数据库查询

        model.addAttribute("page", page);

        //下拉列表的数据
        model.addAttribute("fromType", sourceList);
        model.addAttribute("industryType", industryList);
        model.addAttribute("levelType", levelList);

        //数据回显
        model.addAttribute("custName", vo.getCustName());
        model.addAttribute("custSource", vo.getCustSource());
        model.addAttribute("custIndustry", vo.getCustIndustry());
        model.addAttribute("custLevel", vo.getCustLevel());

        return "customer";
    }

    @RequestMapping("/detail")
    @ResponseBody//表示该方法的返回结果直接写入HTTP response body中
    //http://localhost/crm0523/customer/detail.do?id=14
    public Customer detail(Long id) {
        Customer customer = customerService.findCustomerById(id);
        return customer;
    }

    @RequestMapping("/update")
    public String update(Customer customer) {
        customerService.updateCustomerById(customer);
        return "customer";
    }

    @RequestMapping("/delete")
    public String delete(Long id) {
        customerService.delCustomerById(id);
        return "customer";
    }
}


