package com.syl.bos.service;

import java.util.List;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Region;

public interface IRegionService {

	void saveBatch(List<Region> regionList);

	void pageQuery(PageBean pageBean);

	List<Region> findAll();

	List<Region> findListByQ(String q);

}
