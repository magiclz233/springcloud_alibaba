package com.magic.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.magic.clear.UrlCleaner;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author magic_lz
 * @version 1.0
 * @name RestConfig
 * @description TODO
 * @date 2020/4/30   17:02
 **/

@Component
public class RestConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(urlCleanerClass = UrlCleaner.class,urlCleaner = "clean")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate1(){
        return new RestTemplate();
    }

}
