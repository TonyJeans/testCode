package com.syl.controller;

import com.syl.pojo.Items;
import com.syl.service.ItemsSerice;
import com.syl.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by ainsc on 2017/7/29.
 */
@Controller
public class ItemsController {
//   @Autowired //自动注入
//    @Qualifier
    @Resource(name="itemSerice")
    private ItemsSerice itemsSerice;

    @RequestMapping("/list")
    public ModelAndView itemsList(){
      List<Items> list = itemsSerice.list();
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("itemList",list);
      modelAndView.setViewName("itemList");
      return modelAndView;
    }

    /**
     * spring mvc中默认支持的4中参数类型,加不加都可以
     * @param request
     * @param //response
     * @param //session
     * @param model
     * @return
     */
    @RequestMapping("/itemEdit")
    public  String itemEdit(HttpServletRequest request,  Model model){

        String idStr = request.getParameter("id");
        Items items = itemsSerice.findItemsById(Integer.parseInt(idStr));
        //Model模型,模型放入了返回给页面的数据
        //model底成是request域,但进行了扩展
        model.addAttribute("item",items);
        //如果springmvc返回一个简单的string字符串,那么springmvc认为这个是页面的名称
        //也可用ModelAndView的方式
        return "editItem";

    }
    //springmvc可以直接接收基本数据类型,包括String
    //自动类型转化
    //controller方法接收的参数变量名称必须等于input name
    @RequestMapping("/updateitem")
   //1. public  String update(Integer id,String name,Float price,String detail){
   //2.也可以直接接收pojo类型,要求pojo name与input name
    public  String update(Items items){
//1,        Items items = new Items();
//        items.setId(id);
//        items.setName(name);
//        items.setPrice(price);
//        items.setDetail(detail);
//        items.setCreatetime(new Date());
//        itemsSerice.updateItems(items);
//        return "success";


        //2.
      //  items.setCreatetime(new Date());
        itemsSerice.updateItems(items);
        return "success";

    }

    @RequestMapping("/search")
    public  String search(QueryVo vo){
//        如果controller中接收的是vo name要是属性.属性.属性
        System.err.println(vo);
        return "";
    }

}
