package com.example.jpamultitenancy.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.jpamultitenancy.common.exception.ServerException;
import com.example.jpamultitenancy.dto.LoginUser;

/**
 * @ClassName: SecurityUtils @Author: amy @Description: SecurityUtils @Date: 2021/7/6 @Version: 1.0
 */
public class SecurityUtils {

  public static String getUsername() {
    try {
      return getLoginUser().getUsername();
    } catch (Exception e) {
      throw new ServerException("获取用户账户异常", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
    }
  }

  /**
   * 获取 用户账户
   *
   * @return
   */
  public static LoginUser getLoginUser() {
    try {
      return (LoginUser) getAuthentication().getPrincipal();
    } catch (Exception e) {
      throw new ServerException("获取用户信息异常", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
    }
  }

  /**
   * 获取 Authentication
   *
   * @return
   */
  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
