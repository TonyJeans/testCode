package com.syl.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.INoticeBillService;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.crm.Customer;
import com.syl.crm.ICustomerService;
import com.syl.domain.Noticebill;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private INoticeBillService noticeBillService; 
	
	public String findCustomerByTelphone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelphone(telephone);
		this.jave2Json(customer, new String[]{});
		return NONE;
	}
	
	/**保存业务通知单并尝试自动分单*/
	public String add(){
		noticeBillService.save(model);
		return "noticebill_add";
	}
}
