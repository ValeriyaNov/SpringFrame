package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggableAspect {
    @Pointcut("@annotation(com.example.demo.aspect.Timer)")
    public void methodsAnnotatedWith() {

    }
    @Around("methodsAnnotatedWith()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)throws Throwable {

        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        Long timeElapsed = endTime - startTime;
        log.info("{} - {} #({} mseconds)", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), timeElapsed / 1000.0);
        System.out.println( timeElapsed / 1000.0);
        return result;
    }
}
