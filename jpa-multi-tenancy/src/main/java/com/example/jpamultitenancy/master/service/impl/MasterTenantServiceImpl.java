package com.example.jpamultitenancy.master.service.impl;

import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.example.jpamultitenancy.master.repository.MasterTenantRepository;
import com.example.jpamultitenancy.master.service.MasterTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: MasterTenantServiceImpl @Author: amy @Description: MasterTenantServiceImpl @Date:
 * 2021/7/4 @Version: 1.0
 */
@Service
@Slf4j
public class MasterTenantServiceImpl implements MasterTenantService {

  @Autowired private MasterTenantRepository masterTenantRepository;

  @Override
  public MasterTenant findByTenant(String tenant) {
    return masterTenantRepository.findByTenant(tenant);
  }
}
