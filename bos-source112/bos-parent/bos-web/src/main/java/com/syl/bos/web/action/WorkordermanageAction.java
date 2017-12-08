package com.syl.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IWorkOderMangeService;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.WorkOderManage;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<WorkOderManage> {

	@Autowired
	private IWorkOderMangeService workMangerService;
	
	public String add() throws Exception{
		String f = "1";
		try {
			workMangerService.save(model);
			
		} catch (Exception e) {
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
}
