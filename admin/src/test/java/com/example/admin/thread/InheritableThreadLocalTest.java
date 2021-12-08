package com.example.admin.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: InheritableThreadLocalTest @Author: amy @Description:
 * InheritableThreadLocalTest @Date: 2021/12/8 @Version: 1.0
 *
 * <p>InheritableThreadLocal 线程共享了 其他子线程再获取的时候 值会变掉 demo演示 error
 */
@Slf4j
public class InheritableThreadLocalTest implements Runnable {

  private static InheritableThreadLocal<String> inheritableThreadLocal =
      new InheritableThreadLocal<>();

  /** 线程池 复用 InheritableThreadLocal 就会出现问题 */
  private static ThreadPoolExecutor tpe =
      new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

  public static void main(String[] args) throws InterruptedException {
    log.info("主线程启动");
    inheritableThreadLocal.set("主线程第一次赋值");
    log.info("主线程:{},主线程设置后获取值：{}", Thread.currentThread().getName(), inheritableThreadLocal.get());

    tpe.submit(new InheritableThreadLocalTest());

    log.info("主线程:{},sleep 2 s", Thread.currentThread().getName());
    Thread.sleep(2 * 1000);

    inheritableThreadLocal.set("主线程第二次赋值");
    tpe.submit(new InheritableThreadLocalTest());

    tpe.shutdown();
  }

  @Override
  public void run() {
    log.info("子线程:{},子线程获取值:{}", Thread.currentThread().getName(), inheritableThreadLocal.get());
  }
}

/**
 * 主线程启动 主线程:main,
 * 主线程设置后获取值：主线程第一次赋值
 * 主线程:main,sleep 2 s
 * 子线程:pool-1-thread-1,子线程获取值:主线程第一次赋值
 * 子线程:pool-1-thread-1,子线程获取值:主线程第一次赋值
 */
