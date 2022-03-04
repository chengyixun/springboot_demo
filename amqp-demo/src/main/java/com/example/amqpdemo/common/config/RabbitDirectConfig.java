package com.example.amqpdemo.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitDirectConfig @Author: amy @Description: RabbitDirectConfig @Date:
 * 2022/2/23 @Version: 1.0
 */
@Configuration
public class RabbitDirectConfig {

  public static final String DIRECTNAME = "test-direct";

  @Bean
  Queue queue() {
    return new Queue("hello-queue");
  }

  @Bean
  DirectExchange directExchange() {
    return new DirectExchange(DIRECTNAME, true, false);
  }

  @Bean
  Binding binding() {
    return BindingBuilder.bind(queue()).to(directExchange()).with("");
  }
}
