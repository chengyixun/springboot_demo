package com.example.jpamultitenancy.master.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MasterDatabaseProperties @Author: amy @Description: MasterDatabaseProperties @Date:
 * 2021/7/4 @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties("multitenancy.master.datasource")
public class MasterDatabaseProperties {

  private String url;

  private String password;

  private String username;

  private String driverClassName;

  private long connectionTimeout;

  private int maxPoolSize;

  private long idleTimeout;

  private int minIdle;

  private String poolName;
}
