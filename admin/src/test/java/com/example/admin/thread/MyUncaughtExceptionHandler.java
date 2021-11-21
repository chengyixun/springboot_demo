package com.example.admin.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MyUncaughtExceptionHandler @Author: amy @Description:
 * MyUncaughtExceptionHandler @Date: 2021/9/28 @Version: 1.0
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
  @Override
  public void uncaughtException(Thread t, Throwable e) {
    log.info(">> Thread：{},status:{},error:{}", t.getName(), t.getState(), e);
  }
}
