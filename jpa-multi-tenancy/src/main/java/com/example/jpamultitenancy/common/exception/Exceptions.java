package com.example.jpamultitenancy.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName: Exceptions @Author: amy @Description: Exceptions @Date:
 *             2021/6/23 @Version: 1.0
 */
public class Exceptions {

	public static final String ERROR_CODE_KEY = "errorCode";
	public static final String ERROR_MESSAGE_KEY = "errorMessage";

	public static class Code {
		public static final String IO_ERROR = "IO_ERROR";
		public static final String E0501 = "E0501";
	}

	public static class Message {
		public static final String DE0310 = "failed to parse data to json";
		public static final String E0501 = "failed to process new task";
	}

	public static ServerException of(int statusCode) {
		return of(HttpStatus.valueOf(statusCode));
	}

	public static ServerException of(HttpStatus status) {
		return new ServerException(status.getReasonPhrase(), "" + status.value());
	}

	public static ServerException NOT_FOUND() {
		return of(HttpStatus.NOT_FOUND);
	}
}
