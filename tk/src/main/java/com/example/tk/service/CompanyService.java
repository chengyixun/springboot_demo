package com.example.tk.service;

import com.example.tk.entity.Company;
import com.example.tk.vo.CompanyVO;

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

	void save2(CompanyVO companyvo);

	List<Company> list();

	void insertOrUpdate(Company company);
}
