package com.syl.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.IWorkOderMangeService;
import com.syl.dao.IWorkOderMangerDao;
import com.syl.domain.WorkOderManage;
@Service
@Transactional
public class WorkOderMangeServiceImpl implements IWorkOderMangeService {

	@Autowired
	private IWorkOderMangerDao workOrderMangerDao;
	@Override
	public void save(WorkOderManage model) {
		workOrderMangerDao.save(model);
	}

}
