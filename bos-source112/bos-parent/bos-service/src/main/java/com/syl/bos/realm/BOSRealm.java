package com.syl.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.syl.dao.IFunctionDao;
import com.syl.dao.IUserDao;
import com.syl.domain.Function;
import com.syl.domain.User;

public class BOSRealm  extends AuthorizingRealm  {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IFunctionDao functionDao;


	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授予权限
		//info.addStringPermission("staff-list");
		//获取当前登录对象
		User user = (User) SecurityUtils.getSubject().getPrincipal();//User user2 = (User) principals.getPrimaryPrincipal();
		List<Function> list=null;
		if ("admin".equals(user.getUsername())) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			list = functionDao.findByCriteria(detachedCriteria);
		}else {
			list = functionDao.findFunctionListByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;

	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("自定义的reale自行了");
		//根据用户名查询数据库中的密码
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		String username = passwordToken.getUsername();
		User user = userDao.findUserByUsername(username);
		if (user==null) {
			//用户名不zai
			return null;
		}
		//框架负责对比数据库与输入的密码
		//简单认证信息对象
		//parm1:绑定了本地线程 从这个取出subject.getPrincipal();
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}





}
