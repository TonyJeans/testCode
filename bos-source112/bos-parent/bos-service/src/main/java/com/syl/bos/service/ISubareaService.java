package com.syl.bos.service;

import java.util.List;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Subarea;

public interface ISubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

	List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	List<Object> findSubareaGroupByProvince();

}
