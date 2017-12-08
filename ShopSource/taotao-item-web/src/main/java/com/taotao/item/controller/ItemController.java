package com.taotao.item.controller;

import com.taotao.pojo.ItemVo;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        System.err.println("showItem--->"+itemId);
        TbItem tbItem = itemService.getItemsById(itemId);
        ItemVo itemVo = new ItemVo(tbItem);

        //取商品详情
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        model.addAttribute("item",itemVo);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }
}
