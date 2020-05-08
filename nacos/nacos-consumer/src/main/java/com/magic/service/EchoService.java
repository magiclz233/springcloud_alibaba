package com.magic.service;

import com.magic.clear.EchoServiceFallback;
import com.magic.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author magic_lz
 * @version 1.0
 * @name EchoService
 * @description TODO
 * @date 2020/5/8   16:22
 **/

@FeignClient(name = "nacos-provider",fallback = EchoServiceFallback.class,configuration = FeignConfig.class)
public interface EchoService {

    @GetMapping("/echo/{name}")
    String test(@PathVariable("name") String name);
}
