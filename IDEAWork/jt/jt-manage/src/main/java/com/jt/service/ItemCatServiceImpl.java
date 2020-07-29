package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.annotation.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
@Slf4j
@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private ItemCatMapper itemCatMapper;
    //required spring容器初始化时,该注解不是必须注入,但是如果程序调用时 必须有值
    @Autowired(required = false)
    private Jedis jedis;

    @CacheFind(key = "com.jt.service.ItemCatServiceImpl.queryItemName")
    @Override
    public ItemCat queryItemName(Long id) {
        ItemCat itemCat = itemCatMapper.selectById(id);
        return itemCat;
    }

    /**
     * 数据来源:数据库中
     * 数据库中的数据类型  : ItemCat对象信息系- POJO
     * 数据库中的数据类型  : EasyUITree对象信息系->VO对象
     * 思路: ItemCat转换为EasyUITree对象
     * @return
     * @param parentId
     */

    @CacheFind(key = "com.jt.service.ItemCatServiceImpl.FindEasyUITree")
    @Override
    public List<EasyUITree> FindEasyUITree(Long parentId){
        List<EasyUITree> listTree = new ArrayList<>();

        //1.根据ParaentId 对数据库信息 根据父级查询子级信息
        QueryWrapper<ItemCat> itemCatQueryWrapper = new QueryWrapper<>();
        itemCatQueryWrapper.eq("parent_id",parentId);
        List<ItemCat> itemCats = itemCatMapper.selectList(itemCatQueryWrapper);
        itemCats.forEach(
                itemCat ->{
                    EasyUITree tree = new EasyUITree(itemCat.getId(), itemCat.getName(), itemCat.getIsParent() ? "closed" : "open");
                    listTree.add(tree);
                });
        //findRedisEasyUITree(key, value);
        return listTree;
    }

    /**
     * 通过缓存方式查询数据库
     * @param id
     * @return
     * @SuppressWarnings("unchecked") 压制警告
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EasyUITree> findItemCatByCache(Long id) {
        //定义key  冒号是常规写法
        String key = "com.jt.service.ItemCatServiceImpl.findItemCatByCache::"+id;
        List<EasyUITree> listTree = new ArrayList<>();
        if (jedis.exists(key)){
            String json = jedis.get(key);
            log.info("------------------{我走的redis缓存}");
            //return ObjectMapperUtil.toObject(json, listTree.getClass());
            listTree = ObjectMapperUtil.toObject(json, listTree.getClass());
        }else {
            listTree = FindEasyUITree(id);
            jedis.set(key,ObjectMapperUtil.toJson(listTree));
            log.info("-----------------{第一次查询 走的数据库}");
        }
        return listTree;
    }
    /**
     * redis缓存机制
     */

//    public List<EasyUITree> findRedisEasyUITree(String key,String value){
//        String json = jedis.get(key);
//        jedis.set(key,value);
//        EasyUITree list = ObjectMapperUtil.toObject(json, EasyUITree.class);
//        return list;
//    }
}
