package com.example.jpacrud.commons.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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
