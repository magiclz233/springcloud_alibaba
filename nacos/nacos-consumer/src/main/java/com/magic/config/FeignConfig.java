package com.magic.config;

import com.magic.clear.EchoServiceFallback;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author magic_lz
 * @version 1.0
 * @name FeignConfig
 * @description TODO
 * @date 2020/5/8   16:20
 **/

@Configurable
public class FeignConfig {

    public EchoServiceFallback echoServiceFallback(){
        return new EchoServiceFallback();
    }
}
