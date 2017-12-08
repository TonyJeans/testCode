package com.syl.bos.web.action;

import java.util.List;

import org.apache.xml.resolver.helpers.PublicId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IFunctionService;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.Function;

/**
 * 权限管理
 * 
 * @author ainsc
 *
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	@Autowired 
	private IFunctionService functionSerice;

	public String listajax() {
	List<Function> list = 	functionSerice.findAll();
	this.jave2Json(list, new String[]{"parentFunction","roles"});
		//this.jave2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * 添加权限
	 * @return
	 */
	public String add() {
		functionSerice.save(model);
		return LIST;
	}
	
	public String pageQuery() {
		//类属性与PageBean重名,Structs封装给了类,导致分页失败
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		
		functionSerice.pageQuery(pageBean);
		this.jave2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	public String findMenu() {
		List<Function> list = functionSerice.findMenu();
		this.jave2Json(list,new String[]{"parentFunction","roles","children"});
		return NONE;
	}
}
