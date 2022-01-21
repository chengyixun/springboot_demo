package com.example.jpamultitenancy.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.jpamultitenancy.tenant.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: LoginUser @Author: amy @Description: LoginUser 登陆用户的身份权限 @Date:
 *             2021/7/6 @Version: 1.0
 */
@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

	/** 用户信息 */
	private User user;

	@JSONField(serialize = false)
	private final List<GrantedAuthority> authorities;

	public Set<String> getRoles() {
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
	}

	public LoginUser(User user, List<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	private String tenant;

	private String token;

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/** 账户是否未过期,过期无法验证 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 指定用户是否解锁,锁定的用户无法进行身份验证
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否可用 ,禁用的用户不能身份验证
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
