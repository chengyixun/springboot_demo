package com.example.admin.commons.config;

import com.example.admin.commons.aop.AopAccessLoggerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-05 10:22
 * @Description:
 * @Modified By:
 */
@Configuration
public class AopAccessLoggerSupportAutoConfiguration {


    @Bean
    public AopAccessLoggerSupport aopAccessLoggerSupport(){
        return new AopAccessLoggerSupport();
    }
}
