package com.example.sc.repository;

import com.example.sc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: UserRepository
 * @Author: amy
 * @Description: UserRepository
 * @Date: 2021/7/7
 * @Version: 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
