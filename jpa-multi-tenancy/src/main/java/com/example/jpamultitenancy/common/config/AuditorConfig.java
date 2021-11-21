package com.example.jpamultitenancy.common.config;

import com.example.jpamultitenancy.common.interceptor.TenantContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** @ClassName: UserAuditor @Author: amy @Description: UserAuditor @Date: 2021/6/16 @Version: 1.0 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

  /**
   * 返回操作员标志信息
   *
   * @return
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    try {
      return Optional.of(TenantContextHolder.getTenant());
    } catch (Exception e) {
    }
    // 用户定时任务，或者无Token调用的情况
    return Optional.of("system");
  }
}
