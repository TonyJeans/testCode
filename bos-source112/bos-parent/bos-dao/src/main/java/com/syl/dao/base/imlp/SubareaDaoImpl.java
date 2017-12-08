package com.syl.dao.base.imlp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.syl.dao.ISubareaDao;
import com.syl.domain.Subarea;


@Repository
public class SubareaDaoImpl extends BaseDaoImp<Subarea> implements ISubareaDao {

	@Override
	public List<Object> findSubareaGroupByProvince() {
		//sql SELECT r.province,COUNT(*) from bc_subarea s LEFT JOIN bc_region r
		//ON s.region_id = r.id  GROUP BY r.province
		String hql = "SELECT r.province,COUNT(*) from Subarea s LEFT JOIN s.region r GROUP BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
 