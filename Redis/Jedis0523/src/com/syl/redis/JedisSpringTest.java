package com.syl.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by ainsc on 2017/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicationContext.xml")
public class JedisSpringTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testJedisSpring(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("key4","bbb");
        System.out.println(jedis.get("key4"));
    }
}
