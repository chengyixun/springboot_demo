package com.example.jpamultitenancy.common.exception.handler;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ResponseError @Author: amy @Description: ResponseError
 *             实现返回固定格式嘛 @Date: 2021/6/23 @Version: 1.0
 */
@Data
public class ResponseError {

	private String errorCode;

	private String errorMessage;

	private JsonNode details;

	private Date timestamp;

	public ResponseError() {
		this.timestamp = new Date();
	}

	public static ResponseError error(String errorCode, String errorMessage) {
		ResponseError error = new ResponseError();
		error.setErrorCode(errorCode);
		error.setErrorMessage(errorMessage);
		return error;
	}

	/*
	 * public static ResponseError error(String errorCode,String
	 * errorMessage,JsonNode details){
	 *
	 * }
	 */
}
