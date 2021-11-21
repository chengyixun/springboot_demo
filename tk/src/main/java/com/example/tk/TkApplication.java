package com.example.tk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.tk.mapper")
@EnableAspectJAutoProxy
public class TkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TkApplication.class, args);
	}

}
