package com.jt.service;

import com.jt.pojo.Cart;
import com.jt.pojo.Order;

import java.util.List;

public interface DubboOrderService {
    String saveOrder(Order order);

    Order findOrderByOrderId(String orderId);
}
