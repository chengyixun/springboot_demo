package com.example.mybatismultitenant.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: DynamicDataSourceConfig @Author: amy @Description:
 *             DynamicDataSourceConfig @Date: 2021/10/9 @Version: 1.0
 */
@Data
@Configuration
public class BaseDataSourceConfig {

	/** HikariDataSource数据库连接池的必要连接配置 */
	@Value("${spring.datasource.driver-class-name}")
	private String driverClass;

	@Value("${spring.datasource.jdbcUrl}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	/**
	 * 数据库连接校验sql:mysql 是【select 1】，oracle是【select 1 from dual】，需要外部来配置
	 */
	/*
	 * @Value("${sql.validation-query}") private String validationQuery;
	 */

	/********************************
	 * HikariDataSource数据库连接池的其他自定义配置-开始
	 ********************************/
	/** 连接超时时间:30秒(Long类型) */
	@Value("${aicc.spring.datasource.hikari.connection-timeout:30000}")
	private Long connectionTimeout;
	/** 最大生命时间:10分钟(Long类型) */
	@Value("${aicc.spring.datasource.hikari.max-lifetime:600000}")
	private Long maxLifetime;
	/** 最大空闲时间:5分钟(Long类型) */
	@Value("${aicc.spring.datasource.hikari.idle-timeout:300000}")
	private Long idleTimeout;
	/** 最大连接数(Integer) */
	@Value("${aicc.spring.datasource.hikari.maximum-pool-size:20}")
	private Integer maximumPoolSize;
	/** 最小空闲连接数(Integer) */
	@Value("${aicc.spring.datasource.hikari.minimum-idle:1}")
	private Integer minimumIdle;
	/** 启用泄漏检测超时判定标准(毫秒):3分钟(Long) */
	@Value("${aicc.spring.datasource.hikari.leak-detection-threshold:180000}")
	private Integer leakDetectionThreshold;

	/********************************
	 * HikariDataSource数据库连接池的其他自定义配置-结束
	 ********************************/
}
