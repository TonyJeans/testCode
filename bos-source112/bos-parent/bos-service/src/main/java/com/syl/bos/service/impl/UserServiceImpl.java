package com.syl.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IUSerService;
import com.syl.bos.utils.MD5Utils;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IUserDao;
import com.syl.domain.Role;
import com.syl.domain.User;

@Service
@Transactional
public class UserServiceImpl implements IUSerService {
	// 按类型自动装配
	// @Autowired
	// @Qualifier(value="userDao")//按名称注入

	@Autowired
	private IUserDao userDao;

	@Override
	public User login(User user) {
		// 使用MD5
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(), password);

	}

	/**
	 * 更具id修改密码
	 */
	@Override
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password, id);
	}

	/**
	 * 添加一个用户同时关联角色
	 */
	@Override
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		if (roleIds!=null&&roleIds.length>0) {
			for (String id : roleIds) {
				//手动构造托管对象
				//检查User是否能关联,没有放弃外键Invers
				Role role = new Role(id);
				user.getRoles().add(role);
			}
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
