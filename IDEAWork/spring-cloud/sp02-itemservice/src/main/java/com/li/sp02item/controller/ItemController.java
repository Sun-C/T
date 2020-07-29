package com.li.sp02item.controller;

import com.li.sp01.pojo.Item;
import com.li.sp01.service.ItemService;
import com.li.sp01.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Value("${server.port}")
    private int port;
    @GetMapping("/{orderId}")
    public JsonResult<List<Item>>  getItems(@PathVariable String orderId){
        log.info("server.port="+port+",orderId="+orderId);
        List<Item> items = itemService.getItems(orderId);
        new LinkedList<>();
        return JsonResult.ok(items).msg("port="+port);

    }
    @PostMapping("/decreaseNumber")
    public JsonResult decreaseNumber(@RequestBody List<Item> items){
        itemService.decreaseNumbers(items);
        return JsonResult.ok().msg("减少商品库存成功");
    }
}

