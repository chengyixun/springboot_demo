package com.example.sm.service.impl;

import com.example.sm.entity.Company;
import com.example.sm.repository.CompanyMapper;
import com.example.sm.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName: CompanyServiceImpl
 * @Author: amy
 * @Description: CompanyServiceImpl
 * @Date: 2021/7/14
 * @Version: 1.0
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public void save(Company company) {
		// companyMapper.insert(company);
		companyMapper.insert(company);
	}

	@Override
	public List<Company> list() {
		Example example = new Example(Company.class);

		Example.Criteria criteria = example.createCriteria();
		// criteria.andLike("companyName", "test");
		return companyMapper.selectByExample(example);
	}
}
