package com.example.admin.commons.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-04 15:44
 * @Description:
 * @Modified By:
 */
public class WebUtil {


    static final String[] IP_HEADERS = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};


    public WebUtil() {

    }

    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;

    }

    public static String getIpAddr(HttpServletRequest request){
        String[] var1 = IP_HEADERS;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String ipHeader = var1[var3];
            String ip = request.getHeader(ipHeader);
            if (!StringUtils.isEmpty(ip) && !ip.contains("unknown")) {
                return ip;
            }
        }

        return request.getRemoteAddr();

    }
}
