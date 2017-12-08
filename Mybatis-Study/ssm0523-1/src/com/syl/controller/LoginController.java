package com.syl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ainsc on 2017/7/31.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    //登录页面跳转
    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    @RequestMapping("/submit")
    public String submit(String username, String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();

        //正确则放入session
        if (username!=null){
            session.setAttribute("username",username);
        }
        //跳转到列表页面
        return "redirect:/items/list";
    }
}
