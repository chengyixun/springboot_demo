package com.example.mybatispluscrud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: BaseMapperTest @Author: amy @Description: BaseMapperTest @Date:
 *             2021/11/2 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusCrudApplication.class)
@Slf4j
public class BaseMapperTest {
	@Autowired
	private EmployeeMapper employeeMapper;

	/**
	 * 开启mp的逻辑删除后，会对SQL产生如下的影响
	 *
	 * <p>
	 * INSERT语句：没有影响
	 *
	 * <p>
	 * SELECT语句：追加WHERE条件，过滤掉已删除的数据
	 *
	 * <p>
	 * UPDATE语句：追加WHERE条件，防止更新到已删除的数据
	 *
	 * <p>
	 * DELETE语句：转变为UPDATE语句
	 */
	// UPDATE biz_employee2 SET deleted=0 WHERE id=? AND deleted=1
	@Test
	public void testLogicDel() {
		int i = employeeMapper.deleteById(5L);
		log.info("rowAffected:{}", i);
	}

	@Test
	public void testQueryWithLogic() {
		List<Employee> employees = employeeMapper.selectList(null);
		employees.forEach(System.out::println);
	}

	/**
	 * 1. 名字中包含佳，且年龄小于25
	 *
	 * <p>
	 * sql: SELECT * FROM biz_employee WHERE name like '%主%' AND age < 50
	 *
	 * <p>
	 * 2. 名字为王姓，并且（年龄小于40，或者邮箱不为空）
	 *
	 * <p>
	 * sql: name like '王%' AND (age < 40 OR email is not null)
	 *
	 * <p>
	 * 3. 满足：年龄为30，31，34，35, 返回满足条件的第一条记录
	 *
	 * <p>
	 * sql: ge IN (30,31,34,35) LIMIT 1
	 *
	 * <p>
	 * 4. 只选出id, name 列 (QueryWrapper 特有)
	 *
	 * <p>
	 * sql: SELECT id, name FROM user;
	 *
	 * <p>
	 * 5. 选出id, name, age, email, 等同于排除 manager_id 和 create_time // 当列特别多,
	 * 而只需要排除个别列时, 采用上面的方式可能需要写很多个列, 可以采用重载的select方法，指定需要排除的列
	 */
	@Test
	public void testQuery1() {
		QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
		// 1.
		queryWrapper.like("name", "%主%").lt("age", 50);
		// 2.
		queryWrapper.likeRight("name", "王%").and(q -> q.lt("age", 40).or().isNotNull("email"));
		// 3.
		queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("LIMIT 1");
		// 4.
		queryWrapper.select("id", "name");

		// 5.重载的select方法，指定需要排除的列
		queryWrapper.select(p -> {
			String columnName = p.getColumn();
			return !"create_time".equals(columnName) && !"manager_id".equals(columnName);
		});
	}

	@Test
	public void testQueryConditionApi() {
		String name = "黄";
		// 当 name为外部传入的参数， 仅当 StringUtils.hasText(name) 为 true 时, 会拼接这个like语句到WHERE中
		QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.hasText(name), "name", name);
		// 等价于简化代码
		if (StringUtils.hasText(name)) {
			queryWrapper.like("name", name);
		}
	}

	/** lambda条件构造器 */
	@Test
	public void testQueryLambda() {
		LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Employee::getAge, 50);
		employeeMapper.selectList(lambdaQueryWrapper);
	}

	/**
	 * 满足 name %吴 的人的数据 更新他们的email
	 *
	 * <p>
	 * UPDATE biz_employee SET email=? WHERE name LIKE CONCAT(?,'%')
	 */
	@Test
	public void testLambdaUpdate() {
		Employee employee = new Employee();
		employee.setName("吴");
		LambdaUpdateWrapper<Employee> updateWrapper = new LambdaUpdateWrapper<>(employee);
		// 等价于
		/*
		 * LambdaUpdateWrapper<Employee> updateWrapper = new LambdaUpdateWrapper<>();
		 * updateWrapper.likeRight(Employee::getName,"吴");
		 */
		Employee currentEmployee = new Employee();
		currentEmployee.setEmail("150121@qq.com");

		employeeMapper.update(currentEmployee, updateWrapper);
	}

	/**
	 * 由于BaseMapper提供的2个更新方法都是传入一个实体对象去执行更新，这在需要更新的列比较多时还好，
	 *
	 * <p>
	 * 若想要更新的只有那么一列，或者两列，则创建一个实体对象就显得有点麻烦。
	 *
	 * <p>
	 * 针对这种情况，UpdateWrapper提供有set方法，可以手动拼接SQL中的SET语句，此时可以不必传入实体对象，示例如下
	 */
	@Test
	public void testLambdaUpdate2() {
		// 满足名字开头姓吴的，将 managerId 设为1
		LambdaUpdateWrapper<Employee> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.likeRight(Employee::getName, "吴").set(Employee::getManagerId, 1);
		employeeMapper.update(null, updateWrapper);
	}

	/**
	 * SELECT id,name,age,email,manager_id,create_time,version,deleted FROM
	 * biz_employee WHERE (age > ?) LIMIT ?,?
	 */
	@Test
	public void testPage() {
		LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.gt(Employee::getAge, 20);
		// 设置分页信息, 查第2页, 每页3条数据
		Page<Employee> employeePage = new Page<>(2, 3);

		Page<Employee> employees = employeeMapper.selectPage(employeePage, lambdaQueryWrapper);
		log.info("总数量：{},总页数：{},当前第：{} 页,当前页数量：{}", employees.getTotal(), employees.getPages(), employees.getCurrent(),
				employees.getRecords().size());
	}

	/** 测试自动填充数据 */
	@Test
	public void testAutoFillInsert() {
		Employee employee = Employee.builder().name("测试1").age(30).email("150121268@qq.com").managerId(2L).build();
		employeeMapper.insert(employee);
	}

	@Test
	public void testAutoFillUpdate() {
		Employee employee = Employee.builder().id(154L).name("测试1_test").build();
		employeeMapper.updateById(employee);
	}

	/**
	 * 测试乐观锁
	 *
	 * <p>
	 * 在读多写少的场景下，乐观锁比较适用，能够减少加锁操作导致的性能开销，提高系统吞吐量。
	 *
	 * <p>
	 * 在写多读少的场景下，悲观锁比较使用，否则会因为乐观锁不断失败重试，反而导致性能下降。
	 *
	 * <p>
	 * 乐观锁的实现如下： 取出记录时，获取当前version 更新时，带上这个version 执行更新时， set version = newVersion
	 * where version = oldVersion 如果oldVersion与数据库中的version不一致，就更新失败
	 * 这种思想和CAS（Compare And Swap）非常相似。
	 *
	 * UPDATE biz_employee SET name=?, age=?, email=?, manager_id=?, create_time=?,
	 * update_time=?, version=? WHERE id=? AND version=? AND deleted=0
	 * 
	 */
	@Test
	public void testOptimisticLocke() {
		// 先查询
		Employee employee = employeeMapper.selectById(155L);
		// 后修改
		employee.setName("测试Version变更");
		// 最后更新 WHERE id=? AND version=? AND deleted=0
		employeeMapper.updateById(employee);
	}

}
