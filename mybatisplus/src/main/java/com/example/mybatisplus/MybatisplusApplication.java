package com.example.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ClassName: MybatisplusApplication @Author: amy @Description:
 *             MybatisplusApplication @Date: 2021/8/21 @Version: 1.0
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan(basePackages = "com.example.mybatisplus.mapper")
public class MybatisplusApplication {
	public static void main(String[] args) {
		SpringApplication.run(MybatisplusApplication.class, args);
	}
}
