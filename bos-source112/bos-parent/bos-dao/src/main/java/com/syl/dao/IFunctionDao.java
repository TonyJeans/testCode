package com.syl.dao;

import java.util.List;

import com.syl.dao.base.IBaseDao;
import com.syl.domain.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	List<Function> findFunctionListByUserId(String id);

	List<Function> findAllMenu();

	List<Function> findMenuByUserId(String id);

}
