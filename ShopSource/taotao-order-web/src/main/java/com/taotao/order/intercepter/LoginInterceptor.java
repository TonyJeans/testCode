package com.taotao.order.intercepter;

import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotaoo.common.utils.CookieUtils;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户是否登录的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //执行handler之前
        //true放行
        //从Cookie中取token,没有则跳转sso登录,登录成功后跳转回来
        //需要当前请求页面的url
        //取到toen,调用sso判断是否已经登录
        //取到用户信息放行


        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //获取当前请求的url
        String requestURL = request.getRequestURL().toString();
        if (StringUtils.isBlank(token)){  //没有取到token
            //跳转到用户页面
            response.sendRedirect(SSO_URL+"/page/login?url="+requestURL);
            return false;
        }
        TaotaoResult taotaoResult = userService.getUserByToken(token);

        if (taotaoResult.getStatus()!=200){ //通过token没有取到用户信息token过期
            response.sendRedirect(SSO_URL+"/page/login?url="+requestURL);
            return false;
        }

        //取到用户信息,把用户信息放到request里面,controller handler接收
       TbUser user= (TbUser) taotaoResult.getData();
        request.setAttribute("user", user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //hanlder执行之后
        //modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    //modelandview返回之后
        //异常处理
    }
}
