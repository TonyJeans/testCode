package cn.syl.action.cargo;

import cn.syl.action.BaseAction;
import cn.syl.domain.ExtCproduct;
import cn.syl.domain.Factory;
import cn.syl.service.ExtCproductService;
import cn.syl.service.FactoryService;
import cn.syl.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;

/**
 * @author ainsc
 */
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {

    private ExtCproduct model = new ExtCproduct();


    @Override
    public ExtCproduct getModel() {
        return model;
    }

    //注入service
    private ExtCproductService extCproductService;
    private FactoryService factoryService;

    public void setFactoryService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public void setExtCproductService(ExtCproductService extCproductService) {
        this.extCproductService = extCproductService;
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

     */
    public String tocreate() throws Exception {
        //查询生产附件的厂家,需要的参数(生产厂家)

        String hql = "from Factory where ctype='附件' and state=1";
        List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
        super.put("factoryList", factoryList);

        //当前货物下的附件列表
        hql = "from ExtCproduct where contractProduct.id=?";
        extCproductService.
                findPage(hql, page, ExtCproduct.class,
                        new String[]{model.getContractProduct().getId()});

        //设置page url
        page.setUrl("extCproductAction_tocreate");
        super.push(page);
        return "tocreate";
    }

    /**
     *http://localhost:8080/jack28/cargo/extCproductAction_insert
     *
     contractProduct.contract.id:402899815e6f1e8b015e6f211f550000
     contractProduct.id:402899815e7114df015e71256ecb0000
     factory.id:4028817a3632e86601363344d16d0001
     factoryName:淄博花纸厂
     productNo:00004
     productImage:11
     cnumber:10-----
     packingUnit:PCS
     price:5--------
     orderNo:1
     productDesc:1
     productRequest:1
     page.pageNo:1
     */
    public String insert() throws Exception {
        //合同下货物id插入时hibernate自动生成
        extCproductService.saveOrUpdate(model); //向合同下的货物列表插入数据
        return tocreate();
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        ExtCproduct role = extCproductService.get(ExtCproduct.class, model.getId());
        super.push(role); //放入值栈

        //生产厂家列表
        String hql = "from Factory where ctype='附件' and state=1";
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
        //extCproductService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        ExtCproduct cp = extCproductService.get(ExtCproduct.class, model.getId());

        cp.setFactory(model.getFactory());
        cp.setFactoryName(model.getFactoryName());//冗余字段
        cp.setProductNo(model.getProductNo());
        cp.setProductImage(model.getProductImage());
        cp.setCnumber(model.getCnumber());
        cp.setAmount(model.getAmount());
        cp.setPackingUnit(model.getPackingUnit());
//        cp.setLoadingRate(model.getLoadingRate());
//        cp.setBoxNum(model.getBoxNum());
        cp.setPrice(model.getPrice());
        cp.setOrderNo(model.getOrderNo());
        cp.setProductDesc(model.getProductDesc());
        cp.setProductRequest(model.getProductRequest());
        extCproductService.saveOrUpdate(cp);

        return tocreate();
    }


    // 对子项操时候,最好同时传入他的所有祖宗
    /**
     *http://localhost:8080/jack28/cargo/extCproductAction_delete.action
     * ?id=402899815e73f539015e73fe4eb50002
     * &contractProduct.id=402899815e7114df015e71256ecb0000
     * &contractProduct.contract.id=402899815e6f1e8b015e6f211f550000
     */
    public String delete() throws  Exception{
        extCproductService.delete(ExtCproduct.class,model);
        return tocreate();
    }


}
