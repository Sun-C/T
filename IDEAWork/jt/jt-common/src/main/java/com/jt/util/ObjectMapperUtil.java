package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;


public class ObjectMapperUtil {
    //json 与对象转化 优化异常处理
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //1.将对象转换为json
    public static String toJson(Object target){
        if (target==null)throw new NullPointerException("参数target不能为null");
        try {
            return MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        throw new RuntimeException(e);//如果转化过程中有问题则直接抛出异常
        }
    }
    //2. 将json串转化为对象   用户传递什么样的类型,就返回什么样的对象!!!
    // <T>  定义了一个泛型对象  代表任意类型
    public static <T> T toObject(String json,Class<T> targetClass){
        if (StringUtils.isEmpty(json)||targetClass==null)throw new NullPointerException("参数不能为null");
        try {
            return MAPPER.readValue(json,targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
