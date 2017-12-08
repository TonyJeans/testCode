package com.syl.dao;

import java.util.List;

import com.syl.dao.base.IBaseDao;
import com.syl.domain.Subarea;

public interface ISubareaDao extends IBaseDao<Subarea> {

	List<Object> findSubareaGroupByProvince();

}
