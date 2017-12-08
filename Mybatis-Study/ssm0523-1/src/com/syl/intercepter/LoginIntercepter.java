package com.syl.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ainsc on 2017/7/31.
 */
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        HttpSession session = request.getSession();

        //   request.getRequestURI();
        //  request.getContextPath();
        //  request.getRequestURL();

        // request.getContextPath() = "/ssm0523"
        // request.getRequestURL() = {StringBuffer@5301} "http://localhost/ssm0523/login/login"
        // request.getRequestURI() = "/ssm0523/login/login"
        //判断当访问的路径
        if (request.getRequestURI().indexOf("login")>0){
            return  true;
        }

        if (session.getAttribute("username")!=null){
            return  true;
        }
     //   response.sendRedirect("/WEB-INF/jsp/login.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
