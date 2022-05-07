package com.tyzz.blog.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-04-19 11:41
 */
@Aspect//封装了对切点的所有通知的类就叫切面
@Component
@Log4j2
public class ExecuteTimeAspect {
    @Autowired
    private HttpServletRequest request;

    //切点就是具体哪些需要被进行处理的方法
    @Pointcut("execution(public * com.tyzz.blog.controller.*.*.*(..))")
    public void pointcut() {}

    //通知就是对切入点方法的具体处理逻辑
    @Around("pointcut()")//而连接点就是目标方法
    public Object executeTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("当前访问地址：{}，用户IP：{} 客户端：{} =======================================",
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader("user-agent")
                );


        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();


        log.info("当前方法耗时：{} ms        =======================================", end - start);
        return result;
    }
}
