package cn.syl.action.sysadmin;

import cn.syl.action.BaseAction;
import cn.syl.domain.Dept;
import cn.syl.domain.Module;
import cn.syl.domain.Role;
import cn.syl.domain.User;
import cn.syl.service.DeptService;
import cn.syl.service.ModuleService;
import cn.syl.service.RoleService;
import cn.syl.service.UserService;
import cn.syl.utils.Page;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 部门管理
 *
 * @author ainsc
 */
public class UserAction extends BaseAction implements ModelDriven<User> {

    private User model = new User();

    @Override
    public User getModel() {
        return model;
    }

    //注入service
    private UserService userService;
    private DeptService deptService;
    private RoleService roleService;
    private ModuleService moduleService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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
        page = userService.findPage("from User", page, User.class, null);
        //分页条url地址
        page.setUrl("userAction_list");
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
        User dept = userService.get(User.class, model.getId());
        super.push(dept);
        return "toview";
    }

    /**
     * 进入新增页面  下拉列表
     *
     * @return
     * @throws Exception
     */
    public String tocreate() throws Exception {
        List<Dept> list = deptService.find("from Dept where state=1", Dept.class, null);
        super.put("deptList", list);//部门列表
        List<User> userList = userService.find("from User where state=1", User.class, null);
        super.put("userList", userList);//User列表
        return "tocreate";
    }
    /*
	Request URL:http://localhost:8080/jack28/sysadmin/deptAction_insert
		parent.id:4028827c4fb645b0014fb64668550000
		deptName:装箱部门
	 */
    //Request URL:http://localhost:8080/jack28/sysadmin/userAction_insert

    /**
     * dept.id:aeb1c7d3-9a54-4f73-b0ec-0325b83aef45
     * userName:sunkes
     * state:1
     * userInfo.name:孙先
     * userInfo.manager.id:
     * userInfo.joinDate:2017-09-13
     * userInfo.salary:1500
     * userInfo.degree:1
     * userInfo.gender:1
     * userInfo.station:工程师
     * userInfo.telephone:153788888
     * userInfo.email:11515@qq.com
     * userInfo.birthday:1988-10-12
     * userInfo.orderNo:1
     * userInfo.remark:试用期
     */
    public String insert() throws Exception {
        userService.saveOrUpdate(model);
        return "alist";
    }

    /**
     * 进入修改页面
     * http://localhost:8080/jack28/sysadmin/userAction_toupdate
     * page.pageNo:3
     * id:4230cf48-0652-485b-9815-5254a78d3a81
     */
    public String toupdate() {
        //根据id查找
        User dept = userService.get(User.class, model.getId());
        super.push(dept); //放入值栈

        //查询父亲部门
        List<Dept> list = deptService.find("from Dept where state=1", Dept.class, null);
        super.put("deptList", list);

        //页面跳转
        return "toupdate";
    }

    /**
     * 执行更新操作
     * http://localhost:8080/jack28/sysadmin/userAction_update
     * <p>
     * id:4230cf48-0652-485b-9815-5254a78d3a81
     * <p>
     * dept.id:aeb1c7d3-9a54-4f73-b0ec-0325b83aef45
     * userName:new login name
     * state:1
     *
     * @return
     */
    public String update() {
        super.isValidatePost();
        //userService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        User user = userService.get(User.class, model.getId());

        user.setDept(model.getDept());
        user.setUserName(model.getUserName());
        user.setState(model.getState());

        userService.saveOrUpdate(user);

        return "alist";
    }

    // *Request URL:http://localhost:8080/jack28/sysadmin/userAction_delete

    /**
     * page.pageNo:3
     * id:4230cf48-0652-485b-9815-5254a78d3a81
     * id:3d80dc08-916d-4eff-98af-4897143018dc
     */
    public String delete() {
        //ids[12aa65aa-75a2-47b0-9fbf-6054311b85e8,  23199779-1e50-4d00-9e89-e4b01de26846,  700473d7-04d2-408c-beb0-5640532177f7]
        System.out.println("id没有切分前" + model.getId());
        String ids[] = model.getId().split(",");
        //调用业务方法,实现批量删除
        System.err.println("ids" + Arrays.toString(ids));
        userService.delete(User.class, ids);
        System.err.println("跳转页面...");
        return "alist";
    }

    public String torole() {
        User user = userService.get(User.class,model.getId());
        List<Role> roleList = roleService.find("from Role", Role.class, null);
        super.push(user);
        super.put("roleList",roleList);

        //得到当前对象的角色.
        Set<Role> roleSet = user.getRoles();
        StringBuffer sb = new StringBuffer();
        for (Role role : roleSet) {
            sb.append(role.getName()).append(",");//管理员,船运经理,
        }

        super.put("roleStr",sb.toString());
        return "torole";
    }

    /**
     * 修改角色
     * <input type="hidden" name="id" value="50ea48b7-2bdf-499c-af01-5766fc669702"/>
       <input type="checkbox" name="roleIds" value="4028a1c34ec2e5c8014ec2ebf8430001" class="input">
     * @return
     */
    private String [] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String role(){
        System.err.println("String [] roleIds"+roleIds.toString());
        User user = userService.get(User.class, model.getId());
        //用户有哪些权限
        Set<Role> roles = new HashSet<Role>();
        for (String id : roleIds) {//遍历页面上面的复选框id
            Role role = roleService.get(Role.class, id);
            roles.add(role);//向角色列表中添加角色
        }
        //设置用户与角色列表的关系
        user.setRoles(roles);
        userService.saveOrUpdate(user);//影响的是中间表

        return "alist";
    }


}
