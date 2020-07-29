package com.jt.web.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DubboItemServiceImpl implements DubboItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Override
    public Item findItemById(long l) {
        Item item = itemMapper.selectById(l);
        return item;
    }

    @Override
    public ItemDesc findItemDescById(long l) {
        ItemDesc itemDesc = itemDescMapper.selectById(l);
        return itemDesc;
    }
}
