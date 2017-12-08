package com.taotao.order.controller;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotaoo.common.utils.CookieUtils;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单确认页面Controller
 */
@Controller  //忘记加注解???
public class OrderCartController {

    @Value("${CART_KEY}")
    private String TT_CART;

    @Autowired
    private OrderService orderService;



    /**
     * 订单确认页面
     * @param request
     * @return
     */
    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        //用户登录状态
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println("用户信息"+user.getUsername());
        //根据用户信息去收货列表,展示给页面(模拟)
        //cookie取出购物车商品列表
        //逻辑视图
        List<TbItem> cartItemList = getCartItemList(request);
        request.setAttribute("cartList",cartItemList);
        return "order-cart";
    }

    /**
     * 生成订单
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model){
        TaotaoResult result = orderService.createOrder(orderInfo);
        model.addAttribute("orderId",result.getData().toString());
        model.addAttribute("payment",orderInfo.getPayment());
        DateTime dateTime = new DateTime();
        dateTime=dateTime.plusDays(3); //预计三天后送达
        model.addAttribute("date",dateTime.toString("yyyy-MM-dd"));
        return "success";
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

}
