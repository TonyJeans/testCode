package com.syl.controller;

import com.syl.pojo.Items;
import com.syl.pojo.QueryVo;
import com.syl.service.ItemsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ainsc on 2017/8/2.
 */
@Controller
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @RequestMapping(value = "/list.action")
    public String list(@RequestParam(value = "mingcheng",required = false) String name, String detail, Model model) {
        QueryVo vo = new QueryVo();
        if (StringUtils.isNotBlank(name)) {

            vo.setName(name);
            model.addAttribute("mingcheng",name);
        }
        if (StringUtils.isNotBlank(detail)) {

            vo.setDetail(detail);
            model.addAttribute("detail",detail);
        }
        List<Items> itemsList = itemsService.findItemsByQueryVo(vo);
        model.addAttribute("itemList",itemsList);
        return "itemList";
    }
}
