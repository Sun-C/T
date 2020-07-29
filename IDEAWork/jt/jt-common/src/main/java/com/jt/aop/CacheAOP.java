package com.jt.aop;

import com.jt.annotation.CacheFind;
import com.jt.util.ObjectMapperUtil;
import com.sun.media.jfxmedia.logging.Logger;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

//1.定义aop切面
@Aspect
//2.交给spring管理
@Component
public class CacheAOP {
    @Autowired
    //private Jedis jedis;//单片redis
    //private ShardedJedis jedis;//多台分片 redis
    private JedisCluster jedis;//集群主从环注入
    /**
     * 实现思路: 拦截CacheFind标识的方法之后利用aop进行缓存控制
     * 通知方法: 环绕通知最合适
     *        1.准备查询redis的key   "com.jt.service.ItemCatServiceImpl.FindEasyUITree::"第一个参数
     *        2.@annotation(cacheFind)动态获取注解的方法
     *        拦截指定注解类型的注解并且将注解对象当做参数进行传递
     */
    //@Around("@annotation(com.jt.annotation.CacheFind)")
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind){

        //1.如何获取注解中的key?
        String key = cacheFind.key();
        System.out.println("CacheAOP"+key);
        //2.动态获取第一个参数当key
        String farstKey = joinPoint.getArgs()[0].toString();
        key+="::"+farstKey;
        Object result = null;
        //3.根据key存取redis
        if (jedis.exists(key)){
            String json = jedis.get(key);
            /*为了获取 注解描述方法的返回值类型 我们先获取 joinPoint.getSignature() 也就是切入点方法的 参数信息
              但是没有该方法返回值的类型方法 那么我们去找他下面的实现类 ctrl+t 查找到一个他的实现类有return的方法就强转
             */
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Class target = methodSignature.getReturnType();
            result = ObjectMapperUtil.toObject(json, target);
            System.out.println("查询redis缓存");
        }else {
            try {
                result = joinPoint.proceed();
                String json = ObjectMapperUtil.toJson(result);
                int secondes = cacheFind.secondes();
                if (secondes > 0)jedis.setex(key, secondes, json);
                else jedis.set(key, json);
                System.out.println("查询mysql数据库");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }
        }
        return result;
    }





    //公式: 切面 = 切入点表达式+通知方法
    /**
     * 业务需求: 要求拦截ItemCatServiceImpl
     * @Pointcut 切入点表达式可以理解为是一个if判断,只有满足条件才会执行通知方法
     */
    //@Pointcut("bean(itemCatServiceImpl)")//按类匹配,控制的粒度较粗 单个bean
    //@Pointcut("within(com.jt.service..*)")//匹配多个bean
//    @Pointcut("execution(* com.jt.service..*.*(..))")  //返回值类型  包名.类名.方法名.参数类型
//    public void pointCut(){}
//
//    @Before(value = "pointCut()")  //joinPoint 该方法执行切面恰好被切入点表达式匹配,该方法的执行被称之为连接点
//    public void before(JoinPoint joinPoint){
//        System.out.println("我是前置通知!!!");
//        String typeName = joinPoint.getSignature().getDeclaringTypeName();//获取路径
//        String methodName = joinPoint.getSignature().getName();//获取方法名
//        Object[] args = joinPoint.getArgs();//获取参数
//        Object target = joinPoint.getTarget();//获取目标对象
//        System.out.println("方法执行的全路径和参数为:"+typeName+"."+methodName+"."+args);
//        System.out.println("获取目标对象是:"+target);
//
//    }
    //环绕通知 可以控制目标方法执行 要求添加参数
//    @Around("pointCut()")
//    public Object around(ProceedingJoinPoint joinPoint){
//        System.out.println("我是环绕通知的开始");
//        try {
//            Object proceed = joinPoint.proceed();
//            System.out.println("我是环绕通知的结束");
//            return proceed;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            new RuntimeException(throwable);
//        }
//        return null;
//    }

}
