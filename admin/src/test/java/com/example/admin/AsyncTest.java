package com.example.admin;

import com.example.admin.commons.componet.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * {@link AsyncTest}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdminApplication.class)
@Slf4j
public class AsyncTest {

	@Autowired
	private AsyncTask asyncTask;

	@Test
	public void test1() {
		log.info(">>>begin to deal other Task!1111111");
		asyncTask.dealNoReturnTask();
		log.info(">>>begin to deal other Task!222222");
		/*
		 * try { log.info(">>>begin to deal other Task!"); Thread.sleep(1000); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}

	@Test
	public void test2() throws Exception {
		Future<String> future = asyncTask.dealHaveReturnTask();
		log.info("begin to deal other Task!");
		while (true) {
			if (future.isCancelled()) {
				log.info("deal async task is Cancelled");
				break;
			}
			if (future.isDone()) {
				log.info("deal async task is Done");
				log.info("return result is " + future.get());
				break;
			}
			log.info("wait async task to end ...");
			Thread.sleep(1000);
		}
	}

	/**
	 * 三个任务 互不影响 并行 计算最后执行完成的总耗时
	 *
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		long start = System.currentTimeMillis();

		CompletableFuture<String> task1 = asyncTask.doTaskOne();
		CompletableFuture<String> task2 = asyncTask.doTaskTwo();
		CompletableFuture<String> task3 = asyncTask.doTaskThree();

		CompletableFuture.allOf(task1, task2, task3).join();

		long end = System.currentTimeMillis();

		log.info("任务全部完成，总耗时：" + (end - start) + "毫秒");
	}

	// TODO: 2021/12/6 need demo to test!!
	@Test
	public void testBlockQueue() {
		// BlockingQueue<String> queues
	}

	@Test
	public void testCompletableFuture() {
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				log.info("当前线程:{}", Thread.currentThread().getName());
			}
		});
	}
}
