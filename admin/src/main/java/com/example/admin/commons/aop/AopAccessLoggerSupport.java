package com.example.admin.commons.aop;

import com.example.admin.commons.annotation.AccessLogger;
import com.example.admin.commons.event.AccessLoggerEvent;
import com.example.admin.commons.util.AopUtil;
import com.example.admin.commons.util.WebUtil;
import com.example.admin.entity.AccessLoggerInfo;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-29 17:01
 * @Description:
 * @Modified By:
 */
@Slf4j
public class AopAccessLoggerSupport extends StaticMethodMatcherPointcutAdvisor
		implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public AopAccessLoggerSupport() {
		this.setAdvice((MethodInterceptor) methodInvocation -> {
			MethodInterceptorHolder holder = MethodInterceptorHolder.create(methodInvocation);
			AccessLoggerInfo info = this.createLog(holder);
			Object response;
			try {
				response = methodInvocation.proceed();
				info.setResponse(response);
				info.setResponseTime(System.currentTimeMillis());
				/** auth 暂时不加 */
			} catch (Exception e) {
				info.setException(e);
				throw e;
			} finally {
				this.applicationEventPublisher.publishEvent(new AccessLoggerEvent(info));
			}
			return response;
		});

	}

	/**
	 * fix AccessLoggerInfo
	 *
	 * @param holder
	 * @return
	 */
	public AccessLoggerInfo createLog(MethodInterceptorHolder holder) {
		AccessLoggerInfo info = new AccessLoggerInfo();
		info.setRequestTime(System.currentTimeMillis());
		// 日志log
		AccessLogger methodAnn = holder.findMethodAnnotation(AccessLogger.class);
		AccessLogger classAnn = holder.findClassAnnotation(AccessLogger.class);
		String describe = Stream.of(classAnn, methodAnn).filter(Objects::nonNull).map(AccessLogger::describe)
				.flatMap(Stream::of).reduce((c, s) -> {
					return c.concat("\n").concat(s);
				}).orElse("");
		info.setAction(methodAnn == null ? holder.getMethod().getName() : methodAnn.value());
		info.setModule(classAnn == null ? holder.getTarget().getClass().getSimpleName() : classAnn.value());
		info.setDescribe(describe);
		info.setParameters(holder.getArgs());
		info.setTarget(holder.getTarget().getClass());
		info.setMethod(holder.getMethod());

		HttpServletRequest request = WebUtil.getHttpServletRequest();
		if (null != request) {
			info.setHttpHeaders(WebUtil.getHeaders(request));
			info.setIp(StringUtils.replace(WebUtil.getIpAddr(request), "0:0:0:0:0:0:0:1", "127.0.0.1"));
			info.setHttpMethod(request.getMethod());
			info.setUrl(request.getRequestURL().toString());
		}

		return info;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public boolean matches(Method method, Class<?> aClass) {
		AccessLogger ann = (AccessLogger) AopUtil.findAnnotation(aClass, method, AccessLogger.class);
		return null != ann && !ann.ignore();
	}
}
