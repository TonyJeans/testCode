package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * [{"id":1,"text":"图书、音像、电子书刊","state":"closed"},{"id":74,"text":"家用电器","state":"closed"},{"id":161,"text":"电脑、办公","state":"closed"},{"id":249,"text":"个护化妆","state":"closed"},{"id":290,"text":"钟表","state":"closed"},{"id":296,"text":"母婴","state":"closed"},{"id":378,"text":"食品饮料、保健食品","state":"closed"},{"id":438,"text":"汽车用品","state":"closed"},{"id":495,"text":"玩具乐器","state":"closed"},{"id":558,"text":"手机","state":"closed"},{"id":580,"text":"数码","state":"closed"},{"id":633,"text":"家居家装","state":"closed"},{"id":699,"text":"厨具","state":"closed"},{"id":749,"text":"服饰内衣","state":"closed"},{"id":865,"text":"鞋靴","state":"closed"},{"id":903,"text":"礼品箱包","state":"closed"},{"id":963,"text":"珠宝","state":"closed"},{"id":1031,"text":"运动健康","state":"closed"},
     * {"id":1147,"text":"彩票、旅行、充值、票务","state":"closed"}]
     * @param parentId
     * @return
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }
}
