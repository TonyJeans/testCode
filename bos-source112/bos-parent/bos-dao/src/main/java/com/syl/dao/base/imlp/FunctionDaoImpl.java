package com.syl.dao.base.imlp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syl.dao.IFunctionDao;
import com.syl.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImp<Function> implements IFunctionDao {

	//@Override
	public List<Function> findAll() {
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据用户id查询对应的权限
	 */
	@Override
	public List<Function> findFunctionListByUserId(String id) {
		//Object[3] Order function role  --->List<Funciton> 3
		String hql = "SELECT distinct f From Function f LEFT OUTER JOIN f.roles r"
				+ " LEFT OUTER JOIN r.users u Where u.id = ? ";
		List<Function> find = (List<Function>) this.getHibernateTemplate().find(hql, id);
		return find;
	}

	@Override
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu ='1' Order By f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Function> findMenuByUserId(String id) {
		String hql = "SELECT distinct f From Function f LEFT OUTER JOIN f.roles r"
				+ " LEFT OUTER JOIN r.users u Where u.id = ? "
				+ "AND f.generatemenu ='1' Order By f.zindex desc";
		List<Function> find = (List<Function>) this.getHibernateTemplate().find(hql, id);
		return find;
	}
}
