package com.syl.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by ainsc on 2017/8/5.
 */
public class JedisTest {

    @Test
    public void testJedis1(){
        //创建和redis的连接
        Jedis jedis = new Jedis("192.168.142.133",6379);

        jedis.set("key2","2");
        System.out.println(jedis.get("key2"));

        jedis.close();

    }

    @Test
    public void testJedsPool (){
        //创建连接池
        JedisPool pool = new JedisPool("192.168.142.133",6379);
        //获取连接
        Jedis jedis = pool.getResource();
        jedis.set("key3","aaa");
        System.out.println(jedis.get("key3"));
        jedis.close();

    }
}
