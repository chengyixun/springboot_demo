package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatisplus.entity.Employee;
import com.example.mybatisplus.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName: BaseServiceTest @Author: amy @Description: BaseServiceTest @Date: 2021/8/27 @Version:
 * 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class)
@Slf4j
public class BaseServiceTest {

  @Autowired private EmployeeService employeeService;

  @Test
  public void test1() {

    LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
    wrapper.gt(Employee::getAge, 28);

    Employee one =
        employeeService.getOne(wrapper, false); // 第二参数指定为false,使得在查到了多行记录时,不抛出异常,而返回第一条记录

    log.info("getOne:{}", one);
  }

  /**
   * IService也支持链式调用，代码写起来非常简洁 Lambda条件构造器，支持lambda表达式，可以不必像普通条件构造器一样，以字符串形式指定列名，它可以直接以实体类的方法引用来指定列
   * eg:Employee::getAge ,
   */
  @Test
  public void testLambdaQuery() {

    List<Employee> list =
        employeeService
            .lambdaQuery()
            .gt(Employee::getAge, 39)
            .likeRight(Employee::getName, "王")
            .list();

    list.forEach(System.out::println);
  }

  @Test
  public void testLambdaUpdate() {

    employeeService
        .lambdaUpdate()
        .ge(Employee::getName, "ee")
        .set(Employee::getEmail, "xxx@123")
        .update();
  }

  @Test
  public void testLambdaDelete() {

    employeeService.lambdaUpdate().like(Employee::getName, "xxx").remove();
  }
}
