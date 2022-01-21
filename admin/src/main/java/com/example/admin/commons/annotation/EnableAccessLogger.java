package com.example.admin.commons.annotation;

import com.example.admin.commons.config.AopAccessLoggerSupportAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-06 18:00
 * @Description:
 * @Modified By:
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration({ AopAccessLoggerSupportAutoConfiguration.class })
public @interface EnableAccessLogger {
}
