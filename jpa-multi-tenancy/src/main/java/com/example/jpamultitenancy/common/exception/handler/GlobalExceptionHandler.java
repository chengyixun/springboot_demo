package com.example.jpamultitenancy.common.exception.handler;

import com.example.jpamultitenancy.common.exception.RestException;
import com.example.jpamultitenancy.common.util.JsonUtils;
import com.example.jpamultitenancy.common.util.ThrowableUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.example.jpamultitenancy.common.constant.Constants.CODE;
import static com.example.jpamultitenancy.common.constant.Constants.DETAILS;
import static com.example.jpamultitenancy.common.constant.Constants.ERROR;
import static com.example.jpamultitenancy.common.constant.Constants.MESSAGE;
import static com.example.jpamultitenancy.common.constant.Constants.SUCCESS;

/**
 * @ClassName: GlobalExceptionHandler @Author: amy @Description:
 *             GlobalExceptionHandler 全局异常处理类 @Date: 2021/6/16 @Version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	/**
	 * 处理 REST请求 异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestException.class)
	public ResponseEntity<JsonNode> handleRestException(RestException ex) {
		ObjectNode result = JsonUtils.object().put(SUCCESS, false);
		ObjectNode error = JsonUtils.object().put(CODE, ex.status().value());
		result.set(ERROR, error);

		if (StringUtils.isNotEmpty(ex.getMessage())) {
			error.put(MESSAGE, getMessage(ex.getMessage()));
		}
		if (Objects.nonNull(ex.getDetails())) {
			error.set(DETAILS, ex.getDetails());
		}
		return ResponseEntity.status(ex.status()).body(result);
	}

	/** 处理所有接口数据验证异常 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JsonNode handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		// 打印堆栈信息
		log.error(ThrowableUtils.getStackTrace(ex));

		ObjectNode result = JsonUtils.object().put(SUCCESS, false);
		ObjectNode error = JsonUtils.object().put(CODE, HttpStatus.BAD_REQUEST.value());
		result.set(ERROR, error);

		ObjectNode details = JsonUtils.object();
		ex.getBindingResult().getAllErrors().forEach(err -> {
			String fieldName = ((FieldError) err).getField();
			String defaultMessage = err.getDefaultMessage();
			details.put(fieldName, defaultMessage);
		});
		result.set(DETAILS, details);
		return result;
	}

	/**
	 * 配置国际化翻译
	 *
	 * @param message
	 * @return
	 */
	public String getMessage(String message) {
		String value = StringUtils.EMPTY;
		try {
			value = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			return message;
		}
		return value;
	}

	/**
	 * 处理未知异常 有点问题
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ApiError> handleException(Throwable e) {
		// 打印堆栈信息
		log.error(e.getMessage());
		return buildResponseEntity(ApiError.error(e.getMessage()));
	}

	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
	}
}
