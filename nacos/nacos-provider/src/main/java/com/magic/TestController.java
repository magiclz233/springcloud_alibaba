package com.magic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author magic_lz
 * @version 1.0
 * @name TestController
 * @description TODO
 * @date 2020/5/6   9:39
 **/

@RestController
@RequestMapping("/echo")
public class TestController {

    @GetMapping("/{name}")
    public String test(@PathVariable String name){
        return "hello " + name + LocalDate.now().toString();
    }
}
