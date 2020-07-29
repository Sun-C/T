package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 判断 ticket是否存在 如果存在返回用户信息
     * @return
     */
    @RequestMapping("query/{ticket}")
    public JSONPObject findTicket(String callback,@PathVariable String ticket){
        if (jedisCluster.exists(ticket)){
            String data = jedisCluster.get(ticket);
            return new JSONPObject(callback,SysResult.success("因为data是String类型所以我又添加了这句话",data));
        }else {
            return new JSONPObject(callback,SysResult.fail("请登录"));
        }
    }


    /**
     * 注册参数验证
     * URL: http://sso.jt.com/user/check/admin123/1?r=0.07521444731394133&callback=jsonp1595331268957&_=1595331281732
     * 2.参数: param 需要校验的数据
     *        type 校验类型
     * 3.返回值结果: SysResult对象 data: false/true
     * 4.jsonp跨域访问
     * 代码要 安全 快 省  算法影响快慢
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkParam(@PathVariable String param, @PathVariable Integer type,String callback){
        boolean falg = userService.findParams(param,type);//要求返回true/false
        SysResult sysResult = SysResult.success(falg);
        //int a = 1/0;//测试 跨域异常是否可以进行抛出返回
        return new JSONPObject(callback,sysResult);
    }

    /**
<<<<<<< HEAD
=======
     * 注册参数验证
     * URL: http://sso.jt.com/user/check/admin123/1?r=0.07521444731394133&callback=jsonp1595331268957&_=1595331281732
     * 2.参数: param 需要校验的数据
     *        type 校验类型
     * 3.返回值结果: SysResult对象 data: false/true
     * 4.jsonp跨域访问
     * 代码要 安全 快 省  算法影响快慢
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkParam(@PathVariable String param, @PathVariable Integer type,String callback){
        boolean falg = userService.findParams(param,type);//要求返回true/false
        SysResult sysResult = SysResult.success(falg);
        //int a = 1/0;//测试 跨域异常是否可以进行抛出返回
        return new JSONPObject(callback,sysResult);
    }

    /**
>>>>>>> c5eda464de27773d3009be6e95de3a7f2e9c3f9a
     * 测试搭建环境是否成功
     * @return
     */
    @RequestMapping("/getMsg")
    public String getMsg(){
        return "sso系统测试搭建完成!!!";
    }
}
