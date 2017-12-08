package com.syl.controller;

import com.syl.pojo.ResultModel;
import com.syl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ainsc on 2017/8/4.
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public String list(String queryString, String catalog_name,
                       String price, Integer page, String sort, Model model) throws Exception {

        ResultModel resultModel = productService.query(queryString, catalog_name, price, page, sort);

        model.addAttribute("result",resultModel);
        model.addAttribute("queryString",queryString);
        model.addAttribute("catalog_name",catalog_name);
        model.addAttribute("price",price);
        model.addAttribute("sort",sort);
        return "product_list";
    }
}
