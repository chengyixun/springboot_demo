package com.example.mybatispluscrud;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatispluscrud.mapper")
@EnableAsync
@EnableApolloConfig
public class MybatisplusCrudApplication {

  public static void main(String[] args) {
    SpringApplication.run(MybatisplusCrudApplication.class, args);
  }
}
