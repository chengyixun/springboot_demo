package com.example.jpamultidatasources.common.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceConfig @Author: amy @Description: DataSourceConfig @Date:
 * 2021/9/18 @Version: 1.0
 */
@Configuration
public class DataSourceConfig {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource.first")
  public DataSourceProperties firstDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  public DataSource firstDataSource() {
    return firstDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.second")
  public DataSourceProperties secondDataSourceProperties() {

    return new DataSourceProperties();
  }

  @Bean
  public DataSource secondDataSource() {
    return secondDataSourceProperties().initializeDataSourceBuilder().build();
  }
}
