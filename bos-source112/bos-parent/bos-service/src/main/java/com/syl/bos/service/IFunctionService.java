package com.syl.bos.service;

import java.util.List;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Function;

public interface IFunctionService {

	List<Function> findAll();

	void save(Function model);

	void pageQuery(PageBean pageBean);

	List<Function> findMenu();

}
