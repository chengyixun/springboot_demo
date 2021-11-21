package com.example.tk;

import com.example.tk.entity.Company;
import com.example.tk.mapper.CompanyMapper;
import com.example.tk.service.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @ClassName: CompanyServiceTest
 * @Author: amy
 * @Description: CompanyServiceTest
 * @Date: 2021/7/23
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMapper companyMapper;


	@Test
	public void testQuery1() {
		List<Company> list = companyService.list();
		System.out.println(list);
	}

	@Test
	public void testSelectByExample() {
		PageHelper.startPage(1, 3);
		Example example = new Example(Company.class);
		example
				// 设置查询列
				.selectProperties("companyId")
				// 动态sql
				.and().andLike("companyName", "%test%")
				.andEqualTo("xxx.yy","value");

		List<Company> companies = companyMapper.selectByExample(example);

		PageInfo<Company> companyPageInfo = new PageInfo<>(companies);
		log.info("total:{}, pages:{},list:{}", companyPageInfo.getTotal(), companyPageInfo.getPages(),
				companyPageInfo.getList());
	}

	@Test
	public void testSelectByExampleCriteria() {
		PageHelper.startPage(1, 3);
		Example example = new Example(Company.class);
		example.createCriteria().andLike("companyName", "%test%");

		List<Company> companies = companyMapper.selectByExample(example);
		PageInfo<Company> companyPageInfo = new PageInfo<>(companies);
		log.info("total:{}, pages:{},list:{}", companyPageInfo.getTotal(), companyPageInfo.getPages(),
				companyPageInfo.getList());
	}

	@Test
	public void testSelectByExampleBuilder() {
		PageHelper.startPage(1, 3);
		Example example = Example.builder(Company.class)
				// 查询列
				.select("companyId")
				// 动态sql
				.where(Sqls.custom().andLike("companyName", "%test%"))
				// 去重
				.distinct()
				// 排序
				.orderByDesc("companyId").build();

		// companyMapper.selectByExampleAndRowBounds(example,new RowBounds(1,3));
		List<Company> companies = companyMapper.selectByExample(example);
		PageInfo<Company> companyPageInfo = new PageInfo<>(companies);
		log.info("total:{}, pages:{},list:{}", companyPageInfo.getTotal(), companyPageInfo.getPages(),
				companyPageInfo.getList());
	}

	@Test
	public void testSelectByWeekendSqls() {
		WeekendSqls<Object> sqls = WeekendSqls.custom();
		sqls.andLike("companyName", "%test%");

		List<Company> companies = companyMapper.selectByExample(Example.builder(Company.class).andWhere(sqls).build());
		log.info("size:{}", companies.size());
	}

	@Test
	public void testPage1() {
		PageHelper.startPage(1, 3);
		List<Company> list = companyService.list();
		PageInfo<Company> companyPageInfo = new PageInfo<>(list);
		log.info("total:{}, pages:{},list:{}", companyPageInfo.getTotal(), companyPageInfo.getPages(),
				companyPageInfo.getList());
	}

	@Test
	public void testPageByParam() {
		PageHelper.startPage(1, 3);

		WeekendSqls<Object> param = WeekendSqls.custom().andLike("companyName", "%test%");
		List<Company> companies = companyMapper.selectByExample(Example.builder(Company.class).andWhere(param).build());
		PageInfo<Company> companyPageInfo = new PageInfo<>(companies);
		log.info("total:{}, pages:{},list:{}", companyPageInfo.getTotal(), companyPageInfo.getPages(),
				companyPageInfo.getList());
	}
}
