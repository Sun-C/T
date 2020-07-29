package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items/")
public class ItemController {
    @Reference(check = false,loadbalance = "leastactive")
    private DubboItemService dubboItemService;
    @RequestMapping("{itemId}")
    public String findItem(Model model, @PathVariable Long itemId){
        Item item = dubboItemService.findItemById(itemId);
        ItemDesc itemDesc = dubboItemService.findItemDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}
