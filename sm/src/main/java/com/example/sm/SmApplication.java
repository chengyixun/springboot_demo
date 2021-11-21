package com.example.sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.sm.repository")
public class SmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmApplication.class, args);
	}

}
