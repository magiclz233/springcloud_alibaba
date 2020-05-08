package com.magic;

import com.magic.service.EchoService;
import com.magic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author magic_lz
 * @version 1.0
 * @name TestController
 * @description TODO
 * @date 2020/5/8   16:30
 **/

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    private RestTemplate restTemplate1;

    @Autowired
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/test/{name}")
    public String test(@PathVariable("name") String name){
        if("magic".equals(name)){
            throw new RuntimeException();
        }
        return echoService.test(name);
    }
}
