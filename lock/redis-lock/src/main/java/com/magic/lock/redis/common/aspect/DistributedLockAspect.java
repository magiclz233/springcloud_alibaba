package com.magic.lock.redis.common.aspect;

import com.magic.lock.redis.common.lock.DistributedLockTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author magic_lz
 * @version 1.0
 * @classname DistributeLockAspect
 * @date 2021/3/3 : 17:11
 */
@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    @Pointcut("@@annotation(com.magic.lock.redis.common.annotation.DistributedLock)")
    public void DistributedLockAspect(){}

    @Around(value = "DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint jp){
        //得到使用注解的方法。可使用Method.getAnnotation(Class<T> annotationClass)获取指定的注解，然后可获得注解的属性
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Object[] args = jp.getArgs();
        final String lockName = 
    }
}
