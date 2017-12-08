package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Module;
import cn.syl.domain.Module;
import cn.syl.service.ModuleService;
import cn.syl.service.ModuleService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ModuleServiceImpl implements ModuleService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Module get(Class<Module> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Module entity) {
        if (UtilFuns.isEmpty(entity.getId())) {

        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Module> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Module> entityClass, Serializable id) {
        System.err.println("正在删除"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<Module> entityClass, Serializable[] ids) {
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
