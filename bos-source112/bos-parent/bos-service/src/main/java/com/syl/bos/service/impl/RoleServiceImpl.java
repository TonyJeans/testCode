package com.syl.bos.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IRoleService;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IRoleDao;
import com.syl.domain.Function;
import com.syl.domain.Role;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao dao;
	
	/**
	 * 保存角色,关联权限
	 * @param role
	 * @param functionIds
	 */
	public void save(Role role,String functionIds) {
		dao.save(role);
		
		if (StringUtils.isNotBlank(functionIds)) {
			String[] split = functionIds.split(",");
			for (String funid : split) {
				//角色关联权限(角色inverse不能为true)
				//手动构造一个权限对象,设置id,对象状态:托管(瞬时没有Id)
				Function function = new Function(funid);
				role.getFunctions().add(function);
			}
		}
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}
}
