package com.jt.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 独立完成关于redis缓存实现
 * 拦截所有的业务层的方式 service 要求打印 sys目标方法类名/方法名称/参数相关信息.并且要求记录各个方法的执行时间 环绕通知
 * 实现异常的记录 但凡service层报错，需要打印目标方法类名/方法名/异常信息
 * 异常通知
 * 记录业务层service层调用的返回值信息。 输出 类名/方法名/参数信息/返回值信息
 */
@Aspect
@Component
@Slf4j
public class HomeWorkAOP {

    @Pointcut("within(com.jt.service..*)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object sysPrintln(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis()-start;
        String name = joinPoint.getSignature().getName();//获取切入点方法名
        Object [] obj = joinPoint.getArgs();//获取切入点方法参数
        List<Object> args = new ArrayList<>();
        for (Object o : obj){
            args.add(o);
        }
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();//全路径
        Object target = joinPoint.getTarget();//获取类
        log.info("-@Around------{},{},{},{},{}","方法名"+name,"参数"+args.toString(),"路径"+declaringTypeName,"类信息"+target,"执行时长"+end);
        return result;
    }
    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void error(JoinPoint joinPoint, Throwable e){
        String name = joinPoint.getSignature().getName();//获取切入点方法名
        Object target = joinPoint.getTarget();//获取类
        log.error("----Exception{},方法名{},类名{}",e.getMessage(),name,target);
    }
    /**
     * 记录业务层service层调用的返回值信息。 输出 类名/方法名/参数信息/返回值信息
     */
    @After("pointCut()")
    public void service(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        Class returnType = method.getReturnType();
        String s = returnType.toString();
        Object[] args = joinPoint.getArgs();
        List<Object> obj = new ArrayList<>();
        for (Object o : args){
            obj.add(o);
        }
        log.info("-@After-方法名{},类信息{},返回值信息{},方法参数{}",name,target.toString(),s,obj.toString());
    }
}
