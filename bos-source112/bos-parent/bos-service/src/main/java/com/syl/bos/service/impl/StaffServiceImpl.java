package com.syl.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IStaffService;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IStaffDao;
import com.syl.domain.Staff;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{

	@Autowired
	private IStaffDao staffDao1;
	@Override
	public void save(Staff model) {
		staffDao1.save(model);
	}
	
	@Transactional(readOnly=true)//不需要事务的查询方法
	public Staff find() {
		return null;
	
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao1.pageQuery(pageBean);
	}
/**
 * 取派员批量逻辑删除
 */
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String staffIds[] = ids.split(",");
			for (String id : staffIds) {
				staffDao1.executeUpdate("staff.delete", id);
			}
		}
	}

@Override
public Staff findById(String id) {
 return staffDao1.findById(id);
}

/**
 * @param staff
 * 根据取派员id
 */
@Override
public void update(Staff staff) {
	staffDao1.update(staff);
}

@Override
public List<Staff> findListNotDelete() {
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
	detachedCriteria.add(Restrictions.eq("deltag", "0"));
	//detachedCriteria.add(Restrictions.ne("deltag", "1"));
	return staffDao1.findByCriteria(detachedCriteria);
}

}
