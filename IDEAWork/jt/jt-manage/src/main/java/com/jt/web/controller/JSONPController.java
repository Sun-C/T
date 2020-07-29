package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/")
public class JSONPController {
    /**
     * url: http://manage.jt.com/web/testJSONP?callback=hello&_=1595324282562
     * @param jsonp
     * @return JSONPObject 专门返回JSONP的返回值结果
     * 注意事项: 返回值结果
     */
    @RequestMapping("{jsonp}")//URL 是 testJSONP
    public JSONPObject jsonp(@PathVariable String jsonp,String callback){
        User user = new User();
        user.setId(1000L).setPassword("password").setUsername("JSONPName");
        //String json = ObjectMapperUtil.toJson(user);
        return new JSONPObject(callback,user);
    }

}
