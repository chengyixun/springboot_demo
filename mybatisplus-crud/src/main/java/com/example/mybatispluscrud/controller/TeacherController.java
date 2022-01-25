package com.example.mybatispluscrud.controller;

import com.example.mybatispluscrud.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: StudentController @Author: amy @Description: StudentController @Date:
 * 2022/1/25 @Version: 1.0
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
  @Autowired private TeacherService teacherService;

  @GetMapping("/test1")
  public void test1() {
    teacherService.test1();
  }
}
