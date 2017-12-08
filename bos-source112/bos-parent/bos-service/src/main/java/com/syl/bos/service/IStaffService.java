package com.syl.bos.service;

import java.util.List;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Staff;

public interface IStaffService {

	void save(Staff model);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	Staff findById(String id);

	void update(Staff staff);

	List<Staff> findListNotDelete();

}
