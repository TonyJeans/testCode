package com.taotao.sso.service;

import com.taotao.pojo.TbUser;
import com.taotaoo.common.utils.TaotaoResult;

public interface UserService {
    TaotaoResult checkData(String data,int type);
    TaotaoResult register(TbUser user);
    TaotaoResult login(String username,String password);
    TaotaoResult getUserByToken(String token);
    TaotaoResult delTokenByToken(String token);
}
