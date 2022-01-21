package com.example.jpamultitenancy.common.util;

import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceUtils @Author: amy @Description: DataSourceUtils @Date:
 *             2021/7/4 @Version: 1.0
 */
@Slf4j
public class DataSourceUtils {

	public static DataSource wrapperDataSource(MasterTenant tenant) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername(tenant.getUsername());
		dataSource.setPassword(tenant.getPassword());
		dataSource.setJdbcUrl(tenant.getUrl());
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setConnectionTimeout(20000);
		dataSource.setMinimumIdle(10);
		dataSource.setMaximumPoolSize(20);
		dataSource.setIdleTimeout(300000);
		dataSource.setConnectionTimeout(20000);
		String tenantId = tenant.getId();
		String tenantConnectionPoolName = tenantId + "-connection-pool";
		dataSource.setPoolName(tenantConnectionPoolName);
		log.info("Configuration datasource:{},Connection pool name:{}", tenant.getId(), tenantConnectionPoolName);
		return dataSource;
	}
}
