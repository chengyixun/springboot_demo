package com.example.tk.mapper;

import com.example.tk.entity.Company;

import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName: CompanyMapper
 * @Author: amy
 * @Description: CompanyMapper
 * @Date: 2021/7/14
 * @Version: 1.0
 */
public interface CompanyMapper extends Mapper<Company> {

	void insertOrUpdate(Company company);
}
