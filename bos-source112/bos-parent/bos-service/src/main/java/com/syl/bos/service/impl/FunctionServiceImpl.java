package com.syl.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IFunctionService;
import com.syl.bos.utils.BOSUtils;
import com.syl.bos.utils.PageBean;
import com.syl.dao.IFunctionDao;
import com.syl.domain.Function;
import com.syl.domain.User;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;
	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}
	@Override
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if (parentFunction!=null&&parentFunction.getId().equals("")) {
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}
	@Override
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BOSUtils.getLoginUser();
		if ("admin".equals(user.getUsername())) {
			list = functionDao.findAllMenu();
		}else {
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}

}
