package com.taotao.jedis;





import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class TestJedis{



    public void testJedis1(){
     //   jedis对象,ip和端口号
        Jedis jedis = new Jedis("192.168.123.175",6379);
        jedis.set("j-key","1234");
        String result = jedis.get("j-key");
        System.out.println(result);

        jedis.close();
    }


    public void testJestPool(){
       //数据库连接池对象   //单列
        //从连接池获取连接
        //jesdis操作数据库(方法级别)
        //一定要关闭jesids连接

        JedisPool jedisPool = new JedisPool("192.168.123.175",6379);
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("j-key");
        System.out.println("result"+result);
        //一定要关闭
        jedis.close();
        //系统关闭前关闭连接池

    }

    public void testJedisCluster(){
        //创建jedisCluster对象,构造参数Set类型,集合每个元素是HostAndPort类型
        //使用JedisCluster操作redis,自带连接池
        //系统关闭前关闭cluster
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.123.175",7001));
        nodes.add(new HostAndPort("192.168.123.175",7002));
        nodes.add(new HostAndPort("192.168.123.175",7003));
        nodes.add(new HostAndPort("192.168.123.175",7004));
        nodes.add(new HostAndPort("192.168.123.175",7005));
        nodes.add(new HostAndPort("192.168.123.175",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("cluster-test","hello jedis cluster");
        String result = jedisCluster.get("cluster-test");
        System.out.println(result);
        //系统关闭前关闭jedisCulster
        jedisCluster.close();
    }
}
