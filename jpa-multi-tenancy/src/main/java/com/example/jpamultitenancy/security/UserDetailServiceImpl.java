package com.example.jpamultitenancy.security;

import com.example.jpamultitenancy.dto.LoginUser;
import com.example.jpamultitenancy.tenant.entity.Menu;
import com.example.jpamultitenancy.tenant.entity.Role;
import com.example.jpamultitenancy.tenant.entity.User;
import com.example.jpamultitenancy.tenant.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.jpamultitenancy.common.constant.JWTConstants.SUPER_ADMIN;

/**
 * @ClassName: UserDetailServiceImpl @Author: amy @Description: loadUserByUsername 登陆用户名密码 @Date:
 * 2021/7/6 @Version: 1.0
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired private UserService userService;

  @Override
  public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new RuntimeException("Invalid user name or password");
    }
    return new LoginUser(user, getAuthority(user));
  }

  /**
   * 获取 permission 超级管理员 返回唯一标识：superAdmin
   *
   * @param user
   * @return
   */
  public List<GrantedAuthority> getAuthority(User user) {
    Set<String> permissions = new HashSet<>();
    if (user.getIsSuperAdmin()) {
      permissions.add(SUPER_ADMIN);
    } else {
      Set<Role> roles = user.getRoles();
      permissions =
          roles.stream()
              .flatMap(role -> role.getMenus().stream())
              .filter(f -> StringUtils.isNotEmpty(f.getPermission()))
              .map(Menu::getPermission)
              .collect(Collectors.toSet());
    }
    return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }
}
