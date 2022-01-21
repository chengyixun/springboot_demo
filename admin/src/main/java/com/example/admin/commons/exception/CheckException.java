package com.example.admin.commons.exception;

/**
 * {@link CheckException} 参数校验异常
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-22
 */
public class CheckException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 错误code
	 */
	private int errCode = 500;

	public CheckException(String message) {
		super(message);
	}

	public CheckException(String message, int code) {
		super(message);
		this.errCode = code;
	}

	public CheckException(Throwable cause) {
		super(cause);
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getErrCode() {
		return errCode;
	}
}
