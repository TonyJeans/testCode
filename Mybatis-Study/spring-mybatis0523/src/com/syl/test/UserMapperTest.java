package com.syl.test;

import com.syl.mapper.UserMapper;
import com.syl.pojo.User;
import com.syl.pojo.UserExample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by ainsc on 2017/7/28.
 */
public class UserMapperTest {

    private ApplicationContext applicationContext;

    @Before
    public  void setUp(){
        String configLacation = "ApplicationContext.xml";
        applicationContext = new ClassPathXmlApplicationContext(configLacation);
    }

//    @Test
//  public  void testFindUerById(){
//      UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
//      User id = userMapper.findUserById(1);
//      System.out.println(id);
//  }

    @Test
  public  void testFindUerById(){
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
       User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }

    @Test
    public void findUserAndSex(){
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameLike("%王%");
        criteria.andSexEqualTo("1");

        List<User> list = userMapper.selectByExample(userExample);
        System.out.println(list);

    }
}
