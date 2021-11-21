package com.example.sc.security;

import com.example.sc.entity.Role;
import com.example.sc.entity.User;
import com.example.sc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: JwtUserDetailsService
 * @Author: amy
 * @Description: JwtUserDetailsService
 * @Date: 2021/7/7
 * @Version: 1.0
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (Objects.isNull(user)) {
			throw new RuntimeException("Invalid user name or password");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user.getRoles()));
	}

	public List<SimpleGrantedAuthority> getAuthority(Set<Role> roles) {
		return roles.stream().map(m -> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
	}
}
