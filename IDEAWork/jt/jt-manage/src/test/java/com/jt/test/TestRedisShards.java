package com.jt.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

public class TestRedisShards {
    @Test
    public void test01(){
        //1.准备list集合 之后添加节点信息
        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(new JedisShardInfo("192.168.126.129",6379));
        shards.add(new JedisShardInfo("192.168.126.129",6380));
        shards.add(new JedisShardInfo("192.168.126.129",6381));
        //2.创建分片对象  该API中包含了hash算法
        ShardedJedis shardedJedis = new ShardedJedis(shards);
        shardedJedis.set("shards","准备分片测试连接");
        System.out.println(shardedJedis.get("shards"));
    }
}
