package com.example.jpamultitenancy.master.repository;

import com.example.jpamultitenancy.master.entity.MasterTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: MasterTenantRepository @Author: amy @Description: MasterTenantRepository @Date:
 * 2021/7/4 @Version: 1.0
 */
@Repository
public interface MasterTenantRepository extends JpaRepository<MasterTenant, String> {

  MasterTenant findByTenant(String tenant);
}
