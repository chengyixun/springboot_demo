package com.example.jpademo.controller;

import com.example.jpademo.entity.Menu;
import com.example.jpademo.entity.Test;
import com.example.jpademo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TestController @Author: amy @Description: TestController @Date: 2022/3/1 @Version:
 * 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired private TestRepository testRepository;

  @GetMapping
  public List<Test> list() {
    return testRepository.findAll();
  }

  @GetMapping("/menu")
  public Menu menu() {
    return Menu.builder().id(1).createDate(new Date()).build();
  }
}
