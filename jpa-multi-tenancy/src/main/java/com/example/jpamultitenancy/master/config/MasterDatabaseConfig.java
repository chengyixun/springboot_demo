package com.example.jpamultitenancy.master.config;

import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.example.jpamultitenancy.master.repository.MasterTenantRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @ClassName: MasterDatabaseConfig @Author: amy @Description:
 *             MasterDatabaseConfig @Date: 2021/7/4 @Version: 1.0
 */
@Configuration
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.example.jpamultitenancy.master.entity",
		"com.example.jpamultitenancy.master.repository" }, entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager")
public class MasterDatabaseConfig {

	@Autowired
	private JpaProperties jpaProperties;
	@Autowired
	private MasterDatabaseProperties dataSourceProperties;

	@Bean(name = "masterDataSource")
	public DataSource masterDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(dataSourceProperties.getUrl());
		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());
		dataSource.setPoolName(dataSourceProperties.getPoolName());
		dataSource.setMaximumPoolSize(dataSourceProperties.getMaxPoolSize());
		dataSource.setMinimumIdle(dataSourceProperties.getMinIdle());
		dataSource.setConnectionTimeout(dataSourceProperties.getConnectionTimeout());
		dataSource.setIdleTimeout(dataSourceProperties.getIdleTimeout());
		log.info("Setup of masterDatasource successfully.");
		return dataSource;
	}

	/**
	 * 配置实体管理器
	 *
	 * @return
	 */
	@Primary
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lb = new LocalContainerEntityManagerFactoryBean();
		// 注入数据源
		lb.setDataSource(masterDataSource());
		// 设置扫描基本包
		lb.setPackagesToScan(new String[] { MasterTenant.class.getPackage().getName(),
				MasterTenantRepository.class.getPackage().getName() });
		// Setting a name for the persistence unit as Spring sets it as 'default' if not
		// defined.
		lb.setPersistenceUnitName("master-database-persistence-unit");

		// 注入jpa厂商适配器
		// Setting Hibernate as the JPA provider.
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		lb.setJpaVendorAdapter(vendorAdapter);

		// Setting the hibernate properties
		lb.setJpaProperties(hibernateProperties());

		log.info("Setup of masterEntityManagerFactory successfully.");

		return lb;
	}

	// 配置jpa事务管理器
	@Bean(name = "masterTransactionManager")
	public JpaTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		log.info("Setup of masterTransactionManager successfully.");
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		properties.put(Environment.SHOW_SQL, true);
		properties.put(Environment.FORMAT_SQL, true);
		properties.put(Environment.HBM2DDL_AUTO, "update");
		return properties;
	}
}
