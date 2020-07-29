package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 参数是itemCatId
     * @param itemCatId
     * @return
     */
    @RequestMapping("queryItemName")
    public String queryItemName(Long itemCatId){
        return itemCatService.queryItemName(itemCatId).getName();
    }

    /**
     * 业务: 查询商品分类信息,返回VO对象
     * url地址: /item/cat/list
     * 参数: 无
     * 返回值: EasyUITree对象 [{},{[]}]
     * sql语句:
     *       一级带单 parent_id=0 select * from tb_item_cat where parent_id=0
     *
     * @return
     */
    @RequestMapping("list")
    public List<EasyUITree> getItemCatList(@RequestParam(defaultValue = "0",value = "id") Long id){
        //Long parentId=0L;
        //Long id = id==null?0L:id;
        return itemCatService.FindEasyUITree(id);
        //缓存的方式获取数据
        //return itemCatService.findItemCatByCache(id);
    }
}
