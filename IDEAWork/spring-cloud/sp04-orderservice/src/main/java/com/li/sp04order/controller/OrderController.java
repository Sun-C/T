package com.li.sp04order.controller;

import com.li.sp01.pojo.Item;
import com.li.sp01.pojo.Order;
import com.li.sp01.pojo.User;
import com.li.sp01.service.OrderService;
import com.li.sp01.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    //获取订单信息
    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        log.info("获取订单id="+orderId);
        Order order = orderService.getOrder(orderId);
        return JsonResult.ok().msg("获取订单信息").data(order);
    }
    //添加订单
    @GetMapping("/")
    public JsonResult<?> addOrder(){
        Order order = new Order();
        order.setId("2o3h5n52n2l5n23");
        order.setUser(new User(8,"名字","pwd"));
        order.setItems(Arrays.asList((new Item[]{new Item(1,"item1",1),
                new Item(2,"item2",1),
                new Item(3,"item3",34),
                new Item(4,"item4",5),
                new Item(5,"item5",21)})));
        orderService.addOrder(order);
        return JsonResult.ok();
    }
}
