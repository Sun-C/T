package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user/")
public class UserController {
               //均衡策略                   check false 是消费者启动时不需要生产者的需要  默认为true
    @Reference(loadbalance = "leastactive",check = false)
    private DubboUserService dubboUserService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * url: www.jt.com/user/logout.html
     * 要求用户退出操作  删除 redis信息 并且删除cookie的JT_TICKET信息
     * @param request  request只能传递cookie的name和value 为了保证cookie安全不能传递其他参数
     *                 所以想要删除我们需要补全其他参数 比如path 和domain 不然要删除失败
     * @param response
     * @return
     */
   // @ResponseBody
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies!= null && cookies.length>0){
            for (Cookie cookie : cookies){
                if ("JT_TICKET".equalsIgnoreCase(cookie.getName())){
                    System.out.println(cookie.getName());
                    //1.删除redis记录
                    jedisCluster.del(cookie.getValue());
                    //2.删除cookie信息  立即删除 0,暂时不删除,关闭浏览器删除 -1
                    cookie.setDomain("jt.com");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        //重定向到首页 redirect 转发是forward
        //response.sendRedirect("http://www.jt.com");
        return "redirect:/";
    }

    /**
     * 1.url: http://www.jt.com/user/doLogin?r=0.36853287560995573
     * @param username  data: {username:_username,password:_password}
     * @param password
     * @return success: function (result) {
     *                 if (result) {
     *                     var obj = eval(result);
     *                     if (obj.status == 200) {
     * Cookie:参数说明:
     * Cookie: 特点 默认条件下,只能在自己的网址下进行展现,京东家的网址不能有百度家的cookie
     *          setDomain: 指定域名可以实现cookie参数共享.
     *          setPath: 只有特定的路径下,才能获取Cookie
     *                 网址: www.jd.com/abc/findAll
     *                      cookie.setPath("/aa")       只许/aa的路径下获取该Cookie信息  权限设定
     *                      cookie.setPath("/")  任意子网址都可以获取Cookie信息
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public SysResult doLogin(String username, String password, HttpServletResponse response){
        System.out.println(username+"::"+password);
        String ticke = dubboUserService.doLogin(username,password);
        if (StringUtils.isEmpty(ticke)) return SysResult.fail("账号或密码错误");
        Cookie ticket = new Cookie("JT_TICKET", ticke);
        ticket.setDomain("jt.com"); //只要网址是 以 jt.com结尾都可以实现cookie共享
        ticket.setMaxAge(7*24*60*60);//7天超时
        ticket.setPath("/");
        //讲cookie保存到客户端
        response.addCookie(ticket);
        return SysResult.success("ok");
    }
    /**
     * 完成用户的注册
     * 1.url : www.jt.com/user/doRegister
     * 2.参数: 用户名/密码/电话号码
     * 3.返回值: SysResult对象
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("doRegister")
    public SysResult doRegister(User user){
        dubboUserService.insertUser(user);
        return SysResult.success("OK");
    }

    /**
     * 实现用户的页面跳转
     * http//www.jt.com/user/register.html     后端页面register.jsp
     * http//www.jt.com/user/login.html         后端页面login.jsp
     * 重点: 为了实现业务功能.拦截后缀为.html的内容
     */

    @RequestMapping("{module}")
    public String pageUI(@PathVariable String module){
        return module;
    }
}
