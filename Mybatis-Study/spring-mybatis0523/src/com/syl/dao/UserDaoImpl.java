package com.syl.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.syl.pojobak.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
//	private SqlSessionFactory sqlSessionFactory;
//
//	// 构造方法注入
//	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
//		this.sqlSessionFactory = sqlSessionFactory;
//	}
//
//

	@Override
	public User findUserById(Integer id) {
		//SqlSession是线程不安全的,他的最佳使用范围是在方法体内
	//	SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession = this.getSqlSession();
		User user = openSession.selectOne("test.findUserById", id);

		//整合后会话由spring管理,不需要手动关闭
		//openSession.close();
		return user;
	}

	@Override
	public List<User> findUserByUserName(String userName) {
		SqlSession openSession = this.getSqlSession();
		List<User> list = openSession.selectList("test.findUserByUserName",userName);
		return list;
	}

}
