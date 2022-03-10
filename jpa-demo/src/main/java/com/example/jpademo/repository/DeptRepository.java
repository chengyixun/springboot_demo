package com.example.jpademo.repository;

import com.example.jpademo.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: DeptRepository @Author: amy @Description: DeptRepository @Date: 2022/3/10 @Version:
 * 1.0
 */
@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {}
