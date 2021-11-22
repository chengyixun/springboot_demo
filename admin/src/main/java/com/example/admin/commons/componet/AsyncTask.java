package com.example.admin.commons.componet;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * {@link AsyncTask}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-24
 */
@Slf4j
@Component
public class AsyncTask {

  public static Random random = new Random();

  @Async
  public void dealNoReturnTask() {
    log.info(">>>Thread :{} ,deal NO return task start", Thread.currentThread().getName());
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info(
        ">>>Thread :{} ,deal NO return task end ,time:{}",
        Thread.currentThread().getName(),
        System.currentTimeMillis());
  }

  @Async
  public Future<String> dealHaveReturnTask() {
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    JSONObject result = new JSONObject();
    result.put("thread", Thread.currentThread().getName());
    result.put("time", System.currentTimeMillis());
    return new AsyncResult<>(result.toJSONString());
  }

  @Async
  public CompletableFuture<String> doTaskOne() throws Exception {
    log.info("开始做任务一");
    long start = System.currentTimeMillis();
    Thread.sleep(random.nextInt(10 * 1000));
    long end = System.currentTimeMillis();
    log.info("完成任务一，Thread:{},耗时:{} ms", Thread.currentThread().getName(), (end - start));
    return CompletableFuture.completedFuture("任务一完成");
  }

  @Async
  public CompletableFuture<String> doTaskTwo() throws Exception {
    log.info("开始做任务二");
    long start = System.currentTimeMillis();
    Thread.sleep(random.nextInt(10 * 1000));
    long end = System.currentTimeMillis();
    log.info("完成任务二，Thread:{},耗时:{} ms", Thread.currentThread().getName(), (end - start));
    return CompletableFuture.completedFuture("任务二完成");
  }

  @Async
  public CompletableFuture<String> doTaskThree() throws Exception {
    log.info("开始做任务三");
    long start = System.currentTimeMillis();
    Thread.sleep(random.nextInt(10 * 1000));
    long end = System.currentTimeMillis();
    log.info("完成任务三，Thread:{},耗时:{} ms", Thread.currentThread().getName(), (end - start));
    return CompletableFuture.completedFuture("任务三完成");
  }
}
