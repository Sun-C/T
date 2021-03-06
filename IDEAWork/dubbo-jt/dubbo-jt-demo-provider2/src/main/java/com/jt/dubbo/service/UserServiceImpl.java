package com.jt.dubbo.service;

import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.dubbo.mapper.UserMapper;
import com.jt.dubbo.pojo.User;
@Service(timeout=3000)	//3秒超时
public class UserServiceImpl implements UserService {

	public UserServiceImpl(){
		System.out.println("我被实例化了");
	}
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<User> findAll() {
		System.out.println("我是第二个服务提供者");
		return userMapper.selectList(null);
	}
	
	@Override
	public void saveUser(User user) {
		System.out.println("我是第二个服务提供者");
		userMapper.insert(user);
	}

}
