package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.entity.Employee;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.mapper.EmployeeMapper;
import com.example.mybatisplus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/** @Author: wangyu @Date: Created 2020-12-15 09:45 @Description: @Modified By: */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class)
@Slf4j
public class BaseMapperTest {

  @Resource private UserMapper userMapper;

  @Autowired private EmployeeMapper employeeMapper;

  @Test
  public void getAll() {
    List<User> students = userMapper.selectList(null);
    students.stream().forEach(s -> System.out.println(s.getCreateTime()));
    assertEquals(2, students.size());
  }

/*  @Test
  public void insert() {
    User user = User.builder().username("www").password("123456").build();
    int insert = userMapper.insert(user);
    log.info("插入影响行数:{},小华的ID：{}", insert, user.getId());
  }

  *//** 根据ID更新 *//*
  @Test
  public void update() {
    User user = User.builder().username("张三111").build();
    user.setId(22235L);
    userMapper.updateById(user);
  }*/

  /** 根据条件更新 */
  @Test
  public void updateByWrapper() {
    UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("username", "张三");
    User user = User.builder().password("111111").build();
    userMapper.update(user, updateWrapper);
  }

  @Test
  public void deleteById() {
    userMapper.deleteById(7);
  }

  @Test
  public void deleteByMap() {
    Map<String, Object> columnMap = new HashMap<>();
    columnMap.put("username", "小华");
    columnMap.put("password", "777777");
    userMapper.deleteByMap(columnMap);
  }

  @Test
  public void deleteBatchIds() {
    List<Integer> idList = Lists.newArrayList();
    idList.add(8);
    idList.add(2);
    userMapper.deleteBatchIds(idList);
  }

  @Test
  public void selectById() {
    User user = userMapper.selectById(2);
    System.out.println(user);
  }

  @Test
  public void selectCount() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("username", "张三");
    System.out.println(userMapper.selectCount(queryWrapper));
  }

  @Test
  public void selectBatchIds() {
    List<User> students = userMapper.selectBatchIds(Arrays.asList(11));
    students.forEach(System.out::println);
  }

  /** 分页 */
  @Test
  public void selectPage() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("username", "张三");
    // pageNo,pageSize
    Page<User> page = new Page<>(1, 1);
    // page.setAsc("name");
    IPage<User> result = userMapper.selectPage(page, queryWrapper);
    List<User> records = result.getRecords();
    log.info(
        "总数：{},总页数：{},当前页：{},页大小：{}",
        result.getTotal(),
        result.getPages(),
        result.getCurrent(),
        result.getSize());
  }

  @Test
  public void testLogicDelete() {
    int i = userMapper.deleteById(10);
    log.info("deleteFlag:{}", i);
  }

  /**
   * 基本类型不能作为 Arrays.asList方法的参数，否则会被当做一个参数。*2. Arrays.asList 返回的 List 不支持增删操作*
   *
   * <p>可以用new ArrayList(Arrays.asList(arr))包一下的。
   */
  @Test
  public void testAsList() {
    String[] arr = {"1", "2", "4"};
    List<String> strings = Arrays.asList(arr);

    strings.add("5");
  }

  /** 满足 1.只查部分列 2.返回指定列 */
  @Test
  public void testSelectMap() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("id", "username");
    List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
    maps.forEach(System.out::println);
  }

  /** 条件构造器 wrapper */
  @Test
  public void testWrapper() {
    QueryWrapper<Employee> wrapper = new QueryWrapper<>();
    wrapper.like("name", "家").lt("age", 25);
    employeeMapper.selectList(wrapper);
  }

  /** Condition */
  @Test
  public void condition() {
    String name = "xx";
    QueryWrapper<Employee> wrapper = new QueryWrapper<>();
    wrapper.like(StringUtils.isNotBlank(name), "name", name);
  }

  @Test
  public void testPageQuery() {
    LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
    wrapper.ge(Employee::getAge, 28);

    // 设置分页，查第3页，每页 2条数据
    Page<Employee> page = new Page<>(4, 1);
    Page<Employee> employeePage = employeeMapper.selectPage(page, wrapper);

    log.info(
        "总记录数:{},总页数：{},当前页码：{}",
        employeePage.getTotal(),
        employeePage.getPages(),
        employeePage.getCurrent());
    List<Employee> records = employeePage.getRecords();
    records.forEach(System.out::println);
  }

}
