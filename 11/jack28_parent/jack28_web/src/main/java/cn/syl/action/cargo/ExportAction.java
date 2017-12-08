package cn.syl.action.cargo;


import cn.itcast.export.webservice.EpService;
import cn.itcast.export.webservice.Exception_Exception;
import cn.syl.action.BaseAction;
import cn.syl.domain.Contract;
import cn.syl.domain.Export;
import cn.syl.domain.ExportProduct;
import cn.syl.domain.User;
import cn.syl.service.ContractService;
import cn.syl.service.ExportProductService;
import cn.syl.service.ExportService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ainsc
 */
public class ExportAction extends BaseAction implements ModelDriven<Export> {

    private Export model = new Export();


    @Override
    public Export getModel() {
        return model;
    }

    //注入service
    private ExportService exportService;
    private ContractService contractService;
    private ExportProductService exportProductService;

    public void setExportProductService(ExportProductService exportProductService) {
        this.exportProductService = exportProductService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
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
        String hql = "from Export where 1=1";
//        User user = super.getCurUser();
//        int degree =user.getUserInfo().getDegree();
//        if (degree==4){
//            //员工
//            hql+="and createBy='"+user.getId()+"'";
//        }else if (degree==3){
//            //说明是部门经理
//            hql+="and createDept='"+user.getDept().getId()+"'";
//        } else if (degree==2){
//            //管理本部门以及下属的
//        }else if (degree==1){
//            //副总
//        }else if (degree==0){
//            //总经理
//        }
        page = exportService.findPage(hql, page, Export.class, null);
        //分页条url地址
        page.setUrl("exportAction_list");
        //将page压入栈顶
        super.push(page);

        return "list";

    }

    /*
    查状态为1的购销活动
     */
    public String contractList() {
        String hql = "from Contract where state=1";
        page = contractService.findPage(hql, page, Contract.class, null);
        page.setUrl("exportAction_contractList");
        super.push(page);
        return "contractList";

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
        Export dept = exportService.get(Export.class, model.getId());
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

    /**
     * <!-- 保存的是购销合同的id,用逗号空格进行分隔 -->
     * <input type="hidden" name="id" --->contractIds
     * value="4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024" />
     * model 放入的id不是 Export id
     * contractIds
     *
     * @return
     * @throws Exception
     */
    public String insert() throws Exception {
        //加入细粒度权限控制数据
        User user = super.getCurUser();
        model.setCreateBy(user.getId());
        model.setCreateDept(user.getDept().getId());

        exportService.saveOrUpdate(model);
        // return "alist";
        return contractList();
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    public String toupdate() {
        //根据id查找
        Export export = exportService.get(Export.class, model.getId());
        super.push(export); //放入值栈

        //addTRRecord("mRecordTable", "id", "productNo", "cnumber", "grossWeight", "netWeight", "sizeLength", "sizeWidth", "sizeHeight", "exPrice", "tax") ;
        StringBuilder sb = new StringBuilder();
        Set<ExportProduct> epSet = export.getExportProducts();//关联级别的数据查询
        //遍历 报运中的货物
        for (ExportProduct ep : epSet) {
            sb.append("addTRRecord(\"mRecordTable\", '");
            sb.append(ep.getId()).append("', '");
            sb.append(ep.getProductNo()).append("', '");
            sb.append(ep.getCnumber()).append("', '");
            sb.append(UtilFuns.convertNull(ep.getGrossWeight())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getNetWeight())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getSizeLength())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getSizeWidth())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getSizeHeight())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getExPrice())).append("', '");
            sb.append(UtilFuns.convertNull(ep.getTax())).append("');");
        }
        super.put("mRecordData", sb.toString());

        //页面跳转
        return "toupdate";
    }

    /**
     * 执行更新操作
     */
    private String[] mr_id;
    private String[] mr_changed;//当前行是否有改变
    private Integer[] mr_cnumber;
    private Double[] mr_grossWeight;
    private Double[] mr_netWeight;
    private Double[] mr_sizeLength;
    private Double[] mr_sizeWidth;
    private Double[] mr_sizeHeight;
    private Double[] mr_exPrice;
    private Double[] mr_tax;

    public String update() {
        super.isValidatePost();
        //exportService.saveOrUpdate(model);  //警告会擦出其他值!
        //先查询再修改
        Export export = exportService.get(Export.class, model.getId());
        //报运信息
        export.setInputDate(model.getInputDate());
        export.setLcno(model.getLcno());//信用正好
        export.setConsignee(model.getConsignee());//收货地址
        export.setShipmentPort(model.getShipmentPort());
        export.setDestinationPort(model.getDestinationPort());
        export.setTransportMode(model.getTransportMode());
        export.setPriceCondition(model.getPriceCondition());
        export.setMarks(model.getMarks()); //唛头
        export.setRemark(model.getRemark());

        System.err.println("mr_id[]" + Arrays.toString(mr_id));
        //报运单下的货物
        Set<ExportProduct> epSet = new HashSet<>();
        for (int i = 0; i < mr_id.length; i++) {
            ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i].trim());
            epSet.add(ep);
            System.err.println("mr_cnumber[]" + Arrays.toString(mr_cnumber));

            if ("1".equals(mr_changed[i].trim())) {//货物被修改了
                ep.setCnumber(mr_cnumber[i]);
                ep.setGrossWeight(mr_grossWeight[i]);
                ep.setNetWeight(mr_netWeight[i]);
                ep.setSizeLength(mr_sizeLength[i]);
                ep.setSizeWidth(mr_sizeWidth[i]);
                ep.setSizeHeight(mr_sizeHeight[i]);
                ep.setExPrice(mr_exPrice[i]);
                ep.setTax(mr_tax[i]);

                //没有修改也要放入集合 否则原有数据会减少 警告!!
                //epSet.add(ep);
            }
            //  ep.setExport(export); //货物 关联报运单(可省略 应为外键已经存在?)

        }
        export.setExportProducts(epSet); //报运单关联商品 相当于export.getExportProducts().add()

        //Export :
