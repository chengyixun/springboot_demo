package com.example.jpamultitenancy.common.interceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link CurrentUser} current thread user data
 *
 * @author Liyaohui
 * @date 6/20/21
 */
@Slf4j
public class CurrentUser {

	private CurrentUser() {
	}

	private static final ThreadLocal<CurrentLoginUser> CONTEXT = new ThreadLocal<>();

	public static void upload() {
		CONTEXT.remove();
	}

	public static void set(CurrentLoginUser currentLoginUser) {
		upload();
		CONTEXT.set(currentLoginUser);
	}

	public static CurrentLoginUser get() {
		return CONTEXT.get();
	}
}
