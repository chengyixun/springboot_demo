package com.example.mybatispluscrud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.mapper.EmployeeMapper;
import com.example.mybatispluscrud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @ClassName: EmployeeServiceImpl @Author: amy @Description: EmployeeServiceImpl @Date:
 * 2021/11/2 @Version: 1.0
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService {

  @Autowired private EmployeeMapper employeeMapper;

  /**
   * 测试先删除 后插入
   *
   * @param employee
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delAndSave(Employee employee) {
    QueryWrapper<Employee> wrapper = new QueryWrapper<>();
    wrapper.eq("name", employee.getName());
    employeeMapper.delete(wrapper);

    employee.setAge(10);
    employee.setManagerId(1L);
    employee.setEmail("111@qq.com");
    employee.setCreateTime(LocalDateTime.now());
    save(employee);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void queryAndSave(Employee employee) {
    QueryWrapper<Employee> wrapper = new QueryWrapper<>();
    wrapper.eq("name", employee.getName());
    employee.setEmail("update second");
    employeeMapper.update(employee, wrapper);
    int a = 1, b = 0, c = 0;
    c = a / b;
  }

  /** 当前方法 调用 有事务注解的方法 事务失效 */
  @Override
  //  @Transactional(rollbackFor = Exception.class)
  public void testNoTRequestHasT() {
    log.info(
        ">>> test current method no transaction,but invoke current service other method has transaction ");
    Employee employee = Employee.builder().name("小菜").build();
    QueryWrapper<Employee> wrapper = new QueryWrapper<>();
    wrapper.eq("name", employee.getName());
    employee.setEmail("update first");
    employeeMapper.update(employee, wrapper);
    queryAndSave(employee);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateById(Long id) {
    Employee e = Employee.builder().id(id).name("测试_update_2").build();
    employeeMapper.updateById(e);
  }
}
