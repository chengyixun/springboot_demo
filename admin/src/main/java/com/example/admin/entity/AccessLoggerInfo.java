package com.example.admin.entity;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/** @Author: wangyu @Date: Created 2020-12-29 16:42 @Description: @Modified By: */
@Data
public class AccessLoggerInfo {

  private String id = RandomUtil.randomUUID();

  private String action;

  private String module;

  private String describe;

  private Method method;

  private Class target;

  private Map<String, Object> parameters;

  private String ip;

  private String url;

  private Map<String, String> httpHeaders;

  private String httpMethod;

  private Object response;

  private long requestTime;

  private long responseTime;

  private Throwable exception;

  // 暂时不做处理
  private AuthUser authUser;
}
