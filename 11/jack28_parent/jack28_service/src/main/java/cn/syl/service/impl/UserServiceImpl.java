package cn.syl.service.impl;

import cn.syl.dao.BaseDao;
import cn.syl.domain.User;
import cn.syl.service.UserService;
import cn.syl.service.UserService;
import cn.syl.utils.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    private SimpleMailMessage mailMessage;
    private JavaMailSender mailSender;

    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public List<User> find(String hql, Class<User> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public User get(Class<User> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(final User entity) {
        if (UtilFuns.isEmpty(entity.getId())) {//新增没有用户id手动生成
            String id = UUID.randomUUID().toString();
            entity.setId(id);
            entity.getUserInfo().setId(id);

            //设置默认密码
            entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));
            baseDao.saveOrUpdate(entity);
        //发送邮件
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.err.println("email"+entity.getUserInfo().getEmail());
//                        MailUtils.sendMessage(entity.getUserInfo().getEmail(),"新员工入职","欢迎加入本公司.您的用户名:"+entity.getUserName()+",初始密码:"+SysConstant.DEFAULT_PASS);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//               }
//            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mailMessage.setTo(entity.getUserInfo().getEmail());
                        mailMessage.setSubject("入职通知");
                        mailMessage.setText("新员工入职 欢迎加入本公司.您的用户名:"+entity.getUserName()+",初始密码:"+SysConstant.DEFAULT_PASS);
                        mailSender.send(mailMessage);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();
        } else {
            //修改
            baseDao.saveOrUpdate(entity);
        }
    }

    @Override
    public void saveOrUpdateAll(Collection<User> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<User> entityClass, Serializable id) {
        System.err.println("正在珊瑚" + id + "....");
        try {

            baseDao.deleteById(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Class<User> entityClass, Serializable[] ids) {
//        int i=0;
//        for (Serializable id : ids) {
//            i++;
//            System.err.println("第"+(i)+"删除id"+id);
//            this.deleteById(entityClass, id);
//            System.err.println("删除id"+id+"成功");
//        }
        for (Serializable id : ids) {
            baseDao.deleteById(entityClass, id.toString().trim());
        }
    }

}
