package com.li.sp01.service;

import com.li.sp01.pojo.Order;

public interface OrderService {
    //获取订单信息
    Order getOrder(String orderId);
    //添加订单信息
    void addOrder(Order order);
}
