package com.taotao.sso.service.impl;

import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户处理
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {
        TbUserExample exp = new TbUserExample();
        TbUserExample.Criteria criteria = exp.createCriteria();
        //判断用户名是否可用
        if (type==1){
            criteria.andUsernameEqualTo(data);
        }else if (type==2){
            criteria.andPhoneEqualTo(data);
        }else if (type==3){
            criteria.andEmailEqualTo(data);
        }else {
            return TaotaoResult.build(400,"请求参数出错");
        }
        List<TbUser> list = userMapper.selectByExample(exp);
        if (list!=null&&list.size()>0){
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);

    }

    @Override
    public TaotaoResult register(TbUser user) {
       //检查数据
        if (StringUtils.isBlank(user.getUsername())){
            return TaotaoResult.build(400,"用户名不能为空");
        }
        TaotaoResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()) {
            return TaotaoResult.build(400,"用户名重复");
        }

        if (StringUtils.isBlank(user.getPassword())){
            return TaotaoResult.build(400,"密码不能为空");
        }

        if (StringUtils.isNotBlank(user.getPhone())){
            result = checkData(user.getPhone(), 2);
            if (!(boolean)result.getData()){
                  return TaotaoResult.build(400,"电话号码重复");
            }
        }

        if (StringUtils.isNotBlank(user.getEmail())){
            result = checkData(user.getEmail(), 3);
            if (!(boolean)result.getData()){
                return TaotaoResult.build(400,"邮箱重复");
            }
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
       //验证用户名密码
        //生成token使用uuid
        //把用户信息保存到redis
        //设置key过期时间
        //返回登录成功,把token返回

        TbUserExample exp = new TbUserExample();
        TbUserExample.Criteria criteria = exp.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(exp);
        if (list==null||list.size()==0){
            //用户名不存在
            return TaotaoResult.build(400,"用户名或密码不正确");
        }
        TbUser user = list.get(0);
        String md5value =user.getPassword();

        if (!md5value.equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            //密码不正确
            return TaotaoResult.build(400,"用户名或密码不正确");
        }
        //用户信息保存到redis,key是token,value为用户信息
        String token = UUID.randomUUID().toString();
        user.setPassword(null);//清空密码
        jedisClient.set(USER_SESSION+":"+token, JsonUtils.objectToJson(user));
        //设置key过期时间
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if (StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户登录过期");
        }
        //重置session过期时间
        jedisClient.expire(USER_SESSION + ":" + token,SESSION_EXPIRE);
        //json转成对象再返回(直接返回json会有/"/"转意符号)
        TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);

       // return TaotaoResult.ok(json);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult delTokenByToken(String token) {
        //2秒后删除这个session
        jedisClient.expire(USER_SESSION + ":" + token,2);
        return TaotaoResult.ok();
    }
}
