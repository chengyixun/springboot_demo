package com.example.mybatisplus.commons.intercepter;

import com.example.mybatisplus.commons.context.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: TenantInterceptor @Author: amy @Description: TenantInterceptor @Date:
 * 2021/10/10 @Version: 1.0
 */
@Slf4j
public class TenantInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String tenant = request.getParameter("tenant");
    if (StringUtils.isNotEmpty(tenant)) {
      DynamicDataSourceContextHolder.setDataSourceKey(tenant);
      log.info("拦截器里头截获租户tenant为:{}", tenant);
      return true;
    }
    return false;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    DynamicDataSourceContextHolder.clearDataSourceKey();
  }
}
