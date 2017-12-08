package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示登录和注册页面的Controller
 */
@Controller
public class PageController {
    @RequestMapping("/page/register")
    public String showRegister() {

        return "register";
    }
//http://localhost:8089/page/login?url=http://localhost:8092/order/order-cart.html
    @RequestMapping("/page/login")
    public String showLogin(String url, Model model){
        model.addAttribute("redirect",url);
        return "login";
    }
}
