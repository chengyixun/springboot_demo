package com.example.sm.service;

import com.example.sm.entity.Company;

import java.util.List;

/**
 * @ClassName: CompanyService
 * @Author: amy
 * @Description: CompanyServiceImpl
 * @Date: 2021/7/14
 * @Version: 1.0
 */
public interface CompanyService {

	void save(Company company);

	List<Company> list();
}
