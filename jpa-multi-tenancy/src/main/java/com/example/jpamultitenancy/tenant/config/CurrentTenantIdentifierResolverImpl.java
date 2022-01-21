package com.example.jpamultitenancy.tenant.config;

import com.example.jpamultitenancy.common.interceptor.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * @ClassName: CurrentTenantIdentifierResolverImpl @Author: amy
 * @Description: 动态数据源切换 :解析租户标识 @Date: 2021/7/4 @Version: 1.0
 */
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	/** 默认的租户ID */
	private static final String DEFAULT_TENANT = "tenant_1";

	/**
	 * 解析当前的租户的ID
	 *
	 * @return
	 */
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContextHolder.getTenant();
		return StringUtils.isNotEmpty(tenant) ? tenant : DEFAULT_TENANT;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
