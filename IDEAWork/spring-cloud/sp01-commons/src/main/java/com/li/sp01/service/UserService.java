package com.li.sp01.service;

import com.li.sp01.pojo.User;

public interface UserService {
    //查询用户 根据用户id
    User getUser(Integer id);
    //增加用户积分  根据用户id 添加指定积分
    void addScore(Integer id, Integer score);
}
