package com.jt.test;

import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;
import sun.awt.image.codec.JPEGImageEncoderImpl;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestReids {
    Jedis jedis = new Jedis("192.168.126.129",6379);
    /**
     * 1.spring整合redis
     * 报错说明:
     *  !).如果测试过程中报错,则检测redis配置文件 改那三处
     *  2).检查redis启动方式 是否是 redis-server redis.conf
     *  3).检查Linux防火墙
     */
    //@Autowired
    //private Jedis jedis;
    @Test
    public void testString01(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.set("redis","redis的入门案例");
        String redis = jedis.get("redis");
        System.out.println(redis);
    }
    /**
     * 测试二
     */
    @Test
    public void testString02(){
        if (jedis.exists("a")){
            System.out.println(jedis.get("a"));
        }else {
            System.out.println("判断a是否存在");
        }
    }
    /**
     * 1.简化判读02的内容
     * 2.如果数据存在就修改 不存在就不修改
     *
     * setnx的方法: 只有当数据不存在时赋值 要是存在就不可重复赋值
     */
    @Test
    public void testString03(){
        jedis.set("a","aaa");
        jedis.set("a","bbb");
        String a = jedis.get("a");
        System.out.println(a);
        jedis.flushAll();
        jedis.setnx("a","这是第一次赋值1");
        jedis.setnx("a","这是第二次赋值2");
        System.out.println(jedis.get("a"));
    }

    /**
     * 超时时间
     *
     * setex方法
     */
    @Test
    public void testString04() throws InterruptedException {
        jedis.flushAll();
        //jedis.set("a","aaa");
        //如果发生报错 则超时的方法不会执行 数据则永不会超时
        //jedis.expire("a",20);
        /*
        原子性操作测试  一个方法搞定set值和超时操作
         */
        jedis.setex("a",20,"aaa");
        Thread.sleep(2000);
        System.out.println("剩余的存活时间"+jedis.ttl("a"));
    }

    /**
     * 1.只有数据不存在时可以赋值
     * 2.要求添加超时时间 并且是原子性操作
     * SetParams 参数说明
     * 1.NX 如果key不存在时可以修改赋值
     * 2.XX 如果key存在时才可以修改赋值
     * 3.PX 添加的时间单位是毫秒
     * 4.EX 添加的时间是秒
     */
    @Test
    public void testString05(){
        jedis.flushAll();
        //SetParams setParams = new SetParams();
        SetParams setParams = SetParams.setParams();
        setParams.xx().ex(20);
        jedis.set("a","测试原子性不可以修改",setParams);
        System.out.println(jedis.get("a"));
    }
    /**
     * 测试hash
     */
    @Test
    public void testHash(){
        jedis.flushAll();
        jedis.hset("user","java","jdk");
        jedis.hset("user","age","30");
        System.out.println(jedis.hgetAll("user"));
        jedis.flushAll();
        Map<String,String> map = new HashMap<>();
        map.put("id","3306");
        map.put("db","mysql");
        jedis.hset("public",map);
        System.out.println(jedis.hgetAll("public"));
    }
    /**
     * list 测试练习
     */
    @Test
    public void testList(){
        jedis.flushAll();
        jedis.lpush("list","1","2","3");
        System.out.println(jedis.lpop("list"));
    }

    /**
     * redis事务
     */
    @Test
    public void testTx(){
        jedis.flushAll();
        Transaction multi = jedis.multi();//开启事务

        try {
            multi.set("a","aa");
            multi.set("b","bb");
            multi.exec();//事务提交
        }catch (Exception e){
            e.printStackTrace();
            multi.discard();//事务回滚
        }
    }
    @Test
    public void testItemCat(){
        String s = jedis.get("com.jt.service.ItemCatServiceImpl.FindEasyUITree0");
        EasyUITree tree = ObjectMapperUtil.toObject(s,EasyUITree.class);
        System.out.println(tree);
    }
}
