package com.syl.dao.base.imlp;
import org.springframework.stereotype.Repository;

import com.syl.dao.IStaffDao;
import com.syl.dao.base.IBaseDao;
import com.syl.domain.Staff;

// IStaffDao extends IBaseDao<Staff>
@Repository
public class StaffDaoImpl extends BaseDaoImp<Staff> implements IStaffDao {

}
