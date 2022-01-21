package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.tenant.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: DepartmentRepository @Author: amy @Description:
 *             DepartmentRepository @Date: 2021/7/7 @Version: 1.0
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
