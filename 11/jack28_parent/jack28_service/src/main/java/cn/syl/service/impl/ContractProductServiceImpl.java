package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Contract;
import cn.syl.domain.ContractProduct;
import cn.syl.domain.ContractProduct;
import cn.syl.domain.ExtCproduct;
import cn.syl.service.ContractProductService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ContractProductServiceImpl implements ContractProductService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(ContractProduct entity) {
        double amount = 0d;
        //新增
        if (UtilFuns.isEmpty(entity.getId())) { //合同号id由hibernate 自动生成
            if (UtilFuns.isNotEmpty(entity.getPrice())&&UtilFuns.isNotEmpty(entity.getCnumber())){
                amount = entity.getPrice()*entity.getCnumber();//货物总金额
                entity.setAmount(amount);
            }
            //修改购销合同的总金额
            Contract contract = baseDao.get(Contract.class, entity.getContract().getId());//根据报销合同id得到购销合同的对象
            contract.setTotalAmount(contract.getTotalAmount()+amount);
            //保存
            baseDao.saveOrUpdate(contract);
        }else {//修改
            double oldAmount  = entity.getAmount();
            if (UtilFuns.isNotEmpty(entity.getPrice())&&UtilFuns.isNotEmpty(entity.getCnumber())){
                amount = entity.getPrice()*entity.getCnumber();//货物总金额
                entity.setAmount(amount);
            }
            Contract contract = baseDao.get(Contract.class, entity.getContract().getId());//根据报销合同id得到购销合同的对象
            contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
        }

        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
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
    public void delete(Class<ContractProduct> entityClass, ContractProduct model) {
        //根据传入删除的id查询合同货物
        ContractProduct contractProduct = baseDao.get(ContractProduct.class, model.getId());
        //加载要删除货物下的附件
        Set<ExtCproduct> extCSet = contractProduct.getExtCproducts();
        //加载购销合同对象
        Contract contract = baseDao.get(Contract.class, model.getContract().getId());
        
        //遍历附件列表 修改购销合同的总金额
        for (ExtCproduct ec : extCSet) {
            contract.setTotalAmount(contract.getTotalAmount()-ec.getAmount());
        }
        //购销合同总金额 -货物总金额
        contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount());

        //更新购销合同总金额
        baseDao.saveOrUpdate(contract);

        //删除货物 级联删除附件
        //<set name="extCproducts" cascade="all" inverse="true">
        baseDao.deleteById(ContractProduct.class,model.getId());

        

    }

}
