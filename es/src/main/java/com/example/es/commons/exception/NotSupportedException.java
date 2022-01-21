package com.example.es.commons.exception;

import com.example.base.commons.exception.CommonException;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 11:24
 * @Description:
 * @Modified By:
 */
public class NotSupportedException extends CommonException {

	public NotSupportedException() {
		super("Error occurred in bean mapper");
	}

	public NotSupportedException(String message) {
		super(message);
	}
}
