package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Contract;
import cn.syl.service.ContractService;
import cn.syl.service.ContractService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ContractServiceImpl implements ContractService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Contract get(Class<Contract> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Contract entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            //新增
            entity.setTotalAmount(0d);
            entity.setState(0);//0草稿 1已经上报 2已经报运费
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Contract> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Contract> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<Contract> entityClass, Serializable[] ids) {
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
            Contract contract = baseDao.get(Contract.class, id);
            contract.setState(state);
            baseDao.saveOrUpdate(contract);//可以省略

        }

    }

}
