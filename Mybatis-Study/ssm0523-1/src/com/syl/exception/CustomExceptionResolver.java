package com.syl.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ainsc on 2017/7/31.
 * 异常处理器自定义实现类
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o 发生异常的地方: 包名+类名+方法名(参数)
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if (e instanceof MessageException){
            MessageException me = (MessageException) e;
            modelAndView.addObject("error",me.getMsg());
        }else {
        modelAndView.addObject("error","未知异常");

        }

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
