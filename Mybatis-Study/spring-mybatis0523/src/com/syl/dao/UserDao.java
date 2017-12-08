package com.syl.dao;

import java.util.List;

import com.syl.pojobak.User;

public interface UserDao {

	User findUserById(Integer id);
	List<User> findUserByUserName(String userName);
}
