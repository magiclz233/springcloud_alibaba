package com.magic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author magic_lz
 * @version 1.0
 * @name RestConfig
 * @description TODO
 * @date 2020/4/30   17:02
 **/

@Configurable
public class RestConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
