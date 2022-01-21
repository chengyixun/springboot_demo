package com.example.jpamultitenancy.master.service;

import com.example.jpamultitenancy.master.entity.MasterTenant;

/**
 * @ClassName: MasterTenantService @Author: amy @Description:
 *             MasterTenantService @Date: 2021/7/4 @Version: 1.0
 */
public interface MasterTenantService {

	/**
	 * 通过租户名获取租户数据源信息
	 *
	 * @param tenant 租户名
	 * @return
	 */
	MasterTenant findByTenant(String tenant);
}
