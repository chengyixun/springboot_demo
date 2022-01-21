package com.example.mybatispluscrud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatispluscrud.mapper")
@EnableAsync
public class MybatisplusCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisplusCrudApplication.class, args);
	}
}
