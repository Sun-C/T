package com.li.sp01.service;

import com.li.sp01.pojo.Item;

import java.util.List;

public interface ItemService {
    //查询订单中的商品列表
    List<Item> getItems(String orderId);
    //减少商品的库存
    void decreaseNumbers(List<Item> list);
}