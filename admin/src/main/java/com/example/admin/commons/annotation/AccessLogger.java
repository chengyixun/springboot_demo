package com.example.admin.commons.annotation;

import java.lang.annotation.*;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-29 16:56
 * @Description:
 * @Modified By:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccessLogger {

    /**
     * 日志值
     *
     * @return
     */
    String value();

    /**
     * 描述
     *
     * @return
     */
    String[] describe() default {""};

    /**
     * 关闭日志 默认不关闭
     *
     * @return
     */
    boolean ignore() default false;

}
