package com.example.tk.controller;

import com.example.tk.common.annotation.AccessLogger;
import com.example.tk.entity.Company;
import com.example.tk.service.CompanyService;
import com.example.tk.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CompanyController
 * @Author: amy
 * @Description: CompanyController
 * @Date: 2021/7/28
 * @Version: 1.0
 */
@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping
	public void save(@RequestBody Company company) {
		companyService.insertOrUpdate(company);
	}

	@GetMapping
	public List<Company> list() {
		return companyService.list();
	}

	@PostMapping("/save2")
	@AccessLogger("company模块")
	public String save2(@RequestBody CompanyVO company) {

		try {
			int a = 1, b = 0;
			int c = a / b;
			System.out.println(c);
			companyService.save2(company);
			System.out.println("test controller");
			return "controller";
		} catch (Exception e) {
			String message = e.getMessage();
			log.error("error:{}", message);
			return message;
		}

	}
}
