package com.magic.rocketmq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author magic_lz
 * @version 1.0
 * @classname TestController
 * @date 2020/8/19 : 10:37
 */

@RestController
public class TestController {

    @GetMapping("/")
    public String test(){
        return "success";
    }
}
