package com.example.admin.service;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-22 10:36
 * @Description:
 * @Modified By:
 */
public interface RedisDistributedLockService {


    /**
     * 获取锁
     *
     * @param key            锁的key值
     * @param expireTime     锁的过期时间
     * @param requireTimeOut 获取锁的超时时间
     * @return
     */
    boolean lock(String key, Long expireTime, Long requireTimeOut);

    /**
     * 释放锁
     *
     * @param key
     * @return
     */
    boolean release(String key);


    void testLog();


}
