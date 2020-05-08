package com.magic.clear;

import com.magic.service.EchoService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author magic_lz
 * @version 1.0
 * @name EchoServiceFallback
 * @description TODO
 * @date 2020/5/8   16:23
 **/
public class EchoServiceFallback implements EchoService {
    @Override
    public String test(@PathVariable("name") String name) {
        return "echo fallback";
    }
}
