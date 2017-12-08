package com.syl.dao;

import com.syl.dao.base.IBaseDao;
import com.syl.domain.User;

public interface IUserDao extends IBaseDao<User> {

	User findUserByUsernameAndPassword(String username, String password);

	User findUserByUsername(String username);

}
