package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Factory;
import cn.syl.service.FactoryService;
import cn.syl.service.FactoryService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class FactoryServiceImpl implements FactoryService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Factory get(Class<Factory> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Factory entity) {
        if (UtilFuns.isEmpty(entity.getId())) {

        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Factory> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Factory> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<Factory> entityClass, Serializable[] ids) {
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

}
