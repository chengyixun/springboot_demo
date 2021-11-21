package com.example.jpamultidatasources.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName: JpaPrimaryConfig @Author: amy @Description: JpaPrimaryConfig @Date:
 * 2021/9/18 @Version: 1.0
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.jpamultidatasources.dao1",
    entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanOne",
    transactionManagerRef = "platformTransactionManagerOne")
public class JpaPrimaryConfig {

  @Autowired
  @Qualifier(value = "firstDataSource")
  private DataSource firstDataSource;

  @Autowired private JpaProperties jpaProperties;

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanOne(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(firstDataSource)
        .properties(jpaProperties.getProperties())
        .packages("com.example.jpamultidatasources.entity")
        // .persistenceUnit("pu1")
        .build();
  }

  @Bean
  @Primary
  public PlatformTransactionManager platformTransactionManagerOne(
      EntityManagerFactoryBuilder builder) {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
        localContainerEntityManagerFactoryBeanOne(builder);
    return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
  }
}
