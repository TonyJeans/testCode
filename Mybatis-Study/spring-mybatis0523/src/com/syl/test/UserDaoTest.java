package com.syl.test;

import com.syl.dao.UserDao;
import com.syl.pojobak.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ainsc on 2017/7/28.
 */
public class UserDaoTest {
    private ApplicationContext applicationContext;

    @Before
    public  void setUp(){
        String configLacation = "ApplicationContext.xml";
        applicationContext = new ClassPathXmlApplicationContext(configLacation);
    }
    @Test
    public  void testFindUserById(){
        //
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        User user = userDao.findUserById(1);
        System.out.println(user);
    }
}
