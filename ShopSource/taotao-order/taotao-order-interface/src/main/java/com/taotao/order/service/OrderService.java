package com.taotao.order.service;

import com.taotao.order.pojo.OrderInfo;
import com.taotaoo.common.utils.TaotaoResult;

public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
