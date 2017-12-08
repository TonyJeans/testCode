package com.syl.bos.web.action;

import java.util.List;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IRoleService;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.Role;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private IRoleService service;

	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	// 页面回显的时候才需要
	public String getFunctionIds() {
		return functionIds;
	}

	/**
	 * 添加
	 */
	public String add() {
		service.save(model,functionIds);
		return LIST;
	}
	
	public String pageQuery() {
		service.pageQuery(pageBean);
		this.jave2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	
	/**
	 * 查询所有的角色
	 * @return
	 */
	public String listajax() {
		List<Role> list = service.findAll();
		this.jave2Json(list, new String[]{"functions","users"} );
		return NONE;
	}
	
	

}
