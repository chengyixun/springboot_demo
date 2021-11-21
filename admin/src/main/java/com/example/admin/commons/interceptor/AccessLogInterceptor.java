package com.example.admin.commons.interceptor;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-06 15:18
 * @Description:
 * @Modified By:
 */
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {

    public AccessLogInterceptor() {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = RandomUtil.simpleUUID();
        log.info(String.format("Request[%s] URL:[%s],Protocol:[%s],Params:%s", uuid, request.getRequestURL(), request.getProtocol(), JSONObject.toJSONString(request.getParameterMap())));
        request.setAttribute("startTime", SystemClock.now());
        request.setAttribute("uuid", uuid);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long timeout = SystemClock.now() - (long) request.getAttribute("startTime");
        String uuid = (String) request.getAttribute("uuid");
        log.info(String.format("Response[%s][%s] Timeout:[%s ms], ResponseStatus:[%s],ResponseBodySize:[%s],Error:[%s]", uuid, request.getRequestURL(), timeout, response.getStatus(), response.getBufferSize(), ex != null ? ex.toString() : "null"));
    }
}
