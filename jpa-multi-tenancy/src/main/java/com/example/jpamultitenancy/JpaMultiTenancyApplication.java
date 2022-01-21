package com.example.jpamultitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // 开启JPA 审计
@EnableCaching
public class JpaMultiTenancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMultiTenancyApplication.class, args);
	}
}
