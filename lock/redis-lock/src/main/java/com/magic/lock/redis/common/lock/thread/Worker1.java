package com.magic.lock.redis.common.lock.thread;

import com.magic.lock.redis.service.DistributedLockManager;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Worker1
 * @date 2021/3/3 : 17:42
 */
public class Worker1 implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    private final DistributedLockManager distributedLockManager;
    private RedissonClient redissonClient;

    public Worker1(CountDownLatch startSignal, CountDownLatch doneSignal, DistributedLockManager distributedLockManager, RedissonClient redissonClient) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.distributedLockManager = distributedLockManager;
        this.redissonClient = redissonClient;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " start");

            startSignal.await();

            Integer count = aspect("lock");

            /*Integer count = distributedLockManager.aspect(() -> {
                return aspect();
            });*/

            System.out.println(Thread.currentThread().getName() + ": count = " + count);

            doneSignal.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int aspect(){
        RMap<String, Integer> map = redissonClient.getMap("distributionTest");
        Integer count1 = map.get("count");
        if (count1 > 0) {
            count1 = count1 - 1;
            map.put("count", count1);
        }
        return count1;
    }

    public int aspect(String lockName) {
        return distributedLockManager.aspect(lockName, this);
    }

    public int aspectBusiness(String lockName) {
        RMap<String, Integer> map = redissonClient.getMap("distributionTest");

        Integer count = map.get("count");

        if (count > 0) {
            count = count - 1;
            map.put("count", count);
        }

        return count;
    }
}
