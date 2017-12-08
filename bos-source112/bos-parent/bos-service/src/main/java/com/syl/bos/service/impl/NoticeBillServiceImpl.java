package com.syl.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syl.bos.service.INoticeBillService;
import com.syl.bos.utils.BOSUtils;
import com.syl.crm.ICustomerService;
import com.syl.dao.IDecidedZoneDao;
import com.syl.dao.INoticeBillDao;
import com.syl.dao.IWorkBillDao;
import com.syl.domain.Decidedzone;
import com.syl.domain.Noticebill;
import com.syl.domain.Staff;
import com.syl.domain.User;
import com.syl.domain.WorkBill;

@Service
@Transactional
public class NoticeBillServiceImpl implements INoticeBillService {

	@Autowired
	private INoticeBillDao noticebillDao;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IDecidedZoneDao decidedZoneDao;
	
	@Autowired
	private IWorkBillDao workBillDao;
	
	@Override
	public void save(Noticebill model) {
		User loginUser = BOSUtils.getLoginUser();
		model.setUser(loginUser);
		noticebillDao.save(model);
		
		//ADDRESS
		String pickaddress = model.getPickaddress();
		//远程调用crm服务,根据取件地址查询定区id
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		//查询到了定区,完成自动分单
		if (decidedzoneId!=null) {
			Decidedzone decidedzone = decidedZoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务通知单关联取派员
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员生成一个工单
			WorkBill workBill = new WorkBill();
			workBill.setAttachbilltimes(0);//追单次数
			workBill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workBill.setNoticebill(model);//工单关联页面通知单
			workBill.setPickstate(WorkBill.PICKSTATE_NO);//取件状态
			workBill.setRemark(model.getRemark());
			workBill.setStaff(staff);
			workBill.setType(WorkBill.TYPE_1);
			workBillDao.save(workBill);
			
			//调用短信平台
		}else{
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
			
		}
	}

}
