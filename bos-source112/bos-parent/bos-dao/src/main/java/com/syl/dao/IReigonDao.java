package com.syl.dao;

import java.util.List;

import com.syl.dao.base.IBaseDao;
import com.syl.domain.Region;

public interface IReigonDao extends IBaseDao<Region> {

	List<Region> findListByQ(String q);

	
}
