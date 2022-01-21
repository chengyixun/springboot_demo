package com.example.admin.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TestThreadLocal @Author: amy @Description: TestThreadLocal @Date:
 *             2021/12/7 @Version: 1.0
 *
 *             <p>
 *             验证ThreadLocal 线程间隔离 ，
 *
 *             <p>
 *             main线程 设置的值，
 *
 *             <p>
 *             T2线程 获取不到
 */
@Slf4j
public class TestThreadLocal implements Runnable {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
		log.info("T:{},begin set value:主线程", Thread.currentThread().getName());
		threadLocal.set("主线程");
		log.info("T:{},after get value:{}", Thread.currentThread().getName(), threadLocal.get());
		TestThreadLocal testThreadLocal = new TestThreadLocal();
		new Thread(() -> testThreadLocal.run(), "T2").start();
	}

	@Override
	public void run() {
		log.info("T:{},before set value, get current local value:{}", Thread.currentThread().getName(),
				threadLocal.get());
		log.info("T:{},begin set value:子线程", Thread.currentThread().getName());
		threadLocal.set("子线程");
		log.info("T:{},after get value:{}", Thread.currentThread().getName(), threadLocal.get());
	}
}
