package cn.syl.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.syl.dao.BaseDao;
import cn.syl.domain.Dept;
import cn.syl.service.DeptService;
import cn.syl.utils.Page;
import cn.syl.utils.UtilFuns;

public class DeptServiceImpl implements DeptService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Dept get(Class<Dept> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(Dept entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            entity.setState(1); //启用 0停用
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Dept> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Dept> entityClass, Serializable id) {
        String hql = "from Dept where parent.id =?";
        List<Dept> list = baseDao.find(hql, Dept.class, new Object[]{id});//查询这个id有没有子部门

        //递归删除:
        for (Dept dept : list) { //遍历子部门,删除他们
            deleteById(Dept.class, dept.getId()); //如果子部门下还有子部门
        }

        try {
            baseDao.deleteById(entityClass, id); //删除父亲,如果父亲递归删除了儿子,再次删除会报错. 但不影响在这里捕获异常
        } catch (Exception e) {
            e.printStackTrace();
        }

//非递归写法:
//        if (list!=null&&list.size()>0){
//            for (Dept dept : list) {
//                //  deleteById(Dept.class, dept.getId()); //如果子部门下还有子部门
//                baseDao.deleteById(entityClass, dept.getId());
//            }
//        }else {
//            //没有儿子 直接删除?
//            baseDao.deleteById(entityClass,id);
//        }
    }

    @Override
    public void delete(Class<Dept> entityClass, Serializable[] ids) {

        for (Serializable id : ids) {
            this.deleteById(entityClass, id);
        }
    }

}
