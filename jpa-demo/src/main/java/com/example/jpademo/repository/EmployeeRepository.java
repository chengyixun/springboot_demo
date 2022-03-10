package com.example.jpademo.repository;

import com.example.jpademo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: EmployeeRepository @Author: amy @Description: EmployeeRepository @Date:
 * 2022/3/10 @Version: 1.0
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
