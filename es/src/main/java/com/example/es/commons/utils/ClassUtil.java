package com.example.es.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 16:53
 * @Description:
 * @Modified By:
 */
@Slf4j
public class ClassUtil extends cn.hutool.core.util.ClassUtil {

    public static final String ARRAY_SUFFIX = "[]";
    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    public static final String CLASS_FILE_SUFFIX = ".class";
    private static final String INTERNAL_ARRAY_PREFIX = "[";
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";
    private static final char PACKAGE_SEPARATOR = '.';
    private static final char PATH_SEPARATOR = '/';
    private static final char INNER_CLASS_SEPARATOR = '$';

    public ClassUtil() {

    }

    /**
     *
     * @param clazz
     * @param annotation
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        if (null == clazz) {
            return null;
        } else {
            T ann = clazz.getAnnotation(annotation);
            if (null == ann) {
                return null;
            } else {
                //父类不是对象 则
                return clazz.getSuperclass() != Object.class ? getAnnotation(clazz.getSuperclass(),annotation): ann;
            }

        }

    }


}
