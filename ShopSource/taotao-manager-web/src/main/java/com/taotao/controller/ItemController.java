package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ainsc on 2017/8/8.
 * 商品管理
 * centos-720
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * {"id":830972,"title":"飞利浦 老人手机 (X2560) 深情蓝 移动联通2G手机 双卡双待","sellPoint":"赠：九安血压计+8G内存！超长待机，关爱无限，更好用！飞利浦简单健康老人手机！外观圆滑，手感极佳！","price":48900,"num":99999,
     * "barcode":null,"image":"http://image.e3mall.cn/jd/4f1d41baa6c84219a622f20a4f1c32bb.jpg","cid":560,"status":1,"created":1425821310000,"updated":1425821310000}
     * @param id
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable("itemId") Long id) {
        TbItem tbItem = itemService.getItemsById(id);
        return tbItem;
    }

    //http://localhost:8081/item/list?page=4&rows=30
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getIemList(@RequestParam(value = "page",required = false) Integer p, Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(p,rows);
        return result;

    }
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item,String desc){
        TaotaoResult result = itemService.addItem(item, desc);
        return result;
    }
}
