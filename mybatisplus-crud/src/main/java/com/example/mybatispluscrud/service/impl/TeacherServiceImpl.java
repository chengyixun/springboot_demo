package com.example.mybatispluscrud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.entity.Teacher;
import com.example.mybatispluscrud.mapper.TeacherMapper;
import com.example.mybatispluscrud.service.EmployeeService;
import com.example.mybatispluscrud.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TeacherServiceImpl @Author: amy @Description: TeacherServiceImpl @Date:
 * 2021/11/12 @Version: 1.0
 */
@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService {

  @Autowired private EmployeeService employeeService;

  @Override
  public void test1() {
    Teacher teacher = Teacher.builder().id(1L).age(30).name("里斯2").build();
    updateById(teacher);

    log.info("测试 serviceA 调用 serviceB的带事物的方法 当serviceB方法有异常时 如何处理");
    try {
      Employee employee = Employee.builder().name("小菜").build();
      employeeService.queryAndSave(employee);
    } catch (Exception e) {
      log.error("error ", e);
    }
  }
}
