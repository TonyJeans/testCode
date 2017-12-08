package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Contract;
import cn.syl.domain.ExtCproduct;
import cn.syl.service.ExtCproductService;
import cn.syl.service.ExtCproductService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ExtCproductServiceImpl implements ExtCproductService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(ExtCproduct entity) {
        double amount = 0d;
        //新增
        if (UtilFuns.isEmpty(entity.getId())) { //合同号id由hibernate 自动生成
            if (UtilFuns.isNotEmpty(entity.getPrice())&&UtilFuns.isNotEmpty(entity.getCnumber())){
                amount = entity.getPrice()*entity.getCnumber();//货物总金额
                entity.setAmount(amount);
            }
            //修改购销合同的总金额
            Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据报销合同id得到购销合同的对象
            contract.setTotalAmount(contract.getTotalAmount()+amount);
            //保存
            baseDao.saveOrUpdate(contract);
        }else {//修改
            double oldAmount  = entity.getAmount();
            if (UtilFuns.isNotEmpty(entity.getPrice())&&UtilFuns.isNotEmpty(entity.getCnumber())){
                amount = entity.getPrice()*entity.getCnumber();//货物总金额
                entity.setAmount(amount);
            }
            Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据报销合同id得到购销合同的对象
            contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
        }

        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
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
    public void delete(Class<ExtCproduct> entityClass, ExtCproduct model) {
        ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());
        Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());
        contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
        baseDao.saveOrUpdate(contract);

        //删除附件
        baseDao.deleteById(ExtCproduct.class,model.getId());
    }

}
