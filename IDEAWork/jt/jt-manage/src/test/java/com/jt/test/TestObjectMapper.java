package com.jt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;


public class TestObjectMapper {
    //改对象就是工具api  有且只有一份即可.并且不允许别人修改.
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 目的: 实现对象与json串之间的转化
     * 步骤1:  将对象转化为json
     * 步骤2:  将json转化为对象
     * 利用ObjectMapper 工具API实现
     * @throws JsonProcessingException
     */
    @Test
    public void testObjectMapper() throws JsonProcessingException {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc("测试ObjectMapper").setItemId(1111L).setCreated(new Date()).setUpdated(itemDesc.getCreated());
        String json = MAPPER.writeValueAsString(itemDesc);
        System.out.println(json);
        ItemDesc itemDesc1 = MAPPER.readValue(json, ItemDesc.class);
        System.out.println(itemDesc1);
    }
}
