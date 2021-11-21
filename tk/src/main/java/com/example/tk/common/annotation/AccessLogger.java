package com.example.tk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-29 16:56
 * @Description:
 * @Modified By:
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLogger {


    /**
     * 日志值
     *
     * @return
     */
    String value();

    /**
     * 关闭日志 默认不关闭
     *
     * @return
     */
    boolean ignore() default false;

}
