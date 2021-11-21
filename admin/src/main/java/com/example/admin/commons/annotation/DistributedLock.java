package com.example.admin.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-10 18:08
 * @Description: 利用aop和注解的方式 加个分布式锁
 * 锁需要 key、锁的过期时间 防止死锁 、获取锁的超时时间
 * @Modified By:
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    /**
     * 默认包名加方法名
     *
     * @return
     */
    String key() default "";

    /**
     * 锁过期时间 秒
     *
     * @return
     */
    long expire() default 30;


    /**
     * 获取锁超时时间 毫秒
     *
     * @return
     */
    long timeout() default 3000;


}
