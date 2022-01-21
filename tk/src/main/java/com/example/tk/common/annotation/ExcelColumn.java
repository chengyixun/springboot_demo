package com.example.tk.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ExcelColumn
 * @Author: amy
 * @Description: ExcelColumn
 * @Date: 2021/8/15
 * @Version: 1.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
	/**
	 * excel header name unique
	 *
	 * @return value
	 */
	String value() default "";

	/**
	 * excel row index
	 *
	 * @return col
	 */
	int col() default 0;
}
