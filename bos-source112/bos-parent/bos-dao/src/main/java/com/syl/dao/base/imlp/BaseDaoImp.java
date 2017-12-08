package com.syl.dao.base.imlp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.syl.bos.utils.PageBean;
import com.syl.dao.base.IBaseDao;
import com.syl.domain.Staff;

//<bean id=userDao class=UserDaoImpl>
//	<prop name=sessionFactory ref= sessionFactory> 
//</bean>
public class BaseDaoImp<T> extends HibernateDaoSupport implements IBaseDao<T> {

	// 某个实体的类型
	private Class<T> entityClass;

	@Resource // 根据类型注入 HibernateDaoSupport 的setSessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public BaseDaoImp() {
		Type superclass = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) superclass;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		// 获取T ClassName extends BaseDaoImp<T>
		entityClass = (Class<T>) typeArguments[0];

	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {

		return this.getHibernateTemplate().get(entityClass, id);
	}

//	@Override
//	public List<T> findAll(T entity) {
//		String hql = "From " + entityClass.getSimpleName();
//		return (List<T>) this.getHibernateTemplate().find(hql);
//	}
	
	@Override
	public List<T> findAll() {
		String hql = "From " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	// 执行更新
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		// User.hbm.xml user.editpassword
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		//为Hql语句中?赋值
		int count = 0;
		for (Object object : objects) {
			query.setParameter(count++, object);
		}
		
		// exe
		query.executeUpdate();
	}

	/**
	 * 通用查询方法
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//total
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		//rows
		detachedCriteria.setProjection(null);
		//指定hibernate框架封装对象的方式,Object : Function、Role 、User ---》Function  Object[3]= User+User+User -->List<User>
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
	}



}
