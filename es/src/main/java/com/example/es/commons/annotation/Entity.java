package com.example.es.commons.annotation;

import java.lang.annotation.*;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-07 17:19
 * @Description:
 * @Modified By:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Entity {

    /**
     * 索引名
     *
     * @return
     */
    String index();

    /**
     * 类型
     *
     * @return
     */
    String type();

    /**
     * 别名
     *
     * @return
     */
    String alias();


}
