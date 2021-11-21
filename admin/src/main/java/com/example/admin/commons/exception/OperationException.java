package com.example.admin.commons.exception;

/**
 * {@link OperationException} 业务操作异常
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-22
 */
public class OperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误code
     */
    private int errCode = 500;

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, int code) {
        super(message);
        this.errCode = code;
    }

    public OperationException(Throwable cause) {
        super(cause);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }


    public int getErrCode() {
        return errCode;
    }
}
