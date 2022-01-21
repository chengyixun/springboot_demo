package com.example.admin.controller;

import com.example.admin.commons.annotation.AccessLogger;
import com.example.admin.service.RedisDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-22 13:43
 * @Description:
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/demo")
@AccessLogger(value = "demo", describe = "demoController")
public class DemoController {

	@Autowired
	private RedisDistributedLockService lockService;

	@GetMapping("/test")
	@AccessLogger("query")
	public void testLock() {
		lockService.testLog();
	}

	@GetMapping("/log/{id}/{code}")
	@AccessLogger(value = "test_accesslog_query", describe = "testAccessLogMethod")
	public void testAccessLog(@PathVariable Integer id, @PathVariable String code) {
		log.info("=====accessLog====== id:{},code:{}", id, code);
	}

}
