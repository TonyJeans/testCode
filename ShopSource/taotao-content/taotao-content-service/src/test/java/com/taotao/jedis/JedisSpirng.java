package com.taotao.jedis;

import com.taotao.jedis.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JedisSpirng {

    @Test
    public void testJediClientPool(){
        //初始化spring容器
        //容器获得jedisSClient对象
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");

        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("jedisClient","myTest");
        String result = jedisClient.get("jedisClient");
        System.out.println("======"+result);
    }
}
