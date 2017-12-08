package cn.syl.shiro;

import cn.syl.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    //密码比较

    /**
     *
     * @param token 用户名输入的帐号和密码  由AuthRealm 传入
     * @param info 代码从密码中得到的加密数据(数据库密码)
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        //将用户在页面输入的原始密码加密   param : 1.用户页面填写的密码, 加密的盐

        //String pwd = Encrypt.md5(upToken.getPassword().toString(), upToken.getUsername());
        String pwd = Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());

        //3取出数据库加密的密码
        Object dbPwd = info.getCredentials();  //从AuthRealm doGetAuthenticationInfo传入的密码,数据库查询的密码.

        return  this.equals(pwd,dbPwd);

    }
}
