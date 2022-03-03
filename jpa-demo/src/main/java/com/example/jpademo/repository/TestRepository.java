package com.example.jpademo.repository;

import com.example.jpademo.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: TestRepository @Author: amy @Description: TestRepository @Date: 2022/3/1 @Version:
 * 1.0
 */
public interface TestRepository extends JpaRepository<Test, Integer> {}
