package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.jt.mapper")
@SpringBootApplication
public class SpringBootSSO {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSSO.class,args);
    }
}
