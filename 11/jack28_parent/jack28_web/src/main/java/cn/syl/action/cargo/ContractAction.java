package cn.syl.action.cargo;

import cn.syl.action.BaseAction;
import cn.syl.domain.Contract;
import cn.syl.domain.User;
import cn.syl.service.ContractService;
import cn.syl.utils.Page;
import cn.syl.utils.SysConstant;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.xml.internal.ws.api.policy.PolicyResolver;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 *
 *
 * @author ainsc
 */
public class ContractAction extends BaseAction implements ModelDriven<Contract> {

    private Contract model = new Contract();


    @Override
    public Contract getModel() {
        return model;
    }

    //注入service
    private ContractService contractService;

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
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
        String hql="from Contract where 1=1";
        User user = super.getCurUser();
        int degree =user.getUserInfo().getDegree();
        if (degree==4){
            //员工
            hql+="and createBy='"+user.getId()+"'";
        }else if (degree==3){
            //说明是部门经理
            hql+="and createDept='"+user.getDept().getId()+"'";
        } else if (degree==2){
            //管理本部门以及下属的
        }else if (degree==1){
            //副总
        }else if (degree==0){
            //总经理
        }
        page = contractService.findPage(hql, page, Contract.class, null);
        //分页条url地址
        page.setUrl("contractAction_list");
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
        Contract dept = contractService.get(Contract.class, model.getId());
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
        //加入细粒度权限控制数据
        User user = super.getCurUser();
        model.setCreateBy(user.getId());
        model.setCreateDept(user.getDept().getId());

        contractService.saveOrUpdate(model);
        return "alist";
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        Contract role = contractService.get(Contract.class, model.getId());
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
        //contractService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        Contract contract = contractService.get(Contract.class, model.getId());
        contract.setCustomName(model.getCustomName());
        contract.setPrintStyle(model.getPrintStyle());//打印板式
        contract.setContractNo(model.getContractNo());//合同号码
        contract.setOfferor(model.getOfferor());//收购方
        contract.setInputBy(model.getInputBy());//制单人
        contract.setCheckBy(model.getCheckBy());//审单人
        contract.setInspector(model.getInspector());//验货员
        contract.setSigningDate(model.getSigningDate());//签单日期
        contract.setImportNum(model.getImportNum());//重要程度
        contract.setShipTime(model.getShipTime());//船期
        contract.setTradeTerms(model.getTradeTerms());//贸易条款
        contract.setDeliveryPeriod(model.getDeliveryPeriod());//交货日期
        contract.setCrequest(model.getCrequest());//要求
        contract.setRemark(model.getRemark());//说明
        contractService.saveOrUpdate(contract);

        return "alist";
    }

    /**
     *
     */
    public String print() throws Exception{
        Contract contract = contractService.get(Contract.class,model.getId());
        ContractPrint cp  =new ContractPrint();
        String path = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(path);
        HttpServletResponse response = ServletActionContext.getResponse();
        cp.print(contract,path,response);
        return NONE;
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
        contractService.delete(Contract.class, ids);
        System.err.println("跳转页面...");
        return "alist";
    }

    public String submit(){
        String ids[] = model.getId().split(",");
        //遍历ids加载每个购销合同对象, 再修改购销合同的状态
        contractService.changeSate(ids,1);
        return "alist";
    }

    public String cancel(){
        String ids[] = model.getId().split(",");
        //遍历ids加载每个购销合同对象, 再修改购销合同的状态
        contractService.changeSate(ids,0);
        return "alist";
    }


}
