package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    private static Map<Integer,String> paramMap;//写死不变的数据可以 这样维护
    static {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"username");
        map.put(2,"phone");
        map.put(3,"email");
        paramMap=map;
    }
    /**
     * 判断用户名/手机号/邮箱 是否被占用
     * 校验数据库中是否已存在数据
     * 实现 查询是否存在
     * @param param
     * @param type 1:username|2:phone|3:email
     * @return
     */
    @Override
    public boolean findParams(String param, Integer type) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(paramMap.get(type),param );
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
        return userMapper.selectCount(queryWrapper)<1?false:true;
    }
}
