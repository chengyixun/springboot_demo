package com.example.mybatispluscrud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscrud.entity.Student;
import com.example.mybatispluscrud.mapper.StudentMapper;
import com.example.mybatispluscrud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: StudentServiceImpl @Author: amy @Description:
 *             StudentServiceImpl @Date: 2021/11/12 @Version: 1.0
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
