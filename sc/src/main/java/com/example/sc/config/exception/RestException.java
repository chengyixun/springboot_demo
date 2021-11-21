package com.example.sc.config.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: RestException
 * @Author: amy
 * @Description: RestException
 * @Date: 2021/6/23
 * @Version: 1.0
 */
public abstract class RestException extends RuntimeException {

	@Getter
	@Setter
	protected ObjectNode details;

	public RestException() {
	}

	public RestException(String message) {
		super(message);
	}

	public abstract HttpStatus status();
}
