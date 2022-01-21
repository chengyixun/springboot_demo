package com.example.mybatismultitenant.common.context;

import com.example.mybatismultitenant.model.Tenant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: TenantContextHolder @Author: amy @Description:
 *             TenantContextHolder @Date: 2021/10/9 @Version: 1.0
 */
public class TenantContext {

	/**
	 * 【维护租户信息的全局核心Map】 以tenantId为键,以Tenant对象为值,
	 * 在调用DynamicDataSourceInit内方法初始化数据源的时候会将租户信息备份到该Map,可用于后续判断租户数据源是否存在等
	 */
	public static Map<String, Tenant> tenantInfoMap = new LinkedHashMap<>();

	private static ThreadLocal<String> currentTenantId = new ThreadLocal<>();

	public static void setTenantId(String tenantId) {
		currentTenantId.set(tenantId);
	}

	public static String getTenantId() {
		return currentTenantId.get();
	}

	public static void removeTenantId() {
		currentTenantId.remove();
	}

	public static boolean containTenantId(String tenantId) {
		return tenantInfoMap.containsKey(tenantId);
	}
}
