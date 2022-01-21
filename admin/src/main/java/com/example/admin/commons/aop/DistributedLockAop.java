package com.example.admin.commons.aop;

import com.example.admin.commons.annotation.DistributedLock;
import com.example.admin.service.RedisDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: wangyu @Date: Created 2020-12-22 10:37 @Description: 1、获取锁 2、加锁
 *          3、释放锁 @Modified By:
 */
@Slf4j
@Component
@Aspect
public class DistributedLockAop {

	@Autowired
	private RedisDistributedLockService lockService;

	@Around(value = "@annotation(lock)")
	public Object doAroundAdvice(ProceedingJoinPoint joinPoint, DistributedLock lock) {
		// 1、获取锁
		String key = getKey(joinPoint, lock);
		log.info(Thread.currentThread().getName() + "锁:{}", key);
		boolean success = false;
		try {
			// 2、加锁
			success = lockService.lock(key, lock.expire(), lock.timeout());

			if (success) {
				log.info(Thread.currentThread().getName() + " 加锁成功！！");
				return joinPoint.proceed();
			}
			log.info(Thread.currentThread().getName() + " 加锁失败！！");
			return null;

		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		} finally {
			// 3、释放锁
			if (success) {
				boolean release = lockService.release(key);
				log.info(Thread.currentThread().getName() + " 锁释放结果:{}", release);
			}
		}
	}

	/**
	 * 默认包名+方法名
	 *
	 * @param joinPoint
	 * @param lock
	 * @return
	 */
	private String getKey(ProceedingJoinPoint joinPoint, DistributedLock lock) {
		if (StringUtils.isEmpty(lock.key())) {
			return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		}
		return lock.key();
	}
}
