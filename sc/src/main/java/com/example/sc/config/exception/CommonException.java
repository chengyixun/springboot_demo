package com.example.sc.config.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 编码
	 */
	private String code;

	public CommonException(String message) {
		this(500, message);
	}

	public CommonException(int status, String message) {
		this(status, null, message);
	}

	public CommonException(int status, String code, String message) {
		super(message);
		this.status = 500;
		this.status = status;
		this.code = code;
	}

	public CommonException(Throwable cause) {
		super(cause);
		this.status = 500;
	}

	public CommonException(Throwable cause, int status) {
		this(cause, status, null);
	}

	public CommonException(Throwable cause, int status, String message) {
		this(cause, status, null, message);
	}

	public CommonException(Throwable cause, int status, String code, String message) {
		super(message, cause);
		this.status = 500;
		this.status = status;
		this.code = code;
	}

}
