package com.taotao.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelper {


    @Test
    public void testPageHelper(){
        //mybatis xml设置分页插件
        //设置分页条件
        //查询
        //取分页信息,使用pageinfo对象

        PageHelper.startPage(1,10);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper= applicationContext.getBean(TbItemMapper.class);

        TbItemExample exp = new TbItemExample();
       // TbItemExample.Criteria criteria = exp.createCriteria();
       // criteria.a
        List<TbItem> list = itemMapper.selectByExample(exp);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总记页数：" + pageInfo.getPages());
        System.out.println("返回的记录数：" + list.size());

    }
}
