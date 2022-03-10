package com.example.jpademo.repository;

import com.example.jpademo.entity.OrganizationStruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OrganizationStructRepository @Author: amy @Description:
 * OrganizationStructRepository @Date: 2022/3/10 @Version: 1.0
 */
@Repository
public interface OrganizationStructRepository extends JpaRepository<OrganizationStruct, String> {}
