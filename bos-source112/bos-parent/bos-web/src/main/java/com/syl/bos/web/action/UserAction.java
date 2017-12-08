package com.syl.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IUSerService;
import com.syl.bos.utils.BOSUtils;
import com.syl.bos.utils.MD5Utils;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.User;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

//id = userAction(首字母小写,默认生成bean id)
@Controller
@Scope("prototype") // 暂时注释,因为多例不会在项目启动的时候加载
public class UserAction extends BaseAction<User> {

	// 属性驱动,接收验证码
	private String checkcode;

	@Autowired
	private IUSerService userService;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	/**
	 * 用户登录,使用shiro框架
	 */
	public String login() {

		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validateCode)) {
			// 使用shiri方式
			// 获得当前对象的状态:未认证
			Subject subject = SecurityUtils.getSubject();
			// 用户名密码令牌对象
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
					MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
			User user = (User) subject.getPrincipal();
			// user放入session
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;
		} else {
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
		// return "";
	}

	/**
	 * 用户登录
	 */
	public String login_bak() {

		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validateCode)) {
			User user = userService.login(model);
			if (user != null) {
				// user放入session
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			} else {
				this.addActionError("用户名或密码错误");
				return LOGIN;
			}

		} else {
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
		// return "";
	}

	/**
	 * 用户注销
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().setAttribute("loginUser", null);
		return LOGIN;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String editPassword() throws IOException {
		String flag = "1";
		User user = BOSUtils.getLoginUser();
		user.setPassword(model.getPassword());
		// 警告!直接更新User对象会把其他的值都更新为null!!!
		// userService.update(user);
		try {
			userService.editPassword(user.getId(), model.getPassword());
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	/**
	 * 
	 */
	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String add() {

		userService.save(model, roleIds);
		return LIST;
	}
	
	public String pageQuery() {
		userService.pageQuery(pageBean);
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
        jsonConfig.setExcludes(new String[]{"noticebills","roles"});
        String json = JSONObject.fromObject(pageBean, jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		
        //日期转化异常!!
		//java.lang.IllegalArgumentException异常
		//java.sql.Date.getHours（Unknown Source）
      //  this.jave2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}

}
