package com.syl.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IStaffService;
import com.syl.bos.utils.PageBean;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.Staff;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;

	/**
	 * 添加去派员
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}

	/**
	 * 分页查询
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		staffService.pageQuery(pageBean);
		this.jave2Json(pageBean, new String[] { "decidedzones","currentPage", "detachedCriteria", "pageSize" });
		return NONE;
	}

	/**
	 * 
	 * 取派员批量删除
	 */
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	@RequiresPermissions("staff-delete")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}

	//@RequiresPermissions("staff-edit")
	public String edit() {
		
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("staff-edit");
		
		// 警告!表单提交的数据不全面,如果直接调用model更新会置空某些字段,甚至外键!
		// staffService.update(model);
		// 先查找再更新
		Staff staff = staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda()); // PDA
		staff.setStandard(model.getStandard());// 单位
		staff.setStation(model.getStation());// 标准
		staffService.update(staff);
		return LIST;
	}
	
	/**
	 * 查询所有未删除的取派员,返回Json数据
	 * 
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		this.jave2Json(list, new String[]{"decidedzones"});
		return NONE;
	}

	public static void main(String[] args) {
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(2);
		pageBean.setPageSize(30);
		String json = JSONObject.fromObject(pageBean).toString();
		System.out.println(json);

	}
}
