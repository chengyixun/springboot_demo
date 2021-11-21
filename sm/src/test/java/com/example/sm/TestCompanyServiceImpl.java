package com.example.sm;

import com.example.sm.entity.Company;
import com.example.sm.service.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: TestCompanyServiceImpl
 * @Author: amy
 * @Description: TestCompanyServiceImpl
 * @Date: 2021/7/14
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestCompanyServiceImpl {

	@Autowired
	private CompanyService companyService;

	@Test
	public void testQuery1() {
		List<Company> list = companyService.list();
		System.out.println(list);
	}

	@Test
	public void testQuery2() {
		List<Company> list = companyService.list();
		System.out.println(list);
	}

	@Test
	public void testSave() {
		companyService.save(Company.builder().companyName("test2").build());
	}

	@Test
	public void testPage() {
		PageHelper.startPage(1, 1);
		List<Company> result = companyService.list();
		PageInfo<Company> page = new PageInfo<>(result);
		log.info("total:{},pages:{}", page.getTotal(), page.getPages());
	}

}
