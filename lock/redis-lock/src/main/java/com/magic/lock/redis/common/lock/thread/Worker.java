package com.magic.lock.redis.common.lock.thread;

import com.magic.lock.redis.entity.Person;
import com.magic.lock.redis.service.DistributionService;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Worker
 * @date 2021/3/3 : 17:31
 */
public class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    private final DistributionService service;
    private RedissonClient redissonClient;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal, DistributionService service, RedissonClient redissonClient) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.service = service;
        this.redissonClient = redissonClient;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            Integer count = service.aspect(new Person(1, "Magic"));
//            Integer count = service.aspect("lock");
            System.out.println(Thread.currentThread().getName() + ": count = " + count);
            doneSignal.countDown();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
