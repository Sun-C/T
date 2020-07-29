package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.jt.mapper")//为mapper接口创建代理对象
@SpringBootApplication
public class SpringBootRunOrder {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRunOrder.class,args);
    }
}
