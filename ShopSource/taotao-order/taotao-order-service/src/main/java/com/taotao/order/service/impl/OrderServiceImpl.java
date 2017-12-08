package com.taotao.order.service.impl;

import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单处理
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN}")
    private String ORDER_ID_GEN;

    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        //生成订单号使用redis incr
        //不存在设置初始值
        if (!jedisClient.exists(ORDER_ID_GEN)) {
            jedisClient.set(ORDER_ID_GEN,ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN).toString();//订单id
        //向订单表插入数据,不全pojo
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");//免邮费
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderMapper.insert(orderInfo);


        //向订单明细表插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem:orderItems){
            String orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            tbOrderItem.setId(orderItemId);//明细表主键
            tbOrderItem.setOrderId(orderId);//外键,订单id
            orderItemMapper.insert(tbOrderItem);
        }
        //向物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);//订单id
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        //返回订单号

        return TaotaoResult.ok(orderId);
    }
}
