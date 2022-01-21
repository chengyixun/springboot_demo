package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.common.exception.Exceptions;
import com.example.jpamultitenancy.common.interceptor.TenantContextHolder;
import com.example.jpamultitenancy.tenant.entity.User;
import com.example.jpamultitenancy.tenant.repository.UserRepository;
import com.example.jpamultitenancy.tenant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServiceImpl @Author: amy @Description: UserServiceImpl @Date:
 *             2021/7/4 @Version: 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> list() {
		return userRepository.findAll();
	}

	@Override
	public User getOne(Long id) {
		return userRepository.findById(id).orElseThrow(() -> Exceptions.NOT_FOUND());
	}

	@Override
	public void save(User user) {
		user.setTenant(TenantContextHolder.getTenant());
		userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> Exceptions.NOT_FOUND());
	}

	@Override
	public User findByUsername(String username) {
		log.info("current tenant:{}", TenantContextHolder.getTenant());
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveAll(List<User> users) {
		userRepository.saveAll(users);
	}
}
