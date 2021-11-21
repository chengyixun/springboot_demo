package com.example.jpamultitenancy.security;

import com.example.jpamultitenancy.dto.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

import static com.example.jpamultitenancy.common.constant.JWTConstants.SUPER_ADMIN;

/**
 * @ClassName: ElPermissionConfig @Author: amy @Description: ElPermissionConfig @Date:
 * 2021/7/8 @Version: 1.0
 */
@Service(value = "el")
public class ElPermissionConfig {

  public Boolean check(String... permission) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Set<String> roles = loginUser.getRoles();
    return roles.contains(SUPER_ADMIN) || roles.contains(permission);
  }
}
