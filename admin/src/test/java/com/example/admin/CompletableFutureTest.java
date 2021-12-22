package com.example.admin;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @ClassName: CompletableFutureTest @Author: amy @Description: CompletableFutureTest @Date:
 * 2021/12/14 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AdminApplication.class)
@Slf4j
public class CompletableFutureTest {

  @Resource private Executor loggerTaskExecutor;

  @Test
  public void testSupplyAsync(){
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "", loggerTaskExecutor)
            .thenRunAsync(() -> log.info("OK"), loggerTaskExecutor);
    log.info("result:{}",CompletableFuture.allOf(future));

  }
  @Test
  public void userTest1() {
    List<User> users = Lists.newArrayList();
    users.add(User.builder().name("小明").age(10).build());
    users.add(User.builder().name("小红").age(11).build());
    users.add(User.builder().name("小张").age(15).build());

    CompletableFuture[] futures =
        users.stream().map(u -> disposeUser(u)).toArray(CompletableFuture[]::new);

    // 等待所有任务执行完
    CompletableFuture.allOf(futures).join();

    for (CompletableFuture future : futures) {
      try {
        log.info("result:{}", future.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public CompletableFuture<Integer> disposeUser(User user) {

    return CompletableFuture.supplyAsync(
            new Supplier<Integer>() {
              @Override
              public Integer get() {
                log.info("Thread Name:{}", Thread.currentThread().getName());
                try {
                  /**
                   * 这次返回耗费了5秒左右， 处理的最慢的那个线程决定了最终的返回时常，这也符合咱们的预期。
                   *
                   * <p>大家在处理集合数据并且每一条的处理都比较耗时的话，可以考虑这个手法。
                   */
                  if (user.getName().equals("小明")) {
                    TimeUnit.SECONDS.sleep(5);
                  } else {
                    TimeUnit.SECONDS.sleep(2);
                  }
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                return user.getAge() + 10;
              }
            },
            loggerTaskExecutor)
        .handleAsync(
            new BiFunction<Integer, Throwable, Integer>() {
              @Override
              public Integer apply(Integer param, Throwable throwable) {
                int result = param;
                if (throwable == null) {
                  result = param * 2;
                } else {
                  log.info("throwable is:{}", throwable.getMessage());
                }
                return result;
              }
            });
  }


}
