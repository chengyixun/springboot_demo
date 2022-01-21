package com.example.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.example.admin.commons.annotation.DistributedLock;
import com.example.admin.service.RedisDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-22 10:36
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
public class RedisDistributedLockServiceImpl implements RedisDistributedLockService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final String PREFIX = "lock:";

	private ThreadLocal<String> threadLocal = new ThreadLocal<>();

	@Override
	public boolean lock(String key, Long expireTime, Long requireTimeOut) {
		String lockName = PREFIX + key;
		// 判断可重入
		String originValue = threadLocal.get();
		if (StringUtils.isNotEmpty(originValue) && isReentrantLock(lockName, originValue)) {
			return true;
		}
		String value = UUID.randomUUID().toString();
		Long end = System.currentTimeMillis() + requireTimeOut;
		// 在设置的请求过期时间内 完成
		while (System.currentTimeMillis() < end) {
			Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockName, value);
			if (ifAbsent) {
				redisTemplate.expire(lockName, expireTime, TimeUnit.SECONDS);
				threadLocal.set(value);
				return true;
			}
			// ifAbsent为false，说明锁已经存在了，此刻sleep一会，直到跳出while
			ThreadUtil.sleep(1, TimeUnit.SECONDS);
		}
		return false;
	}

	/**
	 * 释放锁
	 *
	 * @param key
	 * @return
	 */
	@Override
	public boolean release(String key) {
		String keyName = PREFIX + key;
		String originValue = threadLocal.get();
		if (StringUtils.isEmpty(originValue)) {
			return false;
		}
		Boolean aBoolean = redisTemplate.hasKey(keyName);
		if (aBoolean) {
			redisTemplate.delete(keyName);
			return true;
		}
		return false;
	}

	@Override
	@DistributedLock
	public void testLog() {
		log.info(">>>>testLog!! :{}", DateUtil.date());

	}

	private boolean isReentrantLock(String key, String originValue) {
		String v = redisTemplate.opsForValue().get(key);
		return v != null && v.equals(originValue);
	}
}
