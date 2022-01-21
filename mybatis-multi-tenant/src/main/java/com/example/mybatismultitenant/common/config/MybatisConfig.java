package com.example.mybatismultitenant.common.config;

import com.example.mybatismultitenant.model.Tenant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.example.mybatismultitenant.constant.CommonConstant.BASE_DATASOURCE_KEY;
import static com.example.mybatismultitenant.constant.CommonConstant.BASE_DATASOURCE_NAME;

/**
 * @ClassName: MybatisConfig @Author: amy @Description: MybatisConfig @Date:
 *             2021/10/10 @Version: 1.0
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

	/** 注入数据源相关的外部动态配置 */
	@Autowired
	private BaseDataSourceConfig baseDataSourceConfig;

	/** 基础数据源为单例 */
	private static HikariDataSource singleBaseDataSource = null;

	@Value("${sql.validation-query:select 1}")
	private String validationQuery;

	/**
	 * 初始化 【基础数据源】 的方法
	 *
	 * @return
	 */
	public HikariDataSource getBaseDataSource() {
		if (Objects.nonNull(singleBaseDataSource)) {
			return singleBaseDataSource;
		}

		HikariConfig jdbcConfig = new HikariConfig();
		// 设置必要的连接属性
		jdbcConfig.setDriverClassName(baseDataSourceConfig.getDriverClass());
		jdbcConfig.setJdbcUrl(baseDataSourceConfig.getUrl());
		jdbcConfig.setUsername(baseDataSourceConfig.getUsername());
		jdbcConfig.setPassword(baseDataSourceConfig.getPassword());
		// 设置其他数据源配置属性
		jdbcConfig.setConnectionTimeout(baseDataSourceConfig.getConnectionTimeout());
		jdbcConfig.setMaxLifetime(baseDataSourceConfig.getMaxLifetime());
		jdbcConfig.setIdleTimeout(baseDataSourceConfig.getIdleTimeout());
		jdbcConfig.setMaximumPoolSize(baseDataSourceConfig.getMaximumPoolSize());
		jdbcConfig.setMinimumIdle(baseDataSourceConfig.getMinimumIdle());
		jdbcConfig.setLeakDetectionThreshold(baseDataSourceConfig.getLeakDetectionThreshold());
		jdbcConfig.setConnectionTestQuery(validationQuery);
		jdbcConfig.setConnectionInitSql(validationQuery);

		HikariDataSource baseDataSource = new HikariDataSource(jdbcConfig);
		singleBaseDataSource = baseDataSource;

		return baseDataSource;
	}

	/**
	 * 动态数据源（切库专用）
	 *
	 * @return
	 */
	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		// 初始化基础数据源(baseDataSource)，并将基础数据源注入dynamicDataSource管理的TargetDataSources中，作为第一个默认数据源
		// =====;
		log.info("===== 新建dynamicDataSource Bean  =====");

		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 1.创建【基础数据源】
		HikariDataSource baseDataSource = getBaseDataSource();
		// 2.将【基础数据源】设置为【动态数据源】的默认值
		dynamicDataSource.setDefaultTargetDataSource(baseDataSource);
		// 3.将【基础数据源】放入【动态数据源】底层维护的TargetDataSources
		Map<Object, Object> dataSourceMap = new HashMap<>(16);
		dataSourceMap.put(BASE_DATASOURCE_KEY, baseDataSource);
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		// 4.将【基础数据源】的连接配置信息,放入TenantContext中，也作为租户库中的一员，方便统一管理。
		// 其键名tenantId=baseDataSource
		Tenant tenant = Tenant.builder().id(UUID.randomUUID().toString().replaceAll("-", ""))
				.tenantId(BASE_DATASOURCE_KEY).tenantName(BASE_DATASOURCE_NAME).build();
		Map<String, String> configMap = new LinkedHashMap<>(16);
		configMap.put("driver", baseDataSourceConfig.getDriverClass());
		configMap.put("url", baseDataSourceConfig.getUrl());
		configMap.put("username", baseDataSourceConfig.getUsername());
		configMap.put("password", baseDataSourceConfig.getPassword());
		tenant.setConfigMap(configMap);
		// 5.判断当前模式是【多租户】还是【单租户】，
		// 并将判断结果设置到全局工具类中(本质就是通过判断主库表中是否存在一个名为tenant的表,如果存在就是【多租户模式】，不存在就是【单租户模式】)

		// 6.返回封装后的【动态数据源】(此时内部维护的数据源只有1个基础数据源，键名为baseDataSource)
		return dynamicDataSource;
	}

	/**
	 * Mybatis sqlSession核心工厂
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		/** 配置数据源，此处配置为【关键配置】，如果没有此步,则dynamicDataSource动态数据源的切换无效 */
		sessionFactory.setDataSource(dynamicDataSource());
		// 扫描Model
		// sessionFactory.setTypeAliasesPackage("com.example.mybatismultitenant.model");
		// 扫描映射文件
		// PathMatchingResourcePatternResolver resolver = new
		// PathMatchingResourcePatternResolver();
		// sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
		return sessionFactory;
	}

	/**
	 * 事务管理器
	 *
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		// 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
		return new DataSourceTransactionManager(dynamicDataSource());
	}

	/*
	 * @Bean public JdbcTemplate jdbcTemplate() { return new
	 * JdbcTemplate(dynamicDataSource()); }
	 */
}
