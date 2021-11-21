package com.example.jpamultitenancy.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: TenantInterceptor @Author: amy @Description: TenantInterceptor @Date:
 * 2021/7/4 @Version: 1.0
 */
@Slf4j
public class TenantInterceptor implements HandlerInterceptor {

  private static final String TENANT_KEY = "tenant";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String tenant = request.getParameter(TENANT_KEY);
    if (StringUtils.isEmpty(tenant)) {
      // response.sendRedirect("/login.html");
      return false;
    }
    TenantContextHolder.setTenant(tenant);
    return true;
  }
}
