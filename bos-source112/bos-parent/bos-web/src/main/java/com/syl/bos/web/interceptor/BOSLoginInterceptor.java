package com.syl.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.syl.bos.utils.BOSUtils;
import com.syl.domain.User;

public class BOSLoginInterceptor extends MethodFilterInterceptor {

 
	//拦截方法
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
	

//		
//		User user = BOSUtils.getLoginUser();
//		if (user==null) {
//			ActionProxy proxy = invocation.getProxy();
//			String actionName = proxy.getActionName();
//			String namespace = proxy.getNamespace();
//			String url = namespace.concat(actionName);
//			System.err.println("拦截的ACTION:"+url);
//			return "login";
//		}
		//放行
		return invocation.invoke();
	}

}
