package com.example.admin.commons.listener;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.commons.event.AccessLoggerEvent;
import com.example.admin.entity.AccessLog;
import com.example.admin.entity.AccessLoggerInfo;
import com.example.admin.entity.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-05 17:48
 * @Description:
 * @Modified By:
 */
@Slf4j
@Component
public class AccessLogListener implements ApplicationListener<AccessLoggerEvent> {

	/*
	 * @Autowired private KafkaTemplate<String, String> kafkaTemplate;
	 */

	@Override
	@Async("loggerTaskExecutor")
	public void onApplicationEvent(AccessLoggerEvent event) {
		AccessLoggerInfo source = event.getSource();
		log.info(" temp info:{}", source);
		AccessLog accessLog = buildAccessLog(source);

		log.info(" accessLog:{}", accessLog);
		/*
		 * try { String json = JSONObject.toJSONString(accessLog);
		 * kafkaTemplate.send("access_log", json); } catch (Exception e) {
		 * log.error("accessLog send kafka error:{}", e.getMessage()); }
		 */

	}

	/*
	 * @KafkaListener(topics = "access_log") public void consumer(String message) {
	 * AccessLog accessLog = JSONObject.parseObject(message, AccessLog.class);
	 * //insert es }
	 */

	/**
	 * 组装accessLog字段
	 *
	 * @param info
	 * @return
	 */
	public AccessLog buildAccessLog(AccessLoggerInfo info) {
		AccessLog accessLog = new AccessLog();
		// user暂时不考虑
		accessLog.setIp(info.getIp());
		accessLog.setUrl(info.getUrl());
		accessLog.setTargetClass(info.getTarget().getSimpleName());
		accessLog.setTargetMethod(info.getMethod().getName());
		accessLog.setHttpMethod(info.getHttpMethod());
		accessLog.setAction(info.getAction());
		accessLog.setModule(info.getModule());
		if (null != info.getResponse() && info.getResponse() instanceof ResponseMessage) {
			ResponseMessage response = (ResponseMessage) info.getResponse();
			accessLog.setResponseStatus(response.getStatus());
			accessLog.setResponseCode(response.getCode());
		}
		accessLog.setRequestTime(info.getRequestTime());
		accessLog.setResponseTime(info.getResponseTime());
		accessLog.setCost(info.getResponseTime() - info.getRequestTime());
		if (null != info.getException()) {
			accessLog.setException(info.getException().getMessage());
		}
		return accessLog;
	}

}
