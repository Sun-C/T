package com.li.sp03user.controller;

import com.li.sp01.pojo.User;
import com.li.sp01.service.UserService;
import com.li.sp01.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        User user = userService.getUser(userId);
        return JsonResult.ok().data(user);
    }
    @GetMapping("/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId, @RequestParam Integer score){
        userService.addScore(userId,score);
        return JsonResult.ok("积分添加成功");
    }
}
