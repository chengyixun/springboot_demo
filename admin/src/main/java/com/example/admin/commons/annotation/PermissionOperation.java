package com.example.admin.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link PermissionOperation}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-12
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionOperation {

	/**
	 * key值
	 *
	 * @return
	 */
	String code();

	/**
	 * 描述
	 *
	 * @return
	 */
	String describe() default "";
}
