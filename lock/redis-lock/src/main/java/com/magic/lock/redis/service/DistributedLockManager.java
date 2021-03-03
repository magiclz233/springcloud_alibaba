package com.magic.lock.redis.service;

import com.magic.lock.redis.common.annotation.DistributedLock;
import com.magic.lock.redis.common.lock.Action;
import com.magic.lock.redis.common.lock.thread.Worker1;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author magic_lz
 * @version 1.0
 * @classname RedisLockManager
 * @date 2021/3/3 : 17:10
 */

@Component
public class DistributedLockManager {

    @DistributedLock(argNum = 1, lockNamePost = ".lock")
    public Integer aspect(String lockName, Worker1 worker1){
        return worker1.aspectBusiness(lockName);
    }

    @DistributedLock(lockName = "lock", lockNamePost = ".lock")
    public int aspect(Supplier<Integer> supplier) {
        return supplier.get();
    }

    @DistributedLock(lockName = "lock", lockNamePost = ".lock")
    public void doSomething(Action action) {
        action.action();
    }
}
