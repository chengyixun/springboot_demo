package com.example.jpademo;

import com.example.jpademo.commons.Gender;
import com.example.jpademo.entity.Content;
import com.example.jpademo.entity.Dept;
import com.example.jpademo.entity.Employee;
import com.example.jpademo.repository.DeptRepository;
import com.example.jpademo.repository.EmployeeRepository;
import com.example.jpademo.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class JpaDemoApplicationTests {

  @Autowired private TestRepository testRepository;

  @Autowired private DeptRepository deptRepository;

  @Autowired private EmployeeRepository employeeRepository;

  @Test
  public void testSave() {
    Dept dept = deptRepository.save(Dept.builder().name("部门B").build());
    log.info("test save dept:{}", dept);
  }

  @Test
  public void testSaveEmployee() {
    Dept dept = Dept.builder().id(1).name("部门A").build();
    Employee employee = Employee.builder().name("张三").gender(Gender.female).dept(dept).build();
    Employee employee1 = Employee.builder().name("李四").gender(Gender.male).dept(dept).build();

    List<Employee> employees = Lists.newArrayList();

    employees.add(employee);
    employees.add(employee1);
    employeeRepository.saveAll(employees);
  }

  @Test
  void contextLoads() {
    List<com.example.jpademo.entity.Test> results = testRepository.findAll();
    log.info("result:{}", results);
  }

  @Test
  void testInsert() {
    Content content = Content.builder().name("amy").host("www.amy.com").build();
    com.example.jpademo.entity.Test test = new com.example.jpademo.entity.Test();
    test.setContent(content);
    testRepository.save(test);
  }

  @Test
  void testSelectByParam() {}
}
