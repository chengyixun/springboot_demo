package com.example.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sc.config.Utils.JwtTokenUtil;
import com.example.sc.dto.AuthResponse;
import com.example.sc.dto.UserLoginDTO;
import com.sun.istack.internal.NotNull;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AuthenticationController
 * @Author: amy
 * @Description: AuthenticationController
 * @Date: 2021/7/7
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> userLogin(@RequestBody @NotNull UserLoginDTO userLoginDTO) throws AuthenticationException {
		log.info("userLogin() method call...");
		if (null == userLoginDTO.getUserName() || userLoginDTO.getUserName().isEmpty()) {
			return new ResponseEntity<>("User name is required", HttpStatus.BAD_REQUEST);
		}
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		final String token = jwtTokenUtil.generateToken(userDetails.getUsername(), String.valueOf(userLoginDTO));
		// Map the value into applicationScope bean
		return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(), token));
	}
}
