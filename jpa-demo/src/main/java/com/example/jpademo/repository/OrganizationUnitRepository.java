package com.example.jpademo.repository;

import com.example.jpademo.entity.OrganizationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OrganizationUnitRepository
 * @Author: amy
 * @Description: OrganizationUnitRepository
 * @Date: 2022/3/10
 * @Version: 1.0
 */
@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit,String> {
}
