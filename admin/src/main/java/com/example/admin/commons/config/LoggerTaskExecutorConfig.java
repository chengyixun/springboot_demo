package com.example.admin.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/** @Author: wangyu @Date: Created 2021-01-05 17:43 @Description: @Modified By: */
@Slf4j
@EnableAsync
@Configuration
public class LoggerTaskExecutorConfig {

  /**
   * 2021-11-22 因为spring.task 配置不生效
   * 使用@Async时注意，原本默认配置项中的 queueCapacity 无上限，容易OOM
   * <p>不同异步任务配置不同线程池 这样异步调用的api接口不会互相影响 可以配置多个线程池
   *
   * @return
   */
  @Bean
  public Executor loggerTaskExecutor() {
    int coreSize = Runtime.getRuntime().availableProcessors() * 2;
    log.info("loggerTaskExecutor.coreSize:{}", coreSize);
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(2);
    taskExecutor.setMaxPoolSize(5);
    taskExecutor.setQueueCapacity(2000);
    taskExecutor.setKeepAliveSeconds(60);
    taskExecutor.setThreadNamePrefix("X-Async-task-");
    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    taskExecutor.initialize();
    return taskExecutor;
    // return new ThreadPoolExecutor(coreSize, 100, 0L, TimeUnit.MILLISECONDS, new
    // LinkedBlockingQueue<>());
  }

  /** 1. 不同异步任务配置不同线程池 这样异步调用的api接口不会互相影响 可以配置多个线程池
   *  2. 创建异步任务，并指定要使用的线程池名称 @Async("taskExecutor1")
   * @Bean
   * public Executor taskExecutor1() {
   * ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
   * executor.setCorePoolSize(2);
   * executor.setMaxPoolSize(2);
   * executor.setQueueCapacity(10);
   * executor.setKeepAliveSeconds(60);
   * executor.setThreadNamePrefix("executor-1-");
   * executor.setRejectedExecutionHandler(new
   * ThreadPoolExecutor.CallerRunsPolicy());
   * return executor;
   * }
   *
   * @Bean
   * public Executor taskExecutor2() {
   * ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
   * executor.setCorePoolSize(2);
   * executor.setMaxPoolSize(2);
   * executor.setQueueCapacity(10);
   * executor.setKeepAliveSeconds(60);
   * executor.setThreadNamePrefix("executor-2-");
   * executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
   * return executor; }
   */
}
