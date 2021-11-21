package com.example.jpamultitenancy.security;

import com.example.jpamultitenancy.common.util.JsonUtils;
import com.example.jpamultitenancy.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @ClassName: AuthenticationEntryPointImpl @Author: amy @Description: 认证失败处理类 返回未授权 @Date:
 * 2021/7/6 @Version: 1.0
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e) {
    int code = HttpStatus.UNAUTHORIZED.value();
    String message = Objects.isNull(e) ? "Unauthorized" : e.getMessage();
    log.error("请求访问:{},认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
    ServletUtils.renderString(httpServletResponse, JsonUtils.error(code, message).toString());
  }
}
