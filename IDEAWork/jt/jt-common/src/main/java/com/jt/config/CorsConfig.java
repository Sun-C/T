package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//相当于web.xml中的配置 WebMvcConfig
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 配置后端服务器可以跨域的清单
     * 参数说明: addMappming: 设么样的请求可以跨域  /web/** /aaa/*   /* 配置一级目录 /**匹配多级目录 使用较多
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {          //.allowedHeaders()配置请求头可以不写
        registry.addMapping("/**").allowedOrigins("*")//配置源路径 http://www.jt.com *代表通配
                .allowedMethods("GET","POST","PUT","DELETE","HEAD")//配置允许的请求方法
                .allowCredentials(true)//是否可以携带cookie
                .maxAge(1800);   //探针机制  允许跨域的访问的时间周期
    }
}
