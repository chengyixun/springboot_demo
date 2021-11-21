package com.example.jpamultitenancy.common.interceptor;

/**
 * @ClassName: TenantContextHolder @Author: amy @Description: TenantContextHolder 维护租户标识信息 @Date:
 * 2021/7/4 @Version: 1.0
 */
public class TenantContextHolder {

  private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

  public static void setTenant(String tenant) {
    CONTEXT.set(tenant);
  }

  public static String getTenant() {
    return CONTEXT.get();
  }

  public static void clear() {
    CONTEXT.remove();
  }
}
