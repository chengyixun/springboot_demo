package com.example.mybatispluscrud.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TaskExecutorConfig @Author: amy @Description: TaskExecutorConfig @Date:
 * 2021/11/18 @Version: 1.0
 */
@Slf4j
@Configuration
public class TaskExecutorConfig {

  @Bean
  public Executor loggerTaskExecutor() {
    int coreSize = Runtime.getRuntime().availableProcessors() * 2;
    log.info(" TaskExecutor.coreSize:{}", coreSize);
    return new ThreadPoolExecutor(
        coreSize, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
  }
}
