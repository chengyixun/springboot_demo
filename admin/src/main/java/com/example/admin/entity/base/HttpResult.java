package com.example.admin.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * {@link HttpResult}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-22
 */
@Data
public class HttpResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg;

	private T data;

	public HttpResult(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public HttpResult() {
		this.code = 200;
		this.msg = "SUCCESS";
	}

	public HttpResult(T data) {
		this();
		this.data = data;
	}

	public static <T> HttpResult<T> success(String msg, T body) {
		return new HttpResult<>(200, msg, body);
	}

	public static <T> HttpResult<T> success(T body) {
		return success("success", body);
	}

	public static <T> HttpResult<T> fail(int code, String msg) {
		return new HttpResult<>(code, msg, null);
	}

	public static <T> HttpResult<T> fail(String msg) {
		return fail(500, msg);
	}
}
