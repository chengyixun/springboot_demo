package com.example.mybatisplus.commons.config;

import com.example.mybatisplus.entity.TenantInfo;
import com.example.mybatisplus.service.TenantInfoService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: DynamicDataSourceInit @Author: amy @Description: DynamicDataSourceInit @Date:
 * 2021/10/11 @Version: 1.0
 */
@Slf4j
@Configuration
public class DynamicDataSourceInit {

  @Autowired private TenantInfoService tenantInfoService;

  @Resource private DynamicDataSource dynamicDataSource;

  @Resource private HikariDataSource master;

  @PostConstruct
  public void initDataSource() {
    log.info("=====DynamicDataSourceInit 【初始化动态数据源】=====");
    Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();
    dataSourceMap.put("master", master);

    List<TenantInfo> tenants = tenantInfoService.list();
    tenants.forEach(
        tenantInfo -> {
          log.info(tenantInfo.toString());
          HikariDataSource dataSource = new HikariDataSource();
          dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
          dataSource.setJdbcUrl(tenantInfo.getUrl());
          dataSource.setUsername(tenantInfo.getUsername());
          dataSource.setPassword(tenantInfo.getPassword());
          dataSource.setDataSourceProperties(master.getDataSourceProperties());
          dataSourceMap.put(tenantInfo.getTenant(), dataSource);
        });
    // 设置数据源
    dynamicDataSource.setDataSources(dataSourceMap);
    /** 必须执行此操作，才会重新初始化AbstractRoutingDataSource 中的 resolvedDataSources，也只有这样，动态切换才会起效 */
    dynamicDataSource.afterPropertiesSet();
    log.info("=====DynamicDataSourceInit 【初始化动态数据源】  dynamicDataSource.afterPropertiesSet() end =====");
  }
}
