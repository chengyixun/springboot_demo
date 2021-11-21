package com.example.jpamultitenancy.common.config;

import com.example.jpamultitenancy.common.interceptor.TenantInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@link InterceptorConfig}
 *
 * @author Liyaohui
 * @date 6/20/21
 */
// @Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  /**
   * 1. 登陆 url 2.
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // login intercept
    registry
        .addInterceptor(new TenantInterceptor())
        // url scope
        .addPathPatterns("/**")
        // exclude paths /api/auth/signIn
        .excludePathPatterns("/api/csv");
  }
}
