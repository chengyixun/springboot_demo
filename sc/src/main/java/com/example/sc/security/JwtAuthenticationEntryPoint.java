package com.example.sc.security;

import com.example.sc.config.Utils.JsonUtils;
import com.example.sc.config.Utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName: JwtAuthenticationEntryPoint
 * @Author: amy
 * @Description: JwtAuthenticationEntryPoint
 * @Date: 2021/7/7
 * @Version: 1.0
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED.value();
        String message = Objects.isNull(e) ? "Unauthorized" : e.getMessage();
        log.error("请求访问:{},认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
        ServletUtils.renderString(httpServletResponse, JsonUtils.error(code, message).toString());
    }
}
