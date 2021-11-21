package com.example.tk.service.impl;

import com.example.tk.entity.Company;
import com.example.tk.mapper.CompanyMapper;
import com.example.tk.service.CompanyService;
import com.example.tk.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CompanyServiceImpl
 * @Author: amy
 * @Description: CompanyServiceImpl
 * @Date: 2021/7/23
 * @Version: 1.0
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public void save(Company company) {
		companyMapper.insert(company);
	}

	@Override
	public void save2(CompanyVO companyvo) {
		System.out.println("save2 function: " + companyvo);
	}

	@Override
	public List<Company> list() {
		return companyMapper.selectAll();
	}

	@Override
	public void insertOrUpdate(Company company) {
		companyMapper.insertOrUpdate(company);
	}
}
