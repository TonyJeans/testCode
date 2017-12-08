package cn.syl.action.sysadmin;

import cn.syl.action.BaseAction;
import cn.syl.domain.Module;
import cn.syl.service.ModuleService;
import cn.syl.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Arrays;

/**
 *
 *
 * @author ainsc
 */
public class ModuleAction extends BaseAction implements ModelDriven<Module> {

    private Module model = new Module();


    @Override
    public Module getModel() {
        return model;
    }

    //注入service
    private ModuleService moduleService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
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
        page = moduleService.findPage("from Module", page, Module.class, null);
        //分页条url地址
        page.setUrl("moduleAction_list");
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
        Module dept = moduleService.get(Module.class, model.getId());
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
        moduleService.saveOrUpdate(model);
        return "alist";
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        Module role = moduleService.get(Module.class, model.getId());
        super.push(role); //放入值栈


        //页面跳转
        return "toupdate";
    }

    /**
     * 执行更新操作
     *Request URL:http://localhost:8080/jack28/sysadmin/moduleAction_update
     id:402899815e60e4e1015e60e6cfa60000
     name:sssss
     layerNum:10
     cpermission:sss
     curl:aaa
     ctype:4
     state:1
     belong:sss
     cwhich:12
     remark:ss
     orderNo:35
     */
    public String update() {
        super.isValidatePost();
        //moduleService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        Module module = moduleService.get(Module.class, model.getId());

        module.setName(model.getName());
        module.setLayerNum(model.getLayerNum());
        module.setCpermission(model.getCpermission());
        module.setCurl(model.getCurl());
        module.setCtype(model.getCtype());
        module.setState(model.getState());
        module.setBelong(model.getBelong());
        module.setCwhich(model.getCwhich());
        module.setRemark(model.getRemark());
        module.setOrderNo(model.getOrderNo());

        moduleService.saveOrUpdate(module);

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
        moduleService.delete(Module.class, ids);
        System.err.println("跳转页面...");
        return "alist";
    }

}
