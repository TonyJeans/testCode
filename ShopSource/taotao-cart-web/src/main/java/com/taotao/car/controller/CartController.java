package com.taotao.car.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotaoo.common.utils.CookieUtils;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理
 */
@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String TT_CART;

    @Value("${CART_EXPIRE}")
    private int CART_EXPIRE;

    @Autowired
    private ItemService itemService;



    //http://localhost:8090/cart/add/1264715.html?num=8
    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable("itemId") Long id, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        System.err.println("itemId--->" + id);
        System.err.println("num--->" + num);
        boolean isExist = false;//商品不存在

        //取购物车商品列表
        List<TbItem> cartItemList = getCartItemList(request);
        for (TbItem item : cartItemList) {
            //判断商品在购物车中是否存在,存在则增加
            if (item.getId() == id.longValue()) {  //Long or use equals()
                item.setNum(item.getNum() + num);
                isExist = true;
                break;
            }
        }
        //不存在商品,查询商品信息并新增商品
        if (!isExist) {
            TbItem tbItem = itemService.getItemsById(id);
            tbItem.setNum(num);
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            //商品添加到购物车
            cartItemList.add(tbItem);

        }

        //购物车列表写入Cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
        return "cartSuccess";

    }

    private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从Cookie
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if (StringUtils.isBlank(json)) {
            //如果没有内容返回空的列表
            return new ArrayList<>();
        }

        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request){
        //取cookie传递给jsp
        List<TbItem> cartItemList = getCartItemList(request);
        request.setAttribute("cartList",cartItemList);
        return "cart";

    }
   // $.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".html" //html会导致406
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateItemNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> cartItemList = getCartItemList(request);
        for (TbItem tbItem:cartItemList){
            if (tbItem.getId().equals(itemId)){
                tbItem.setNum(num);
                break;
            }
        }
        //写回Cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId ,HttpServletRequest request,HttpServletResponse response){
        //cookie取出对应商品,删除,写回cookie,页面重定向
        List<TbItem> cartItemList = getCartItemList(request);
        for (TbItem tbItem:cartItemList){
            if (tbItem.getId().equals(itemId)){
                //删除商品
                cartItemList.remove(tbItem);
                break;
            }
        }

        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);

        //重定向
        return "redirect:/cart/cart.html";
    }
}
