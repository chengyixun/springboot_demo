package com.example.admin.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-05 17:43
 * @Description:
 * @Modified By:
 */
@Slf4j
@Configuration
public class LoggerTaskExecutorConfig {

    @Bean
    public Executor loggerTaskExecutor() {
        int coreSize = Runtime.getRuntime().availableProcessors() * 2;
        log.info("loggerTaskExecutor.coreSize:{}", coreSize);
        return new ThreadPoolExecutor(coreSize, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }


}
