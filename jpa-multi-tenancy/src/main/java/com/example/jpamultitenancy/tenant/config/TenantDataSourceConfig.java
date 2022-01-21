package com.example.jpamultitenancy.tenant.config;

import com.example.jpamultitenancy.tenant.entity.User;
import com.example.jpamultitenancy.tenant.repository.UserRepository;
import com.example.jpamultitenancy.tenant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TenantDataSourceConfig @Author: amy @Description:
 *             TenantDataSourceConfig @Date: 2021/7/4 @Version: 1.0
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.example.jpamultitenancy.tenant.entity",
		"com.example.jpamultitenancy.tenant.repository" })
@EnableJpaRepositories(basePackages = { "com.example.jpamultitenancy.tenant.repository",
		"com.example.jpamultitenancy.tenant.service" }, entityManagerFactoryRef = "tenantEntityManagerFactory", transactionManagerRef = "tenantTransactionManager")
public class TenantDataSourceConfig {

	@Bean("jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/**
	 * The Multi tenant connection provider
	 *
	 * @return
	 */
	@Bean(name = "datasourceBasedMultiTenantConnectionProvider")
	@ConditionalOnBean(name = "masterEntityManagerFactory")
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		return new DataSourceBasedMultiTenantConnectionProviderImpl();
	}

	/**
	 * The Multi tenant identifier resolver
	 *
	 * @return
	 */
	@Bean(name = "currentTenantIdentifierResolver")
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new CurrentTenantIdentifierResolverImpl();
	}

	/**
	 * 首先定义事务，注入多租户信息：connectionProvider、 tenantIdentifierResolver
	 *
	 * @param connectionProvider
	 * @param tenantIdentifierResolver
	 * @return
	 */
	@Bean(name = "tenantEntityManagerFactory")
	@ConditionalOnBean(name = "datasourceBasedMultiTenantConnectionProvider")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("datasourceBasedMultiTenantConnectionProvider") MultiTenantConnectionProvider connectionProvider,
			@Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantIdentifierResolver) {
		LocalContainerEntityManagerFactoryBean localBean = new LocalContainerEntityManagerFactoryBean();
		localBean.setPackagesToScan(new String[] { User.class.getPackage().getName(),
				UserRepository.class.getPackage().getName(), UserService.class.getPackage().getName() });
		localBean.setJpaVendorAdapter(jpaVendorAdapter());
		localBean.setPersistenceUnitName("tenant-database-persistence-unit");
		Map<String, Object> properties = new HashMap<>();
		// 重点 多租户的配置 DATABASE SCHEMA
		properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
		properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
		properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		properties.put(Environment.SHOW_SQL, true);
		properties.put(Environment.FORMAT_SQL, true);
		properties.put(Environment.HBM2DDL_AUTO, "update");
		localBean.setJpaPropertyMap(properties);
		return localBean;
	}

	/**
	 * 创建事务管理器，将租户配置信息注入事务
	 *
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean(name = "tenantTransactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}
