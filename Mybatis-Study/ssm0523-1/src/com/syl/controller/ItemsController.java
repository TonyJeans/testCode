package com.syl.controller;

import com.syl.exception.MessageException;
import com.syl.pojo.Items;
import com.syl.service.ItemsSerice;
import com.syl.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ainsc on 2017/7/29.
 * 控制器
 */
@Controller
//窄化请求映射http://localhost/ssm0523/items/list.action
@RequestMapping("/items")
public class ItemsController {
    //    @Autowired //自动注入
//    @Qualifier
    @Resource(name = "itemSerice")
    private ItemsSerice itemsSerice;

    @RequestMapping(value = {"/list","/index"}, method = RequestMethod.GET)
    public ModelAndView itemsList() throws MessageException {
        //  int i =1/0;异常演示
        List<Items> list = itemsSerice.list();
//      if (null==null){
//          throw new MessageException("商品信息不能为空");
//      }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemList", list);
        modelAndView.setViewName("itemList");
        return modelAndView;
    }

    /**
     * spring mvc中默认支持的4中参数类型,加不加都可以
     *
     * @param request
     * @param //response
     * @param //session
     * @param model
     * @return
     */
    //通过PathVariable可以接收url传入的参数
    //<a href="${pageContext.request.contextPath }/items/itemEdit/${item.id}">修改</a>
    @RequestMapping("/itemEdit/{id}")//支持RESTFULL方式
    public String itemEdit(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {

        // String idStr = request.getParameter("id");
        Items items = itemsSerice.findItemsById(id);
        //Model模型,模型放入了返回给页面的数据
        //model底成是request域,但进行了扩展
        model.addAttribute("item", items);
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
    // public  void update(Items items,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    public String update(MultipartFile pictureFile, Items items, Model model, HttpServletRequest request) throws ServletException, IOException {
        //   public  String update(@RequestParam("id") Integer id, @RequestParam("name") String xxx, Float price, String detail){
//1,        Items items = new Items();
//        items.setId(id);
//        items.setName(name);
//        items.setPrice(price);
//        items.setDetail(detail);
//        items.setCreatetime(new Date());
//        itemsSerice.updateItems(items);
//        return "success";

//获取图片名称
        //使用随机生成的字符串+原图片扩展名
        //保存图片到硬盘
        //图片名称路径保存数据库
        String filename = pictureFile.getOriginalFilename();
        System.out.println("filename = " + filename);
        filename = UUID.randomUUID().toString() +
                filename.substring(filename.lastIndexOf("."));
        //保存到硬盘
        pictureFile.transferTo(new File("D:\\test\\" + filename));
        items.setPic(filename);

        //2.
        //  items.setCreatetime(new Date());
        itemsSerice.updateItems(items);
        //返回数据
        // request.setAttribute("",obj);
        //指定返回的页面,如果返回值为void不走mvc组件:
        // request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request,response);

        //重定向:url改变 request不可不可保留
        //model扩展了request使得重定向model数据也能保留
        //  request.setAttribute("id",items.getId());//丢失
        // model.addAttribute("id",items.getId());
        //RESTFULL http://localhost/ssm0523/items/itemEdit?id=1  404
        return "redirect:itemEdit/" + items.getId();


        //请求转发 url不发生改变,request域中的数据可以转发到方法中
        //action-->到action
        //  model.addAttribute("id",items.getId());

        //  forward:itemEdit.action是相对路径,就是相对与当前类指定的items目录,在当前目录可以只用相对路径跳转某个action方法中
        //  forward:/itemEdit.action为绝对路径,绝对路径从项目名后面开始计算
        //  return "forward:itemEdit.action";
        //return "forward:/items/itemEdit.action";

    }

    @RequestMapping("/search")
    public String search(QueryVo vo) {
//        如果controller中接收的是vo name要是属性.属性.属性
        System.err.println(vo);
        return "";
    }

    @RequestMapping("/delAll")
    public String delAll(QueryVo vo) {
        System.out.println(vo);
        return "";

    }

    @RequestMapping("/updateAll")
    public String updateAll(QueryVo vo) {
        System.out.println(vo);

        return "";
    }

    //导入jaskjson的jar包在controllr的方法中使用@RequestBody让springmvc将json格式字符串自动转化成java对象
    @RequestMapping("/sendJson")
    @ResponseBody   //给页面响应json(pojo自动转化)
    public Items sendJson(@RequestBody Items items) {
        System.out.println(items);
        return items; //给页面响应json

    }

}
