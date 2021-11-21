package com.example.mybatispluscrud.controller;

import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: EmployeeController @Author: amy @Description: EmployeeController @Date:
 * 2021/11/2 @Version: 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired private EmployeeService employeeService;

  @GetMapping
  public List<Employee> list() {
    return employeeService.list();
  }

  @PostMapping
  public void save(@RequestBody Employee employee){
    employeeService.delAndSave(employee);
  }

  @PostMapping("/save")
  public void save2(@RequestBody Employee employee){
    employeeService.queryAndSave(employee);
  }
}
