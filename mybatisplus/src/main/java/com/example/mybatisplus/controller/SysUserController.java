package com.example.mybatisplus.controller;

import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-17 11:39
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> list() {
		return userService.list();
	}

}
