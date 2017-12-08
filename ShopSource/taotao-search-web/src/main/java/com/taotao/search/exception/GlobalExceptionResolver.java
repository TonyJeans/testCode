package com.taotao.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException
            (HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        //控制台打印异常
        //向日志文件中写入异常
        //发邮件\短信通知
        //展示错误页面
        logger.info("进入全局异常处理器");
        logger.debug("debug-Handler"+handler.getClass());
        e.printStackTrace();
        logger.error("系统发生异常",e);
        //Jmail

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message","您的电脑有问题");
        modelAndView.setViewName("error/exception");
        return  modelAndView;
    }
}
