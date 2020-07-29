package com.jt.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class TestPassWord {
    @Test
    public void test01(){
        String pwd = "123456789";
        String uuid = UUID.randomUUID().toString();
        System.out.println(pwd+uuid);
        String s = DigestUtils.md5DigestAsHex((pwd + uuid).getBytes());
        System.out.println(s);

    }
}
