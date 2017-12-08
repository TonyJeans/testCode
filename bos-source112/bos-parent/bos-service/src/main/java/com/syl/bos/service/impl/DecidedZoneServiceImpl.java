package com.syl.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IDecidedZoneService;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IDecidedZoneDao;
import com.syl.dao.ISubareaDao;
import com.syl.domain.Decidedzone;
import com.syl.domain.Subarea;

@Service
@Transactional//事务主机可以放到接口上面
public class DecidedZoneServiceImpl implements IDecidedZoneService {

	@Autowired
	private IDecidedZoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	
	/**
	 * 添加定区,关联分区(一关联多,多关联一都可以,只要没有放弃外键维护)
	 * 规律:一的一方放弃,多的一方关联
	 */
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for(String id :subareaid){
			Subarea subarea = subareaDao.findById(id);
			// <!-- 一方放弃维护外键了 inverse="true -->
			//model.getSubareas().add(subarea);
			subarea.setDecidedzone(model);//多关联一 分区去关联
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
