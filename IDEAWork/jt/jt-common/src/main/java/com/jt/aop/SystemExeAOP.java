package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class SystemExeAOP {
    /**
     * 添加通用异常处理方法
     * 运行原理: AOP异常通知
     *
     * 常规手段: SysResult.fali();
     * 跨域访问: JSONP 必须满足JSONP的跨域访问要求 callback(SysResult.fail())
     * 问题: 如何断定用户使用的是跨域业务还是普通访问业务??
     * 分析: jsonp请求 get请求方式携带?callback
     * 判断依据: 用户参数是否携带callback 特定参数一般条件不会使用
     */
    @ExceptionHandler(RuntimeException.class)//拦截运行时异常
    public Object systemResulteException(HttpServletRequest request, Exception e){
        String method = request.getMethod();
        String callback = request.getParameter("callback");
        if (! method.equalsIgnoreCase("GET")){
            log.info("运行时异常------------>{"+e.getMessage()+"}",e);//如果有异常打印日志
            //log.error("运行时异常------------>{}",e);//如果有异常打印日志
            return SysResult.fail(e.getMessage());//返回异常的内容
        }
<<<<<<< HEAD
        if (callback!=null)
=======
>>>>>>> c5eda464de27773d3009be6e95de3a7f2e9c3f9a
        if (callback.isEmpty()){
            log.info("运行时异常------------>{"+e.getMessage()+"}",e);//如果有异常打印日志
            //log.error("运行时异常------------>{}",e);//如果有异常打印日志
            return SysResult.fail(e.getMessage());//返回异常的内容
        }
        //3.如果程序执行到这里,标识进行了JSONP的请求. 按照JSONP的方式返回数据
        log.info("运行时异常------------>{"+e.getMessage()+"}",e);//如果有异常打印日志
        //log.error("运行时异常------------>{}",e);//如果有异常打印日志
        return new JSONPObject(callback,SysResult.fail(e.getMessage()));//返回异常的内容
    }
}