//        <set name="exportProducts" cascade="all" inverse="true" order-by="ORDER_NO">
//			<key column="EXPORT_ID"/>
//			<one-to-many class="ExportProduct"/>
//		</set>
        // ExportProduct:
        //<many-to-one name="export" class="Export" column="EXPORT_ID"/>

        exportService.saveOrUpdate(export); //保存报运 级联商品 设置cascade在报运 外键由ExportProduct维护(如果是新增需设置exportproduct与export外键关系)

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
        exportService.delete(Export.class, ids);
        System.err.println("跳转页面...");
        return "alist";
    }

    public String submit() {
        String ids[] = model.getId().split(",");
        //遍历ids加载每个购销合同对象, 再修改购销合同的状态
        exportService.changeSate(ids, 1);
        return "alist";
    }

    public String cancel() {
        String ids[] = model.getId().split(",");
        //遍历ids加载每个购销合同对象, 再修改购销合同的状态
        exportService.changeSate(ids, 0);
        return "alist";
    }

    /**
     * 电子报运
     *
     * @param
     */
    private EpService epService;

    public void setEpService(EpService epService) {
        this.epService = epService;
    }

    public String export() throws Exception_Exception {
        //1.确定出选择的报运单对象
        // 2.报运到和商品转成json
        //3.调用远程的webservice服务 传递
        //4.响应处理json
        //5.再次查询
        Export export = exportService.get(Export.class, model.getId());
        String inputJson = JSON.toJSONString(export);
        System.out.println(inputJson);
        String resultJson = epService.exportE(inputJson);
        System.out.println(resultJson);
        Export resultExport = JSON.parseObject(resultJson, Export.class); //值不全会数据丢失

        exportService.updateE(resultExport);
        return "alist";


    }

    public void setMr_id(String[] mr_id) {
        this.mr_id = mr_id;
    }

    public void setMr_changed(String[] mr_changed) {
        this.mr_changed = mr_changed;
    }

    public void setMr_cnumber(Integer[] mr_cnumber) {
        this.mr_cnumber = mr_cnumber;
    }

    public void setMr_grossWeight(Double[] mr_grossWeight) {
        this.mr_grossWeight = mr_grossWeight;
    }

    public void setMr_netWeight(Double[] mr_netWeight) {
        this.mr_netWeight = mr_netWeight;
    }

    public void setMr_sizeLength(Double[] mr_sizeLength) {
        this.mr_sizeLength = mr_sizeLength;
    }

    public void setMr_sizeWidth(Double[] mr_sizeWidth) {
        this.mr_sizeWidth = mr_sizeWidth;
    }

    public void setMr_sizeHeight(Double[] mr_sizeHeight) {
        this.mr_sizeHeight = mr_sizeHeight;
    }

    public void setMr_exPrice(Double[] mr_exPrice) {
        this.mr_exPrice = mr_exPrice;
    }

    public void setMr_tax(Double[] mr_tax) {
        this.mr_tax = mr_tax;
    }
}
