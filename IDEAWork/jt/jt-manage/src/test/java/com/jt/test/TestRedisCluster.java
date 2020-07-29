package com.jt.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class TestRedisCluster {
    @Test
    public void test01(){
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("192.168.126.129",7000));
        set.add(new HostAndPort("192.168.126.129",7001));
        set.add(new HostAndPort("192.168.126.129",7002));
        set.add(new HostAndPort("192.168.126.129",7003));
        set.add(new HostAndPort("192.168.126.129",7004));
        set.add(new HostAndPort("192.168.126.129",7005));
        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("AAAAA","redis集群测试!");
        System.out.println(jedisCluster.get("AAAAA"));

    }
}
