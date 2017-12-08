package com.syl.dao.base.imlp;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.syl.dao.IUserDao;
import com.syl.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImp<User> implements IUserDao {


/**
 * 用户名密码查询用户
 * @return 
 */
	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "From User u Where u.username = ? And u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		String hql = "From User u Where u.username = ? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
