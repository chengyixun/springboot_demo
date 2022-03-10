package com.example.jpademo.controller;

import com.example.jpademo.entity.Employee;
import com.example.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: EmployeeController @Author: amy @Description: EmployeeController @Date:
 * 2022/3/10 @Version: 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired private EmployeeRepository employeeRepository;

  @GetMapping
  public List<Employee> list() {
    return employeeRepository.findAll();
  }
}
