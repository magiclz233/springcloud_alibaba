package com.magic.lock.redis.common.lock;

/**
 * 分布式锁回调接口
 * @author magic_lz
 * @version 1.0
 * @classname DistributedLockCallback
 * @date 2021/3/3 : 17:32
 */
public interface DistributedLockCallback<T> {
    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     * @return T
     */
    public T process();

    /**
     * 得到分布式锁名称
     * @return lockName
     */
    public String getLockName();
}
