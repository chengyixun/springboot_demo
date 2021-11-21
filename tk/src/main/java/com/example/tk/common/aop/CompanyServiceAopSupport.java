package com.example.tk.common.aop;

import com.example.tk.common.annotation.AccessLogger;
import com.example.tk.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CompanyServiceAopSupport
 * @Author: amy
 * @Description: CompanyServiceAopSupport
 * @Date: 2021/7/28
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class CompanyServiceAopSupport {

	@Around(value = "@annotation(logger)")
	public Object doAroundAdvice(ProceedingJoinPoint joinPoint, AccessLogger logger) throws Throwable {
		log.info("0. @Around start:{}", logger.value());
		log.info("1.arg:{}", joinPoint.getArgs());
		Object[] args = joinPoint.getArgs();

		CompanyVO companyVO = new CompanyVO();
		for (int i = 0, length = args.length; i < length; i++) {
			Object arg = args[i];
			if (arg instanceof CompanyVO) {
				companyVO = (CompanyVO) arg;
				// 拿到入参
			}
		}
		log.info("2.before proceed:{}", companyVO);
		Object proceed = joinPoint.proceed();

		// after 处理的功能要catch住
		try {
			log.info(" after testAroundAfterFun");
			testAroundAfterFun();
		} catch (Exception e) {
			log.error("after error:{}", e.getMessage());
		}

		log.info("3.after proceed:{}", proceed);
		return proceed;
	}

	public void testAroundAfterFun() {
		int a = 1, b = 0;
		int c = a / b;
		System.out.println(c);
	}

	/**
	 * 测试场景： 1. success
	 *
	 *
	 * 2. error
	 */

}
