package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//相当于web.xml文件
public class MvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;

    //开启匹配后缀型配置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)//添加注册拦截策略对象操作
                .addPathPatterns("/cart/**","/order/**");// /**表示多级目录 /*表示只拦截一级目录
    }
}
