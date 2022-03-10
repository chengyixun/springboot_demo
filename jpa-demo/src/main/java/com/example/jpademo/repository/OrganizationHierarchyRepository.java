package com.example.jpademo.repository;

import com.example.jpademo.entity.OrganizationHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OrganizationHierarchyRepository @Author: amy @Description:
 * OrganizationHierarchyRepository @Date: 2022/3/10 @Version: 1.0
 */
@Repository
public interface OrganizationHierarchyRepository
    extends JpaRepository<OrganizationHierarchy, String> {}
