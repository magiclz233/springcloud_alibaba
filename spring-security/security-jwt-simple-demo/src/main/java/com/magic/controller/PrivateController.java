package com.magic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/1 23:36
 */

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping
    public String getMessage() {
        return "Hello from private API controller";
    }
}
