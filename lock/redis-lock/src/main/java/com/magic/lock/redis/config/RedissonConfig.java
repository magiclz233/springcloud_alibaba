package com.magic.lock.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author magic_lz
 * @version 1.0
 * @classname RedissonClientConfig
 * @date 2021/3/12 : 15:45
 */
@Configuration
public class RedissonConfig {


    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://106.75.216.194:6379");
        return Redisson.create(config);
    }
}
