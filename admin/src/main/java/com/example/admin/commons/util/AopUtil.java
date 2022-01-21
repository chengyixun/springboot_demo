package com.example.admin.commons.util;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-04 14:51
 * @Description:
 * @Modified By:
 */
public class AopUtil {

	public AopUtil() {

	}

	public static <T extends Annotation> T findMethodAnnotation(Class targetClass, Method method, Class<T> annClass) {
		T a = AnnotationUtils.findAnnotation(method, annClass);
		if (a != null) {
			return a;
		} else {
			Method m = ClassUtils.getMostSpecificMethod(method, targetClass);
			a = AnnotationUtils.findAnnotation(m, annClass);
			return a;
		}
	}

	public static <T extends Annotation> T findAnnotation(Class targetClass, Class<T> annClass) {
		return AnnotationUtils.findAnnotation(targetClass, annClass);
	}

	public static <T extends Annotation> T findAnnotation(Class targetClass, Method method, Class<T> annClass) {
		T a = findMethodAnnotation(targetClass, method, annClass);
		return a != null ? a : findAnnotation(targetClass, annClass);
	}

}
