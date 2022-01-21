package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.common.constant.JWTConstants;
import com.example.jpamultitenancy.common.exception.ServerRestException;
import com.example.jpamultitenancy.common.interceptor.TenantContextHolder;
import com.example.jpamultitenancy.common.util.JwtTokenUtils;
import com.example.jpamultitenancy.dto.LoginUser;
import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.example.jpamultitenancy.master.service.MasterTenantService;
import com.example.jpamultitenancy.tenant.service.LoginService;
import com.example.jpamultitenancy.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName: LoginServiceImpl @Author: amy @Description:
 *             LoginServiceImpl @Date: 2021/6/22 @Version: 1.0
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private MasterTenantService masterTenantService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Override
	public LoginUser login(LoginVO loginVO) {
		// 1. check tenant
		MasterTenant tenant = masterTenantService.findByTenant(loginVO.getTenant());
		if (Objects.isNull(tenant)) {
			throw new ServerRestException("Please contact service provider.");
		}
		TenantContextHolder.setTenant(tenant.getTenant());
		// 2. check username and password
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword()));
		// 3. setAuthentication
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 4. get UserDetails
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		// 5. generateToken
		String token = jwtTokenUtils.generateToken(loginUser.getUsername(), loginVO.getTenant());
		loginUser.setToken(JWTConstants.TOKEN_PREFIX + token);
		loginUser.setTenant(loginVO.getTenant());
		return loginUser;
	}
}
