package com.example.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sc.entity.User;

/**
 * @ClassName: UserRepository
 * @Author: amy
 * @Description: UserRepository
 * @Date: 2021/7/7
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
