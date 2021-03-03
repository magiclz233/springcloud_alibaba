package com.magic.lock.redis.service;

import com.magic.lock.redis.common.annotation.DistributedLock;
import com.magic.lock.redis.entity.Person;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributionService {

    @Autowired
    private RedissonClient redissonClient;

    @DistributedLock(param = "id", lockNamePost = ".lock")
    public Integer aspect(Person person) {
        RMap<String, Integer> map = redissonClient.getMap("distributionTest");

        Integer count = map.get("count");

        if (count > 0) {
            count = count - 1;
            map.put("count", count);
        }

        return count;
    }

    @DistributedLock(argNum = 1, lockNamePost = ".lock")
    public Integer aspect(String i) {
        RMap<String, Integer> map = redissonClient.getMap("distributionTest");

        Integer count = map.get("count");

        if (count > 0) {
            count = count - 1;
            map.put("count", count);
        }

        return count;
    }

}
