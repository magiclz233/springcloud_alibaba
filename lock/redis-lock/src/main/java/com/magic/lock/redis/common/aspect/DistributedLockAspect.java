package com.magic.lock.redis.common.aspect;

import com.magic.lock.redis.common.annotation.DistributedLock;
import com.magic.lock.redis.common.lock.DistributedLockCallback;
import com.magic.lock.redis.common.lock.DistributedLockTemplate;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @Pointcut("@annotation(com.magic.lock.redis.common.annotation.DistributedLock)")
    public void DistributedLockAspect() {
    }

    @Around(value = "DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint jp) {
        //得到使用注解的方法。可使用Method.getAnnotation(Class<T> annotationClass)获取指定的注解，然后可获得注解的属性
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Object[] args = jp.getArgs();
        final String lockName = getLockName(method, args);
        return lock(jp, method, lockName);
    }

    public String getLockName(Method method, Object[] args) {
        Objects.requireNonNull(method);
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        String lockName = annotation.lockName();
        String param = annotation.param();
        if (isEmpty(lockName)) {
            if (args.length > 0) {
                if (isNotEmpty(param)) {
                    Object arg;
                    if (annotation.argNum() > 0) {
                        arg = args[annotation.argNum() - 1];
                    } else {
                        arg = args[0];
                    }
                    lockName = String.valueOf(getParam(arg, param));
                }else if(annotation.argNum() > 0) {
                    lockName = args[annotation.argNum()-1].toString();
                }
            }
        }
        if (isNotEmpty(lockName)) {
            String preLockName = annotation.lockNamePre(),
                    postLockName = annotation.lockNamePost(),
                    separator = annotation.separator();

            StringBuilder lName = new StringBuilder();
            if (isNotEmpty(preLockName)) {
                lName.append(preLockName).append(separator);
            }
            lName.append(lockName);
            if (isNotEmpty(postLockName)) {
                lName.append(separator).append(postLockName);
            }

            lockName = lName.toString();

            return lockName;
        }
        throw new IllegalArgumentException("Can't get or generate lockName accurately!");
    }

    /**
     * 从方法参数获取数据
     *
     * @param param
     * @param arg   方法的参数数组
     * @return
     */
    public Object getParam(Object arg, String param) {

        if (isNotEmpty(param) && arg != null) {
            try {
                Object result = PropertyUtils.getProperty(arg, param);
                return result;
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "没有属性" + param + "或未实现get方法。", e);
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        }
        throw new IllegalArgumentException("");
    }

    public Object lock(ProceedingJoinPoint jp, Method method, final String lockName) {
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        boolean fairLock = annotation.fairLock();
        boolean tryLock = annotation.tryLock();
        if (tryLock) {
            return tryLock(jp, annotation, lockName, fairLock);
        } else {
            return lock(jp, lockName, fairLock);
        }

    }

    public Object lock(ProceedingJoinPoint jp, final String lockName, boolean fairLock) {
        return lockTemplate.lock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(jp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, fairLock);
    }

    public Object tryLock(ProceedingJoinPoint jp, DistributedLock annotation, final String lockName, boolean fairLock) {
        long waitTime = annotation.waitTime(),
                leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        return lockTemplate.tryLock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(jp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, waitTime, leaseTime, timeUnit, fairLock);
    }

    public Object proceed(ProceedingJoinPoint jp) {
        try {
            return jp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    private boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}
