package com.example.admin.entity;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-16 15:13
 * @Description:
 * @Modified By:
 */
@Data
public class AccessLog {

	/**
	 * id
	 */
	private String id;

	/**
	 * 请求者ip
	 */
	private String ip;

	/**
	 * 请求者url地址
	 */
	private String url;

	/**
	 * 请求类
	 */
	private String targetClass;

	/**
	 * 请求方法
	 */
	private String targetMethod;

	/**
	 * Http请求的方法 GET，POST
	 */
	private String httpMethod;

	/**
	 * 异常信息
	 */
	private String Exception;

	/**
	 * 请求
	 */
	private String requestBody;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 动作
	 */
	private String action;

	/**
	 * 操作描述
	 */
	private String description;

	/**
	 * 请求时间
	 */
	private Long requestTime;

	private int responseStatus;

	private String responseCode;

	/**
	 * 返回前端时间
	 */
	private Long responseTime;

	/**
	 * 请求-返回时间戳
	 */
	private Long cost;

}
