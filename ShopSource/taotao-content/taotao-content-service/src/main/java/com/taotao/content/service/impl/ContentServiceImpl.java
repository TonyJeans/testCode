package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'contentServiceImpl' is defined
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //插入到内容表
        contentMapper.insert(tbContent);

        //如果新增的内容,删除缓存中的信息,让下次查询查询数据库
        try {
            jedisClient.hdel(INDEX_CONTENT,tbContent.getCategoryId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(Long id, Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample ex = new TbContentExample();
        TbContentExample.Criteria criteria = ex.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        List<TbContent> list = contentMapper.selectByExample(ex);
        //取分页查询结果
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());

        return result;
    }

    @Override
    public TaotaoResult deleteContent(String[] ids) {

        for (String id : ids) {
            contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //先查询缓存
        //添加缓存不能影响正常业务逻辑

        //查询结构,把json转成list
        try {
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            if (StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //缓存没有,查询数据库
        TbContentExample ex = new TbContentExample();
        TbContentExample.Criteria criteria = ex.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(ex);
        //把查询结果添加到缓存
        try {
            jedisClient.hset(INDEX_CONTENT,cid+"", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //再返回结果
        return list;
    }
}
