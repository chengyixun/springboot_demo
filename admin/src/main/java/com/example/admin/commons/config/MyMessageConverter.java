package com.example.admin.commons.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.admin.commons.annotation.WriteNullListAsNull;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * {@link MyMessageConverter}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-09
 */
public class MyMessageConverter extends FastJsonHttpMessageConverter {

    /**
     * 表明只处理 没有 WriteNullListAsNull 注解的实体类
     *
     * @param clazz
     * @return
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
        for (Annotation ann : declaredAnnotations) {

        }
       clazz.getComponentType();

        WriteNullListAsNull annotation = clazz.getAnnotation(WriteNullListAsNull.class);
        return null != annotation;
    }


    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {


        return super.readInternal(clazz, inputMessage);
    }


}
