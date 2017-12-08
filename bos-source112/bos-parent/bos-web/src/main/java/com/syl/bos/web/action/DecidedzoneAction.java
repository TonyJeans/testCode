package com.syl.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IDecidedZoneService;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.crm.Customer;
import com.syl.crm.ICustomerService;
import com.syl.domain.Decidedzone;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	//属性驱动
	private String[] subareaid;
	
	@Autowired
	private IDecidedZoneService decidedService;
	
	//注入crm对象
	@Autowired
	private ICustomerService proxy;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	/**
	 * 添加定区
	 *
	 */
	public String add() {
		decidedService.save(model,subareaid);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		decidedService.pageQuery(pageBean);
		this.jave2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize","subareas","decidedzones"});
		return NONE;
	}
	
	/*********远程调用CRM*********/
	public String findListNotAssociation(){
		List<Customer> list = proxy.findListNotAssociation();
		this.jave2Json(list, new String[]{});
		return NONE;
	}
	
	public String findListHasAssociation(){
		String id = model.getId();
		List<Customer> list = proxy.findListHasAssociation(id);
		this.jave2Json(list, new String[]{});
		return NONE;
	}
	//属性驱动多个客户Id
	private List<Integer> customerIds;
	
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	public String assigncustomerstodecidedzone(){
		String id = model.getId();
		proxy.assigncustomerstodecidedzone(id,customerIds);
		return LIST;
	}
	
}
