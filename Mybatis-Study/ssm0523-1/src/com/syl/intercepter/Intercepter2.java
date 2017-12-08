package com.syl.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ainsc on 2017/7/31.
 * 拦截器
 */
public class Intercepter2 implements HandlerInterceptor {
    /**
     * 执行时机:Controller没有被执行,ModelAndView没有被返回
     *使用场景:权限验证
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return true放行,false则被拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("==========Intercepter2.preHandle");
        return true;
    }

    /**
     *执行时机:Controller已经执行,ModelAndView没有被返回
     *使用场景:可以在此方法中设置全局的数据处理业务
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("=============Intercepter2.postHandle");
    }

    /**
     *执行时机:Controller已经执行,ModelAndView已经返回
     *使用场景:记录操作日志,记录ip时间
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("==============Intercepter2.afterCompletion");
    }
}
