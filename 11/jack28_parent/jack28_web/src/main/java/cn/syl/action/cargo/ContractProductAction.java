package cn.syl.action.cargo;

import cn.syl.action.BaseAction;
import cn.syl.domain.ContractProduct;
import cn.syl.domain.Factory;
import cn.syl.service.ContractProductService;
import cn.syl.service.FactoryService;
import cn.syl.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Arrays;
import java.util.List;

/**
 * @author ainsc
 */
public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {

    private ContractProduct model = new ContractProduct();


    @Override
    public ContractProduct getModel() {
        return model;
    }

    //注入service
    private ContractProductService contractProductService;
    private FactoryService factoryService;

    public void setFactoryService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public void setContractProductService(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }


    //分页组件
    private Page page = new Page();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }



    /**
     * 进入新增页面
     * http://localhost:8080/jack28/cargo/contractProductAction_tocreate
     * ?contract.id=402899815e6f1e8b015e6f211f550000
     *
     * @return
     * @throws Exception
     */
    public String tocreate() throws Exception {
        //查询生产货物的厂家,需要的参数(生产厂家)

        String hql = "from Factory f where ctype='货物' and f.state=1";
        List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
        super.put("factoryList", factoryList);

        //当前合同下的的货物列表
        hql = "from ContractProduct where contract.id=?";
        contractProductService.
                findPage(hql, page, ContractProduct.class,
                        new String[]{model.getContract().getId()});

        //设置page url
        page.setUrl("contractProductAction_tocreate");
        super.push(page);
        return "tocreate";
    }
//http://localhost:8080/jack28/cargo/contractProductAction_insert

    /**
     contract.id:402899815e6f1e8b015e6f211f550000  //外键购销合同id
     factory.id:10  //外键 厂家id
     factoryName:晶晨
     productNo:0002
     productImage:01.png
     [cnumber:20]
     packingUnit:PCS
     loadingRate:1/5
     boxNum:4
    [ price:1]
     orderNo:1
     productDesc:描述
     productRequest:要求
     page.pageNo:1
     *
     */
    public String insert() throws Exception {
        //合同下货物id插入时hibernate自动生成
        contractProductService.saveOrUpdate(model); //向合同下的货物列表插入数据
        return tocreate();
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        ContractProduct role = contractProductService.get(ContractProduct.class, model.getId());
        super.push(role); //放入值栈

        //生产厂家列表
        String hql = "from Factory where ctype='货物' and state=1";
        List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
        super.put("factoryList", factoryList);


        //页面跳转
        return "toupdate";
    }

    /**
     * 执行更新操作

     */
    public String update() throws Exception {
        super.isValidatePost();
        //contractProductService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        ContractProduct cp = contractProductService.get(ContractProduct.class, model.getId());

        cp.setFactory(model.getFactory());
        cp.setFactoryName(model.getFactoryName());//冗余字段
        cp.setProductNo(model.getProductNo());
        cp.setProductImage(model.getProductImage());
        cp.setCnumber(model.getCnumber());
        cp.setAmount(model.getAmount());
        cp.setPackingUnit(model.getPackingUnit());
        cp.setLoadingRate(model.getLoadingRate());
        cp.setBoxNum(model.getBoxNum());
        cp.setPrice(model.getPrice());
        cp.setOrderNo(model.getOrderNo());
        cp.setProductDesc(model.getProductDesc());
        cp.setProductRequest(model.getProductRequest());
        contractProductService.saveOrUpdate(cp);

        return tocreate();
    }

    // *Request URL:http://localhost:8080/jack28/sysadmin/userAction_delete

    /**
     http://localhost:8080/jack28/cargo/contractProductAction_
     delete.action?id=402899815e7070cf015e7071fe960000&contract.id=402899815e6f1e8b015e6f211f550000

     对子项操时候,最好同时传入他的所有祖宗
     */
    public String delete() throws  Exception{
        contractProductService.delete(ContractProduct.class,model);
        return tocreate();
    }


}
