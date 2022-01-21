package com.example.admin.commons.config;

import com.example.admin.commons.exception.CheckException;
import com.example.admin.commons.exception.OperationException;
import com.example.admin.entity.base.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@link GlobalExceptionHandler} 全局异常处理类
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-22
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CheckException.class)
	@ResponseStatus(HttpStatus.OK)
	public HttpResult businessExceptionHandler(CheckException ce) {
		String message = ce.getMessage();
		int code = ce.getErrCode();
		log.info("参数校验错误", message);
		return HttpResult.fail(code, message);
	}

	@ExceptionHandler(OperationException.class)
	@ResponseStatus(HttpStatus.OK)
	public HttpResult businessExceptionHandler(OperationException e) {
		String message = e.getMessage();
		int code = e.getErrCode();
		log.error("业务操作错误", message);
		return HttpResult.fail(code, message);
	}

	@ExceptionHandler(Exception.class)
	public HttpResult exceptionHandler(Exception e) {
		log.error("系统异常", e.getLocalizedMessage());
		e.printStackTrace();
		return HttpResult.fail(e.getLocalizedMessage());
	}

}
