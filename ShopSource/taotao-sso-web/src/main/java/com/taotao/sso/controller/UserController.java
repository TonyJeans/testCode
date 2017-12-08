package com.taotao.sso.controller;

import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotaoo.common.utils.CookieUtils;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户处理
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${TT_TOKEN}")
    private String TT_TOKEN;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody//jack包 406  //.html后缀springmvc返回非html内容会报错406
    public TaotaoResult checkUserData(@PathVariable String param, @PathVariable Integer type){
        TaotaoResult result = userService.checkData(param, type);
        return result;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        TaotaoResult register = userService.register(user);
        return register;
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletResponse response, HttpServletRequest request){
        TaotaoResult result = userService.login(username, password);
        //把token写入cookie
        if (result.getStatus()==200) CookieUtils.setCookie(request,response,TT_TOKEN,result.getData().toString());
        return result;
    }


//http://localhost:8084/user/token/dedd9-...5?callback=jQuery3048028&_=1503323090778
//    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET
//    ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public String getUserByToken(@PathVariable String token,String callback){
//        TaotaoResult result = userService.getUserByToken(token);
//        //是否为jsonp请求
//        if (StringUtils.isNotBlank(callback)){
//            return callback+"("+JsonUtils.objectToJson(result)+");";//jQuery3048028&_=1503323090778({data})
//        }
//        return JsonUtils.objectToJson(result);
//
//    }

    //jsonp方式2 :spring 4.1(4.2)
    //http://localhost:8084/user/token/dedd9-...5?callback=jQuery3048028&_=1503323090778
    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result = userService.getUserByToken(token);
        //是否为jsonp请求
        if (StringUtils.isNotBlank(callback)){
           // return callback+"("+JsonUtils.objectToJson(result)+");";//jQuery3048028&_=1503323090778({data})
            MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
            jacksonValue.setJsonpFunction(callback);
            return jacksonValue;
        }
        return result;

    }



    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult safeLogonOut(@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
        TaotaoResult result = userService.delTokenByToken(token);
        CookieUtils.deleteCookie(request,response,TT_TOKEN);
        return  result;
    }


}
