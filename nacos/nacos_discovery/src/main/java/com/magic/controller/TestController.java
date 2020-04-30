package com.magic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author magic_lz
 * @version 1.0
 * @name TestController
 * @description TODO
 * @date 2020/4/30 : 14:18
 **/

@RestController
public class TestController {

    @GetMapping("/test/{name}")
    public String test(@PathVariable("name") String name){
        LocalDate date = LocalDate.now();
        return date.toString()+" " + name;
    }
}
