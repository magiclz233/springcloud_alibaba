package com.magic.lock.redis.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.magic.lock.redis.common.lock.thread.Worker;
import com.magic.lock.redis.common.lock.thread.Worker1;
import com.magic.lock.redis.service.DistributedLockManager;
import com.magic.lock.redis.service.DistributionService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author magic_lz
 * @version 1.0
 * @classname DistributedLockTestController
 * @date 2021/3/12 : 15:17
 */
@RestController
@RequestMapping("/lock")
public class DistributedLockTestController {
    private final int COUNT = 10;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private DistributionService service;

    @Autowired
    private DistributedLockManager manager;

    @GetMapping
    public String distributedLockTest() throws Exception {
        RMap<Object, Object> map = redissonClient.getMap("distributionTest");
        map.put("count",8);
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(COUNT);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < COUNT; ++i) {
//            singleThreadPool.execute(new Worker(startSignal,doneSignal,service,redissonClient));
            singleThreadPool.execute(new Worker1(startSignal,doneSignal,manager,redissonClient));
        }
        startSignal.countDown();
        doneSignal.await();
        singleThreadPool.shutdown();
        System.out.println("All processors done. Shutdown connection");
        return "finish";
    }
}
