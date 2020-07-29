package com.li.sp02item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Sp02ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp02ItemApplication.class, args);
    }

}
