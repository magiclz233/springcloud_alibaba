package com.magic;

import com.magic.service.EchoService;
import com.magic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
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
@RefreshScope
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    private RestTemplate restTemplate1;

    @Autowired
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${magic.name}")
    private String name;

    @Value("${magic.sex}")
    private String sex;

    @Value("${magic.age}")
    private Integer age;

    @GetMapping("/test/{name}")
    public String test(@PathVariable("name") String name){
        if("magic".equals(name)){
            throw new RuntimeException();
        }
        return echoService.test(name);
    }

    @GetMapping("/config")
    public String config(){
        return name+","+age+","+sex;
    }

    @GetMapping("/rest/test/{name}")
    public String restTest(@PathVariable("name")String name){
        ResponseEntity forObject = restTemplate.getForObject("localhost:8001/echo/" + name, ResponseEntity.class);
        return (String) forObject.getBody();
    }
    @GetMapping("/rest1/test/{name}")
    public String rest1Test(@PathVariable("name")String name){
        ResponseEntity forObject = restTemplate1.getForObject("localhost:8001/echo/" + name, ResponseEntity.class);
        return (String) forObject.getBody();
    }
}
