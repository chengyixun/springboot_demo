package com.example.admin.commons.componet;

import com.example.admin.commons.annotation.PermissionOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-11 11:50
 * @Description:
 * @Modified By:
 */
//@Component
@Slf4j
public class PermBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    /**
     * 可以拿到所有的 方法，判断 匹配上@PermissionOperation
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getDeclaredMethods(bean.getClass());
        if (null != methods) {
            for (Method method : methods) {
                PermissionOperation ann = AnnotationUtils.findAnnotation(method, PermissionOperation.class);
                if (null != ann) {
                    log.info("auth:{},desc:{}", ann.code(), ann.describe());
                    //可以存数据库 或者 放入缓存
                }
            }
        }
        return bean;
    }
}
