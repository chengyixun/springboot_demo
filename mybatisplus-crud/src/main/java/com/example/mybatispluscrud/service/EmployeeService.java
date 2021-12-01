package com.example.mybatispluscrud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatispluscrud.entity.Employee;

/**
 * @ClassName: EmployeeService
 * @Author: amy
 * @Description: EmployeeService
 * @Date: 2021/11/2
 * @Version: 1.0
 */
public interface EmployeeService extends IService<Employee> {

    void delAndSave(Employee employee);

    void queryAndSave(Employee employee);

    void testNoTRequestHasT();
}
