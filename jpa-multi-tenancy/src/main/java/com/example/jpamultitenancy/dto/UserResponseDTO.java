package com.example.jpamultitenancy.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * {@link UserResponseDTO}
 *
 * @author Liyaohui
 * @date 7/6/21
 */
@Data
@Builder
public class UserResponseDTO {

	private String token;

	private String username;

	private String password;

	private List<GrantedAuthority> authorities;
}
