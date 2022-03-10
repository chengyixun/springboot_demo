package com.example.jpademo.controller;

import com.example.jpademo.entity.Dept;
import com.example.jpademo.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: DeptController @Author: amy @Description: DeptController @Date: 2022/3/10 @Version:
 * 1.0
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

  @Autowired private DeptRepository deptRepository;

  @GetMapping
  public List<Dept> list() {
    return deptRepository.findAll();
  }
}
