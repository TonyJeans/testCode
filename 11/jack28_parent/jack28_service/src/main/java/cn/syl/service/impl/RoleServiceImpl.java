package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Role;
import cn.syl.service.RoleService;
import cn.syl.service.RoleService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RoleServiceImpl implements RoleService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Role get(Class<Role> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Role entity) {
        if (UtilFuns.isEmpty(entity.getId())) {

        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Role> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Role> entityClass, Serializable id) {
        System.err.println("正在珊瑚"+id+"....");
        try{

         baseDao.deleteById(entityClass,id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<Role> entityClass, Serializable[] ids) {
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
