package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.api.R;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

import javax.xml.crypto.Data;

@Service
public class OrderServiceImpl implements DubboOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;

	@Transactional(timeout = 60)
	@Override
	public String saveOrder(Order order) {
		Date date = new Date();
		String orderId = ""+order.getUserId()+System.currentTimeMillis();
		order.setOrderId(orderId).setStatus(1).setCreated(date).setUpdated(date);
		int row = orderMapper.insert(order);
		System.out.println("订单入库成功!!!");
		//2.订单物流入库
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流信息入库成功!!!");
		//3.订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		orderItems.forEach(orderItem -> {
			orderItem.setOrderId(orderId).setCreated(date).setUpdated(date);
			orderItemMapper.insert(orderItem);
		});
		System.out.println("订单商品信息入库成功!!!");
		return orderId;
	}

	/**
	 * 根据订单id查询order 物流等信息
	 * @param orderId
	 * @return
	 */
	@Override
	public Order findOrderByOrderId(String orderId) {
		Order order = orderMapper.selectById(orderId);
		QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
		orderItemQueryWrapper.eq("order_id",orderId);
		List<OrderItem> orderItems = orderItemMapper.selectList(orderItemQueryWrapper);
		OrderShipping orderShipping = orderShippingMapper.selectById(orderId);
		order.setOrderShipping(orderShipping).setOrderItems(orderItems);
		return order;
	}
}
