package com.syl.bos.service;

import java.util.List;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Role;

public interface IRoleService {

	void save(Role model, String functionIds);

	void pageQuery(PageBean pageBean);

	List<Role> findAll();

}
