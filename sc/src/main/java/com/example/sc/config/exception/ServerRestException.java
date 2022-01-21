package com.example.sc.config.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @ClassName: ServerRestException
 * @Author: amy
 * @Description: ServerRestException
 * @Date: 2021/6/23
 * @Version: 1.0
 */
public class ServerRestException extends RestException {

	public ServerRestException() {
	}

	public ServerRestException(String message) {
		super(message);
	}

	public ServerRestException(ObjectNode details) {
		super.details = details;
	}

	public ServerRestException(String message, ObjectNode details) {
		super(message);
		super.details = details;
	}

	@Override
	public HttpStatus status() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
