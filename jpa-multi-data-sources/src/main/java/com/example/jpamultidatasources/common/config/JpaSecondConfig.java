package com.example.jpamultidatasources.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName: JpaSecondConfig @Author: amy @Description: JpaSecondConfig @Date:
 *             2021/9/18 @Version: 1.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.jpamultidatasources.dao2", entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanSecond", transactionManagerRef = "platformTransactionManagerSecond")
public class JpaSecondConfig {

	@Autowired
	@Qualifier(value = "secondDataSource")
	private DataSource secondDataSource;

	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanSecond(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(secondDataSource).properties(jpaProperties.getProperties())
				.packages("com.example.jpamultidatasources.entity")
				// .persistenceUnit("pu1")
				.build();
	}

	@Bean
	public PlatformTransactionManager platformTransactionManagerSecond(EntityManagerFactoryBuilder builder) {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBeanSecond(
				builder);
		return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
	}
}
