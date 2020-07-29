package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

    int insertUser(User user);

    String doLogin(String username, String password);
}
