package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.*;
import cn.syl.service.ExportService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.*;

public class ExportServiceImpl implements ExportService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Export get(Class<Export> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Export entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            //新增报运单
            entity.setState(0);//状态
            System.err.println("entity.getContractIds()"+entity.getContractIds());
            String ids[] = entity.getContractIds().replace(" ","").split(",");
            System.err.println(Arrays.toString(ids));
            StringBuilder sb = new StringBuilder();
            //遍历每个购销活动的id,得到每个购销合同,并修改状态为2
            for (String id : ids) {
                Contract contract = baseDao.get(Contract.class, id.trim());
                contract.setState(2);//修改状态
                baseDao.saveOrUpdate(contract);
                sb.append(contract.getContractNo()).append(" ");
            }
            entity.setCustomerContract(sb.toString()); //设置合同号码以及确认书号
            entity.setContractIds(UtilFuns.joinStr(ids,","));
            entity.setInputDate(new Date());//制单日期
            //通过购销合同的集合,跳远查询购销合同的列表  in ('12123','23425')
            //from ContractProduct contract.id in ('4028817a33812ffd013382048ff80024','4028817a33d4f8b40133d9878e88000d')
           // String hql = "from ContractProduct contract.id in ("+UtilFuns.joinInStr(ids)+")";
            String hql = "from ContractProduct where contract.id in ("+UtilFuns.joinInStr(ids)+")";
            List<ContractProduct> list = baseDao.find(hql, ContractProduct.class, null);
            //数据搬家 合同下的货物--->报运商品明细
            Set<ExportProduct> epSet = new HashSet<ExportProduct>();
            for (ContractProduct cp : list) {
                ExportProduct ep = new ExportProduct();
                ep.setBoxNum(cp.getBoxNum());
                ep.setCnumber(cp.getCnumber());
                ep.setFactory(cp.getFactory());
                ep.setOrderNo(cp.getOrderNo());
                ep.setPackingUnit(cp.getPackingUnit());
                ep.setPrice(cp.getPrice());
                ep.setProductNo(cp.getProductNo());

                ep.setExport(entity); //商品关联报运单 因为Export inverse="true" 外键维护交给了ExportProduct
                //所以设置商品关联报运单是必须的. 否则ExportProduct将找不到外键
                //ExportProduct:<many-to-one name="export" class="Export" column="EXPORT_ID"/>
                //Export---><set name="exportProducts" cascade="all" inverse="true" order-by="ORDER_NO">

                epSet.add(ep); //可以用 entity.getExportProducts().add() 代替

                //加载购销合同下当前货物的附件
                Set<ExtCproduct> extCSet = cp.getExtCproducts();
                Set<ExtEproduct> extESet = new HashSet<ExtEproduct>();//报运单下的附件列表

                for (ExtCproduct extC : extCSet) {

                    ExtEproduct extE = new ExtEproduct();//报运商品附件
                    BeanUtils.copyProperties(extC,extE); //源,目标
                    extE.setId(null);  //uuid生成
                    extE.setExportProduct(ep);
                    //ExportProduct商品:<set name="extEproducts" cascade="all" inverse="true">  //关联保存的是商品 商品有数据 商品关联了附件且设置了级联. 所有附件也有数据
                    //ExtEproduct 附件:<many-to-one name="exportProduct" class="ExportProduct" column="EXPORT_PRODUCT_ID"/>
                    extESet.add(extE);//Set中添加元素
                   // ep.getExtEproducts().add(extE);//add 可用ep.setExtEproducts(extESet);
                    System.out.println("");
                }
             //商品关联附件
                ep.setExtEproducts(extESet); //ep.getExtEproducts().add()

            }//for end

            //设置一个报运单下 有多个商品
            entity.setExportProducts(epSet);  // entity.getExportProducts().add()

        }

        baseDao.saveOrUpdate(entity);  //保存的是Export   entity.setExportProducts(epSet);  // entity.getExportProducts().add()
                                        //Export 关联了ExportProducts并且设置了级联 所以ExportProducts也能保存.
    }

    @Override
    public void saveOrUpdateAll(Collection<Export> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Export> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<Export> entityClass, Serializable[] ids) {
//        int i=0;
//        for (Serializable id : ids) {
//            i++;
//            System.err.println("第"+(i)+"删除id"+id);
//            this.deleteById(entityClass, id);
//            System.err.println("删除id"+id+"成功");
//        }
        for (Serializable id :ids){
            baseDao.deleteById(entityClass,id.toString().trim());
        }
    }

    @Override
    public void changeSate(String[] ids,Integer state) {
        for (String id : ids) {
            Export contract = baseDao.get(Export.class, id);
            contract.setState(state);
            baseDao.saveOrUpdate(contract);//可以省略

        }

    }

    @Override
    public void updateE(Export resultExport) {
        //1.从数据库加载报运单对象
        //2.修改报运属性
        //3.加载报运单下的每个商品
        //4.保存报运单下的商品
        ///5.保存 报运单
        Export export = baseDao.get(Export.class, resultExport.getId());
        export.setState(resultExport.getState());
        export.setRemark(resultExport.getRemark());

        Set<ExportProduct> resultepSet = resultExport.getExportProducts();//返回的税金
        for (ExportProduct ep : resultepSet) {
            ExportProduct epp = baseDao.get(ExportProduct.class, ep.getId());
            epp.setTax(ep.getTax());
            baseDao.saveOrUpdate(epp);//可省略

        }

        baseDao.saveOrUpdate(export);


    }

}
