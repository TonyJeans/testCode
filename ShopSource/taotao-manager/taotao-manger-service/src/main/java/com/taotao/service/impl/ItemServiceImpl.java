package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotaoo.common.utils.IDUtils;
import com.taotaoo.common.utils.JsonUtils;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ainsc on 2017/8/8.
 * 商品管理
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "itemAddTopic")    //默认根据id,否则根据类型
    private Destination destination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${TIME_EXPIRE}")
    private Integer TIME_EXPIRE;

    @Override
    public TbItem getItemsById(long itemId) {
        //查询数据库之前先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId  + ":BASE");
            if(StringUtils.isNotBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return  tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //缓存没有查询数据库
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

        //结果添加到缓存
        try {
            jedisClient.set(ITEM_INFO + ":" + itemId  + ":BASE", JsonUtils.objectToJson(tbItem));
            //设置商品过期时间,提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId  + ":BASE",TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;

    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //取查询结构
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());

        return result;

    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        //生成商品id
        //不全item
        //商品表插入数据
        //商品描述表数据
        //返回结果
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态:1正常\2下架\3删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        itemMapper.insert(item);

        //创建商品描述Pojo
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        item.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);


        //向activemq发送添加商品的消息,在search中更新索引库
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //发送商品id
                TextMessage message = session.createTextMessage(itemId + "");
                return message;
            }
        });

        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        //查询数据库之前先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId  + ":DESC");
            if(StringUtils.isNotBlank(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return  itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc itemDesc =  itemDescMapper.selectByPrimaryKey(itemId);



        //结果添加到缓存
        try {
            jedisClient.set(ITEM_INFO + ":" + itemId  + ":DESC", JsonUtils.objectToJson(itemDesc));
            //设置商品过期时间,提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId  + ":DESC",TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
