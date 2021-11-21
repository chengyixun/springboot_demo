package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.entity.Employee;
import com.example.mybatisplus.mapper.EmployeeMapper;
import com.example.mybatisplus.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: EmployeeServiceImpl
 * @Author: amy
 * @Description: EmployeeServiceImpl
 * @Date: 2021/8/27
 * @Version: 1.0
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
