package com.syl.bos.service;

import com.syl.bos.utils.PageBean;
import com.syl.domain.User;

public interface IUSerService {

	User login(User model);


	void editPassword(String id, String password);


	void save(User model, String[] roleIds);


	void pageQuery(PageBean pageBean);

}
