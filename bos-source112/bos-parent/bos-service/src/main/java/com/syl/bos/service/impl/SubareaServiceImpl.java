package com.syl.bos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.ISubareaService;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IDecidedZoneDao;
import com.syl.dao.ISubareaDao;
import com.syl.domain.Decidedzone;
import com.syl.domain.Subarea;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {

	@Autowired
	private ISubareaDao subareaDao;

	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}
/**
 * 查询所有为关联的
 */
	@Override
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//分区对象中decidedzone null没有外键
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
		 
	}
	//@Autowired
	//private IDecidedZoneDao decidedzoneDao;

	/**
	 * 根据定区id查询分区
	 */
	/**方法一:导致sql很多!
	 * Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
		Set subareas = decidedzone.getSubareas();
		List<Decidedzone> list = new ArrayList<>(subareas);
	 */
@Override
public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
	//SELECT * FROM bc_subarea s where s.decidedzone_id='123'
	detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
	return subareaDao.findByCriteria(detachedCriteria);
}

	@Override
	public List<Object> findSubareaGroupByProvince() {
		return subareaDao.findSubareaGroupByProvince();
	}

}
