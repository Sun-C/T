package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * spring 整合Redis 框架集群
 */
@PropertySource("classpath:/properties/redis.properties")
@Configuration//我是一个配置类 一般都会给@Bean配合使用
public class RedisConfig {

    //多台集群机制
    @Value("${redis.nodes}")
    private String nodes;
    //单台单片 配置方法
   /* @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;*/

    /**
     * 将返回值交个spring管理 以后想使用直接注入使用@Autowired
     * @return
     */
    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        String [] HostAndPort = this.nodes.split(",");
        for(String node : HostAndPort){//host:port
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            nodes.add(new HostAndPort(host,port));
        }
        return new JedisCluster(nodes);
    }
//    @Bean
//    public ShardedJedis shardedJedis(){
//        //1.准备redis分片初始化创建
//        List<JedisShardInfo> list = new ArrayList<>();
//        String [] node = nodes.split(",");
//        for (String s : node){
//            String host = s.split(":")[0];
//            int port = Integer.parseInt(s.split(":")[1]);
//            list.add(new JedisShardInfo(host,port));
//        }
//        //返回 分片机制的ShardedJedis实例
//        return new ShardedJedis(list);
//    }






    //    @Bean//单台配置
//    public Jedis jedis(){
//        return new Jedis(host,port);
//    }
}
