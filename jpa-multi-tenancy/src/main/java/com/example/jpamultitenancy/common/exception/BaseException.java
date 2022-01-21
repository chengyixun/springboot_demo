package com.example.jpamultitenancy.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: BaseException @Author: amy @Description: BaseException @Date:
 *             2021/6/16 @Version: 1.0
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 486200447173026794L;

	@Getter
	@Setter
	private String errorCode;

	public BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BaseException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}
}
