package com.example.admin.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: InheritableThreadLocalTest2 @Author: amy @Description:
 * InheritableThreadLocalTest2 @Date: 2021/12/9 @Version: 1.0
 *
 * <p>在子线程中出现出现了线程本地变量混乱的现象，在全链路跟踪与压测出现这种情况是致命的。
 */
@Slf4j
public class InheritableThreadLocalTest2 {

  /** 模拟tomcat线程池 */
  private static ExecutorService tomcatExecutors = Executors.newFixedThreadPool(10);

  /** 业务线程池，默认Control中异步任务执行线程池 */
  private static ExecutorService businessExecutors = Executors.newFixedThreadPool(5);
  /** 上下文 */
  private static InheritableThreadLocal<Integer> context_in = new InheritableThreadLocal<>();

  public static void main(String[] args) {
    /**
     * 模拟10个请求， 每个请求执行ControlThread的逻辑，
     *
     * <p>其具体实现就是：先输出当前线程名称，设置本地环境变量，并将父线程名称传递给 子线程，子线程中尝试获取父线程设置的环境变量
     */
    for (int i = 1; i <= 10; i++) {
      tomcatExecutors.submit(new ControlThread(i));
    }

    // 关闭线程池
    try {
      Thread.sleep(1 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    businessExecutors.shutdown();
    tomcatExecutors.shutdown();
  }

  /** 模拟control任务 */
  static class ControlThread implements Runnable {
    private int i;

    public ControlThread(int i) {
      this.i = i;
    }

    @Override
    public void run() {
      log.info("当前线程:{},num:{}", Thread.currentThread().getName(), i);
      context_in.set(i);
      // 使用线程池异步处理任务
      businessExecutors.submit(new BusinessTask(Thread.currentThread().getName()));
    }
  }

  /** 模拟业务任务，主要是 模拟在Control控制层，提交任务到线程池执行 */
  static class BusinessTask implements Runnable {
    private String parentThreadName;

    public BusinessTask(String parentThreadName) {
      this.parentThreadName = parentThreadName;
    }

    @Override
    public void run() {
      /** 如果与上面的能对应上来，则说明正确，否则失败 */
      log.info("parentThreadName:{},num:{}", parentThreadName, context_in.get());
    }
  }
}
