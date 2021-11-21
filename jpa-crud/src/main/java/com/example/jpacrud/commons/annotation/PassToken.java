package com.example.jpacrud.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: PassToken
 * @Author: amy
 * @Description: PassToken
 * @Date: 2021/7/12
 * @Version: 1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {

	boolean required() default true;

}
