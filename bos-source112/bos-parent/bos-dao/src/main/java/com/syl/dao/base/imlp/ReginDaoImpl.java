package com.syl.dao.base.imlp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syl.dao.IReigonDao;
import com.syl.domain.Region;

@Repository
public class ReginDaoImpl extends BaseDaoImp<Region> implements IReigonDao {

	/**
	 * 根据q模糊查询
	 */
	@Override
	public List<Region> findListByQ(String q) {
		String hql = "From Region r where  r.shortcode Like ? OR r.citycode Like ? OR r.city Like ? OR r.province Like ? OR r.district Like ? ";
		List<Region> find = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return find;
	}

}
