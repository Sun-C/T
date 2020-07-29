package com.li.sp03user.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.li.sp01.pojo.User;
import com.li.sp01.service.UserService;
import com.li.sp01.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Value("${sp.user-service.users}")
    private String userJson;
    @Override
    public User getUser(Integer id) {
        List<User> users1 = new ArrayList<>();
        //这是一个匿名内部类  就是为了获取List<User>的类型  以前都是传users.getClass()
        List<User> users = JsonUtil.from(userJson, new TypeReference<List<User>>() {});
        for (User u : users){
            if (id.equals(u.getId())){
                return u;
            }
        }
        return new User(id,"name-"+id,"pwd-"+id);
    }

    @Override
    public void addScore(Integer id, Integer score) {
        log.info("增加用户积分::userId="+id+",score="+score);
    }
}
