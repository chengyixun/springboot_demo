package com.example.admin.commons.aop;

import com.example.admin.commons.util.AopUtil;
import lombok.Builder;
import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.DigestUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-30 14:34
 * @Description:
 * @Modified By:
 */
@Data
@Builder
public class MethodInterceptorHolder {

	public static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
	private String id;
	private Method method;
	private Object target;
	private Map<String, Object> args;

	public MethodInterceptorHolder(String id, Method method, Object target, Map<String, Object> args) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(id);
		Objects.requireNonNull(method);
		Objects.requireNonNull(target);
		Objects.requireNonNull(args);
		this.id = id;
		this.method = method;
		this.target = target;
		this.args = args;
	}

	public static MethodInterceptorHolder create(MethodInvocation invocation) {
		String id = DigestUtils.md5DigestAsHex(String.valueOf(invocation.getMethod()).getBytes());
		String[] argNames = NAME_DISCOVERER.getParameterNames(invocation.getMethod());
		Object[] args = invocation.getArguments();
		Map<String, Object> argMap = new LinkedHashMap<>();
		int i = 0;
		for (int len = args.length; i < len; ++i) {
			argMap.put(argNames != null && argNames[i] != null ? argNames[i] : "arg" + i, args[i]);
		}
		return new MethodInterceptorHolder(id, invocation.getMethod(), invocation.getThis(), argMap);

	}

	public <T extends Annotation> T findMethodAnnotation(Class<T> annClass) {
		return AopUtil.findMethodAnnotation(this.target.getClass(), this.method, annClass);
	}

	public <T extends Annotation> T findClassAnnotation(Class<T> annClass) {
		return AopUtil.findAnnotation(this.target.getClass(), annClass);
	}

}
