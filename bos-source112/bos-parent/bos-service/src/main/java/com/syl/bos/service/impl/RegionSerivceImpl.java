package com.syl.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IRegionService;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IReigonDao;
import com.syl.domain.Region;

@Service
@Transactional
public class RegionSerivceImpl implements IRegionService {
	@Autowired
	private IReigonDao reginDao;
	@Override
	public void saveBatch(List<Region> regionList) {
		for (Region region : regionList) {
			reginDao.saveOrUpdate(region);
		}
	}
	
	

	/**
	 * 区域
	 */

	@Override
	public void pageQuery(PageBean pageBean) {
		reginDao.pageQuery(pageBean);
	}


/**
 * 查询所有区域
 */
	@Override
	public List<Region> findAll() {
		return reginDao.findAll();
	}



	/**
	 * 根据页面输入进行模糊查询
	 */
@Override
public List<Region> findListByQ(String q) {
	
	return reginDao.findListByQ(q);
}
	

}
