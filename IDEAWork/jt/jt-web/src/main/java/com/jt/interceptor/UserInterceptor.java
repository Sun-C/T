package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component//不具有任何业务  就用公共注解Component 将对象交个spring容器
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 1.目的: 如果用户不登录 则不允许访问权限相关业务.
     * 返回值: true :表示放行
     *        false :表示拦截一般给一个配置重定向配置使用
     * 注意事项: 必须添加拦截器配置策略
     * 业务逻辑:
     *      用户如果已登录,则放行,反则拦截
     *
     * 如何判断用户是否登录:
     *  1.判断客户端是否有指定的Cookie true
     *  2.获取cookie的值 和redis中校验是否存在true 如果条件都满足就放行
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取cookie信息
        Cookie[] cookies = request.getCookies();
        //2.获取jt_ticket信息
        if (cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies){
                if ("JT_TICKET".equalsIgnoreCase(cookie.getName())){
                    if (jedisCluster.exists(cookie.getValue())) {
                        String userJson = jedisCluster.get(cookie.getValue());
                        User user = ObjectMapperUtil.toObject(userJson, User.class);
                        //把用户信息存入 request里面 利用request传输用户信息
                        request.setAttribute("JT_USER",user);
                        return true;
                    }
                }
            }
        }

        //重定向到登录页面
        response.sendRedirect("/user/login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
