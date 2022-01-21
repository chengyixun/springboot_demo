package com.example.admin;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wangyu
 * @Date: Created 2021-02-01 16:10
 * @Description: 计数器思想 但是会扛不住 临界点问题
 * @Modified By:
 */
@Slf4j
public class CounterDemo {

	private static long ts = System.currentTimeMillis();

	/**
	 * 限制为1s内限制在100个请求
	 */
	private static long limitCount = 100;

	/**
	 * 间隔1秒
	 */
	private static long interval = 1000;

	/**
	 * 请求数
	 */
	private static long reqCount = 0;

	public static boolean grant() {
		long now = System.currentTimeMillis();
		if (now < ts + interval) {
			if (reqCount < limitCount) {
				++reqCount;
				return true;
			} else {
				return false;
			}
		} else {
			ts = System.currentTimeMillis();
			reqCount = 0;
			return false;
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 500; i++) {
			new Thread(() -> {
				if (grant()) {
					log.info("当前线程:{}, 执行业务逻辑", Thread.currentThread().getName());
				} else {
					log.info("当前线程:{}, 限流", Thread.currentThread().getName());

				}

			}, "thread_" + i).start();

		}
	}
}
