package com.example.sc.controller;

import com.example.sc.entity.Role;
import com.example.sc.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: RoleController
 * @Author: amy
 * @Description: RoleController
 * @Date: 2021/7/7
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/role")
@Slf4j
public class RoleController {

	@Autowired
	private RoleRepository repository;

	@GetMapping
	public List<Role> list() {
		return repository.findAll();
	}
}
