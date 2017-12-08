package com.syl.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.syl.domain.User;

public class BOSUtils {
	
	// 获取session对象
	private static HttpSession getSesstion() {
		return ServletActionContext.getRequest().getSession();
	}

	public static User getLoginUser() {
		return (User) getSesstion().getAttribute("loginUser");
	}
}
