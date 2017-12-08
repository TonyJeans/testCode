package com.taotao.controller;

import com.taotao.search.service.SearchItemService;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护
 */
@Controller
public class IndexMangerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex(){
        TaotaoResult result = searchItemService.importItemsToIndex();
        return result;
    }
}
