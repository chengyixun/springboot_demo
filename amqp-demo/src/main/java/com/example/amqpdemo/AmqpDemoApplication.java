package com.example.amqpdemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class AmqpDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(AmqpDemoApplication.class, args);
  }
}
