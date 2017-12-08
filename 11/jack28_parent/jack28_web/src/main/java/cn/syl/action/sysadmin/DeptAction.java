package cn.syl.action.sysadmin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.syl.action.BaseAction;
import cn.syl.domain.Dept;
import cn.syl.service.DeptService;
import cn.syl.utils.Page;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门管理
 * @author ainsc
 *
 */
public class DeptAction extends BaseAction implements ModelDriven<Dept> {

	private Dept model = new Dept();
	@Override
	public Dept getModel() {
		return model;
	}
	
	//注入service
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	//分页组件
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	//Request URL:http://localhost:8080/jack28/sysadmin/deptAction_list
	//page.pageNo:2
	public String list() throws Exception {
		page = deptService.findPage("from Dept", page, Dept.class, null);
		//分页条url地址
		page.setUrl("deptAction_list");
		//将page压入栈顶
		super.push(page);
		
		return "list";
		
	}

	/**
	 * 查看
	 * model接收
	 * http://localhost:8080/jack28/sysadmin/deptAction_toview
	 * post :
	 * id=xx
	 */
	public String toview() throws Exception {
		//根据ide查询对象
		Dept dept = deptService.get(Dept.class, model.getId());
		super.push(dept);
		return "toview";
	}

	/**
	 * 进入新增页面  下拉列表
	 * @return
	 * @throws Exception
	 */
	public String tocreate() throws Exception{
		List<Dept> list = deptService.find("from Dept where state=1", Dept.class, null);
		//ActionContext.getContext().put("deptList",list);  //#deptList
		super.put("deptList",list);
		//ActionContext.getContext().getValueStack().set("deptList",list); //root
		return "tocreate";
	}
	/*
	Request URL:http://localhost:8080/jack28/sysadmin/deptAction_insert
		parent.id:4028827c4fb645b0014fb64668550000
		deptName:装箱部门
	 */
	public String insert() throws Exception{
		deptService.saveOrUpdate(model);
		return "alist";
	}

	/**
	 * 进入修改页面
	 * http://localhost:8080/jack28/sysadmin/deptAction_toupdate
	 * page.pageNo:1
	 id:3d00290a-1af0-4c28-853e-29fbf96a2722
	 */
	public String toupdate(){
		//根据id查找
		Dept dept = deptService.get(Dept.class, model.getId());
		super.push(dept); //放入值栈

		//查询父亲部门
		List<Dept> list = deptService.find("from Dept where state=1", Dept.class, null);
		super.put("deptList",list);
		list.remove(dept); //不能把自己设置成父亲,从下拉框集合移除自己

		//页面跳转
		return "toupdate";
	}
//http://localhost:8080/jack28/sysadmin/deptAction_update
	//id:4028827c4fb645b0014fb64668550000
	//parent.id:4028827c4fb6202a014fb6209c730000
	//deptName:船运部cgx
	public String update(){
		super.isValidatePost();
		//deptService.saveOrUpdate(model);  //警告会擦出其他值!
		Dept dept =  deptService.get(Dept.class,model.getId());

		dept.setParent(model.getParent());
		dept.setDeptName(model.getDeptName());

		deptService.saveOrUpdate(dept);

		return "alist";
	}
	//http://localhost:8080/jack28/sysadmin/deptAction_delete
	/*page.pageNo:1
	 selid:on
	 id:100
	 id:3d00290a-1af0-4c28-853e-29fbf96a2722
	 id:4028827c4fb6202a014fb6209c730000
	 id:4028827c4fb633bd014fb64467470000

	 如果是String struts2默认逗号分装 10,1,2,3
	 如果是Integer,Double,Date struts2默认保留最后一个

	 采用数组Integer[] id;
	 */
	//删除
	public String delete(){
		String ids[] = model.getId().split(",");
		//调用业务方法,实现批量删除
		deptService.delete(Dept.class,ids);
		return "alist";
	}

}
