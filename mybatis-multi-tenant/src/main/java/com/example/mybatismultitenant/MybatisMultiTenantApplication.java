package com.example.mybatismultitenant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.example.mybatismultitenant.dao")
public class MybatisMultiTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisMultiTenantApplication.class, args);
	}
}
