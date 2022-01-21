package com.example.mybatismultitenant.controller;

import com.example.mybatismultitenant.model.SysUser;
import com.example.mybatismultitenant.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器
 *
 * @author Louis
 * @date Jun 17, 2019
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping
	public List<SysUser> list() {
		return sysUserService.list();
	}
}
