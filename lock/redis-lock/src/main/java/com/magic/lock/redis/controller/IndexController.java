package com.magic.lock.redis.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author magic_lz
 * @version 1.0
 * @classname IndexController
 * @date 2021/3/3 : 16:55
 */
@RequestMapping("/IndexController")
@RestController
public class IndexController {

    private StringRedisTemplate redisTemplate;
}
