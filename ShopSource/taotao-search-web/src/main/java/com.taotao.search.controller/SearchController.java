package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResultVo;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 搜索服务
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model)
    throws Exception{

      //  int a=1/0;

            queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            SearchResultVo resultVo = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
            model.addAttribute("query",queryString);
            model.addAttribute("totalPages",resultVo.getTotalPages());
            model.addAttribute("itemList",resultVo.getItemList());
            model.addAttribute("page",page);



        return "search";
    }
}
