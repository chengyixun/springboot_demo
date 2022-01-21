package com.example.admin;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangyu
 * @Date: Created 2021-02-01 17:07
 * @Description:
 * @Modified By:
 */
public class LimitDemo {

	public static ConcurrentHashMap<String, RateLimiter> limiterConcurrentHashMap = new ConcurrentHashMap<>();

	static {

	}

}
