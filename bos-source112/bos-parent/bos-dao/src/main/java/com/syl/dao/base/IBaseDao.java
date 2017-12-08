package com.syl.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Region;
import com.syl.domain.Staff;

public interface IBaseDao<T> {

	void save(T entity);

	void delete(T entity);

	void update(T entity);
	
	void saveOrUpdate(T entity);

	T findById(Serializable id);

	//List<T> findAll(T entity);
	List<T> findByCriteria(DetachedCriteria detachedCriteria);
	
	List<T> findAll();
	
	void executeUpdate(String queryName,Object ...objects);
	
	void pageQuery(PageBean pageBean);
}
