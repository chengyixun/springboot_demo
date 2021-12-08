package com.example.admin.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TransmittableThreadLocalTest @Author: amy @Description:
 * TransmittableThreadLocalTest @Date: 2021/12/8 @Version: 1.0
 */
@Slf4j
public class TransmittableThreadLocalTest implements Runnable {

  private static TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();

  private static ThreadPoolExecutor tpe =
      new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

  public static void main(String[] args) {
    context.set("value-set-in-parent");
    TransmittableThreadLocalTest ttL = new TransmittableThreadLocalTest();
    TtlRunnable ttlRunnable = TtlRunnable.get(ttL);
    tpe.submit(ttlRunnable);
    log.info("T:{}, context:{}", Thread.currentThread().getName(), context.get());
    tpe.shutdown();

  }

  @Override
  public void run() {
    try {
      Thread.sleep(3*1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("T:{},before get context:{}", Thread.currentThread().getName(), context.get());
    context.set("value-set-in-child");
    log.info("T:{},after set context:{}", Thread.currentThread().getName(), context.get());
  }
}
