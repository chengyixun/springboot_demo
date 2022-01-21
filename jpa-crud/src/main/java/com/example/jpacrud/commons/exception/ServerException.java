package com.example.jpacrud.commons.exception;

/**
 * @ClassName: ServiceException
 * @Author: amy
 * @Description: ServiceException
 * @Date: 2021/6/23
 * @Version: 1.0
 */
public class ServerException extends BaseException {

	public ServerException(String message, String errorCode) {
		super(message, errorCode);
	}

	public ServerException(Throwable cause, String message) {
		super(cause, message);
	}

	public ServerException(String message, Throwable cause, String errorCode) {
		super(message, cause, errorCode);
	}

}
