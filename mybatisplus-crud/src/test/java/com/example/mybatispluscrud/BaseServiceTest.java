package com.example.mybatispluscrud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName: BaseServiceTest @Author: amy @Description: BaseServiceTest @Date: 2021/11/5 @Version:
 * 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusCrudApplication.class)
@Slf4j
public class BaseServiceTest {

  @Autowired private EmployeeService employeeService;

  /**
   * 设置了全局逻辑删除
   *
   * <p>SELECT id,name,age,email,manager_id,create_time,version,deleted FROM biz_employee WHERE
   * deleted=0
   */
  @Test
  public void testQuery() {
    List<Employee> employees = employeeService.list();
  }

  @Test
  public void testLambdaQuery() {
    LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Employee::getAge, 28);
    // 第二参数指定为false,使得在查到了多行记录时,不抛出异常,而返回第一条记录
    Employee one = employeeService.getOne(lambdaQueryWrapper, false);
    System.out.println(one);
  }

  @Test
  @Async("loggerTaskExecutor")
  public void testSave() {
    Employee employee = Employee.builder().name("黄主管").build();
    employeeService.queryAndSave(employee);
  }
}
