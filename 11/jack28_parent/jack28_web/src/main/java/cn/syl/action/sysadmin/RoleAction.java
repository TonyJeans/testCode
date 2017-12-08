package cn.syl.action.sysadmin;

import cn.syl.action.BaseAction;
import cn.syl.domain.Dept;
import cn.syl.domain.Module;
import cn.syl.domain.Role;
import cn.syl.service.DeptService;
import cn.syl.service.ModuleService;
import cn.syl.service.RoleService;
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
public class RoleAction extends BaseAction implements ModelDriven<Role> {

    private Role model = new Role();
    private ModuleService moduleService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Override
    public Role getModel() {
        return model;
    }

    //注入service
    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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
        page = roleService.findPage("from Role", page, Role.class, null);
        //分页条url地址
        page.setUrl("roleAction_list");
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
        Role dept = roleService.get(Role.class, model.getId());
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

        return "tocreate";
    }

    public String insert() throws Exception {
        roleService.saveOrUpdate(model);
        return "alist";
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        Role role = roleService.get(Role.class, model.getId());
        super.push(role); //放入值栈


        //页面跳转
        return "toupdate";
    }

    /**
     * 执行更新操作
     * Request URL:http://localhost:8080/jack28/sysadmin/roleAction_update
     * id:4028a1c34ec2e5c8014ec2ebf8430001
     * name:船运专员111
     * remark:船运专员222
     */
    public String update() {
        super.isValidatePost();
        //roleService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        Role role = roleService.get(Role.class, model.getId());

        role.setName(model.getName());
        role.setRemark(model.getRemark());

        roleService.saveOrUpdate(role);

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
        roleService.delete(Role.class, ids);
        System.err.println("跳转页面...");
        return "alist";
    }

    /**
     * 进入模块分块页面
     *
     * @return
     */
    public String tomodule() {
        //更具角色id得到角色对象
        Role role = roleService.get(Role.class, model.getId());
        super.push(role);
        return "tomodule";

    }

    /**
     * ztree数的数据
     * [{id:"模块的id",pid:"父亲id",name:模块名次,checked:true}]
     * json-lib  fastjson struts-json-plus
     */
    public String roleModuleJsonStr() throws IOException {
        //根据角色id得到角色对象
        Role role = roleService.get(Role.class, model.getId());
        //通过对象导航加载模块列表
        Set<Module> moduleSet = role.getModules();
        //加载所有模块 让用胡勾选
        List<Module> moduleList = moduleService.find("from Module", Module.class, null);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = moduleList.size();
        for (Module module : moduleList) {
            size--;
            sb.append("{\"id\":\"").append(module.getId());
            sb.append("\",\"pId\":\"").append(module.getParentId());
            sb.append("\",\"name\":\"").append(module.getName());
            sb.append("\",\"checked\":\"");
            if (moduleSet.contains(module)) {
                sb.append("true");
            } else {
                sb.append("false");
            }
            sb.append("\"}");
            if (size > 0) {
                sb.append(",");
            }
        }
        sb.append("]");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(sb.toString());
        return NONE;
    }

    /**
     * 模块分配 保存
     * <input type="hidden" name="id" value="${id}"/>
     * <input type="hidden" id="moduleIds" name="moduleIds" value="" />
     *
     * @return
     */
    public String module() {
        //1.那个角色
        Role role = roleService.get(Role.class, model.getId());
        //勾选的模块
        String[] ids = moduleIds.split(",");
        //角色分配模块
       Set<Module> moduleSet = new HashSet<Module>();

       if (ids!=null&&ids.length>0){
           for (String id:ids){
               moduleSet.add(moduleService.get(Module.class,id));//添加页面选中的模块
           }
       }
       role.setModules(moduleSet);
       roleService.saveOrUpdate(role);
        return "alist";
    }

    private String moduleIds;

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }
}
