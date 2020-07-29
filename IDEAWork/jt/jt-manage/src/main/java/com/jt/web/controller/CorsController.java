package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/")
public class CorsController {
    /**
     * url: http://manage.jt.com/web/testJSONP?callback=hello&_=1595324282562
     * @return JSONPObject 专门返回JSONP的返回值结果
     * 注意事项: 返回值结果
     */
    @RequestMapping("testCors")//URL 是 testJSONP
    public User testCors(){
        return new User().setId(1000L).setPassword("password").setUsername("CorsName");
    }

}
