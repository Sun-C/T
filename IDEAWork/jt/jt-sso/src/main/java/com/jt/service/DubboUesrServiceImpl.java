package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;

@Service
public class DubboUesrServiceImpl implements DubboUserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedis;
    /**
     * 参数处理
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int insertUser(User user) {

        String password = user.getPassword();
        //DigestUtils spring util家的
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password).setEmail(user.getPhone()).setCreated(new Date()).setUpdated(user.getUpdated());
        System.out.println(password);
        int i = userMapper.insert(user);
        return i;
    }

    @Override
    public String doLogin(String username, String password) {
        /*
        也可以以对象方式传输  把密码加密 比如 DigestUtils.md5DigestAsHex(user.getPassword().getBytes())
        然后根据不为空的数据记性查询 select * from tb_user where username = user.getUsername() and password = user.getPWD();
         */
        //              这样的写法 可以将user 不为null的属性充当where条件
        //QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);
        //Assert.notNull(user,"账号不存在");
        if (StringUtils.isEmpty(user))return null;
        String webPWD = DigestUtils.md5DigestAsHex(password.getBytes());
        if (! webPWD.equals(user.getPassword()))throw new IllegalArgumentException("密码错误");
                                                    //把-替换成空串
        String ticket = UUID.randomUUID().toString().replace("-","");
        user.setPassword("你知道的太多了");
        jedis.setex(ticket, 7*24*3600,ObjectMapperUtil.toJson(user));
        return ticket;
    }
}
