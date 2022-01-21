package com.example.admin.entity.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-07 15:10
 * @Description: 响应结果
 * @Modified By:
 */
@Data
@Builder
public class ResponseMessage<T> implements Serializable {
	private static final long serialVersionUID = 8992436576262574064L;
	/**
	 * 错误消息
	 */
	private String message;

	/**
	 * 成功时响应内容
	 */
	private T result;

	/**
	 * 状态码
	 */
	private int status;

	/**
	 * 业务自定义状态码
	 */
	private String code;

	/**
	 * 响应内容的字段
	 */
	private LinkedHashSet<String> field;

	/**
	 * 时间戳
	 */
	private Long timestamp;

}
