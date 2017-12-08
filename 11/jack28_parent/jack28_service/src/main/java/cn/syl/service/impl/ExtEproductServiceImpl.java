package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.ExtEproduct;
import cn.syl.service.ExtEproductService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ExtEproductServiceImpl implements ExtEproductService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page, Class<ExtEproduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(ExtEproduct entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            //新增
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ExtEproduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ExtEproduct> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<ExtEproduct> entityClass, Serializable[] ids) {
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
