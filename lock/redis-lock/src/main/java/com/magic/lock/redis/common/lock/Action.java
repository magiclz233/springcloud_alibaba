package com.magic.lock.redis.common.lock;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Lock
 * @date 2021/3/3 : 17:19
 */

@FunctionalInterface
public interface Action {
    void action();
}
