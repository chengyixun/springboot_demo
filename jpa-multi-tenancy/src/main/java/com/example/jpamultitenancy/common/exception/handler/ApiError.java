package com.example.jpamultitenancy.common.exception.handler;

import com.example.jpamultitenancy.common.util.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName: ApiError @Author: amy @Description: ApiError @Date:
 *             2021/6/16 @Version: 1.0
 */
@Data
public class ApiError {

	private Integer status = 400;
	private String message;

	private Date timestamp;

	public ApiError() {
		this.timestamp = DateUtils.toDate(LocalDateTime.now());
	}

	public static ApiError error(String message) {
		ApiError apiError = new ApiError();
		apiError.setMessage(message);
		return apiError;
	}

	public static ApiError error(Integer status, String message) {
		ApiError apiError = new ApiError();
		apiError.setStatus(status);
		apiError.setMessage(message);
		return apiError;
	}
}
