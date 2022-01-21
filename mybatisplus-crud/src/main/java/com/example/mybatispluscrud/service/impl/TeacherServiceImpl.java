package com.example.mybatispluscrud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscrud.entity.Teacher;
import com.example.mybatispluscrud.mapper.TeacherMapper;
import com.example.mybatispluscrud.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TeacherServiceImpl @Author: amy @Description:
 *             TeacherServiceImpl @Date: 2021/11/12 @Version: 1.0
 */
@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
